package edu.aljosa.Bomberman.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

public class NovaIgra extends Activity {
	//public int sirinaUV, visinaUV;
	public float cx;
	public float cy;
	private SensorManager sSensorManager;
	public IzrisiPlosco m;
	public Player igralec;
	public ArrayList<Bomba> bombe;
	private long last_tick = 0;
	public Boolean sprememba_na_mapi;
	public int[][] igralna_plosca;
	public int premik;
	public Bitmap levo, desno, dol, gor;
	public Rect sourceRectPlayerSmer;
	loopthread nit;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double a,b,c;
		


	}
	private SensorEventListener sSensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			cx = event.values[1];
			cy = event.values[2];
			//m.invalidate();
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		sprememba_na_mapi = true;
		bombe = new ArrayList<Bomba>();
		m = new IzrisiPlosco(this);
		setContentView(m);
		sourceRectPlayerSmer = new Rect(86, 0, 172, 132);
		igralna_plosca = new int[][] {
				{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2 },
				{ 0, 1, 2, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2 },
				{ 0, 0, 2, 2, 2, 0, 2, 0, 2, 0, 2, 0, 2 },
				{ 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2 },
				{ 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 0, 0, 2, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0 } };
		/*igralna_plosca = new int[][] {
				{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
				{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
				{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
				{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
				{ 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 2, 2, 2 },
				{ 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 0, 0, 2, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0 } };*/
		
		levo = BitmapFactory.decodeResource(getResources(), R.drawable.levop);
		desno = BitmapFactory.decodeResource(getResources(), R.drawable.desnop);
		dol = BitmapFactory.decodeResource(getResources(), R.drawable.naprejp);
		gor = BitmapFactory.decodeResource(getResources(), R.drawable.gorp);
		igralec = new Player(0, 0);
		sSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		nit = new loopthread();
		nit.execute();
	}
	@Override
	public void onStop() {
		super.onStop();
		sSensorManager.unregisterListener(sSensorListener);
	}
	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();
		sSensorManager.registerListener(sSensorListener, sSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION | Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (igralec.stanje) {
		case 1:
			bombe.add(new Bomba(igralec.x, igralec.y - 1));
			break;
		case 2:
			bombe.add(new Bomba(igralec.x + 1, igralec.y));
			break;
		case 3:
			bombe.add(new Bomba(igralec.x, igralec.y + 1));
			break;
		case 4:
			bombe.add(new Bomba(igralec.x - 1, igralec.y));
			break;
		}
		
		return true;
	}

	public void preveriCollision(Bomba temp)
	{
		for(int i=temp.y-1;i<temp.y+2;i++)
		{
			for(int j=temp.x-1;j<temp.x+2;j++)
			{
				if(i>=0 && i<9 && j>=0 && j<13)
				{
					if(igralna_plosca[i][j]==0)
						igralna_plosca[i][j]=2;
				}
			}
		}
	}
	
	// ////////////////////////////
	public class IzrisiPlosco extends View {
		public Bitmap ozadje;
		private Bitmap brick, field, pillar, player,bombaAnimacija;
		private int xs = 0, ys = 0;
		private double korakH, korakV;

		private Rect sourceRectPlayer;

		public IzrisiPlosco(Context context) {
			super(context);
		

			brick = BitmapFactory.decodeResource(getResources(),
					R.drawable.brick);
			field = BitmapFactory.decodeResource(getResources(),
					R.drawable.field);
			pillar = BitmapFactory.decodeResource(getResources(),
					R.drawable.pillar);
			player = BitmapFactory.decodeResource(getResources(),
					R.drawable.stoje);
			bombaAnimacija = BitmapFactory.decodeResource(getResources(),
					R.drawable.animacijabomba);
			sourceRectPlayer = new Rect(0, 0, 86, 132);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {

			int sirina = canvas.getWidth() / 13;
			int visina = canvas.getHeight() / 9;
			updateUI(canvas, sirina, visina);
			updatePlayer(canvas,sirina,visina);
			updateBombe(canvas,sirina,visina);
		}

		public void updateBombe(Canvas canvas,int sirina, int visina) {
			

			for (int i = 0; i < bombe.size(); i++) {

				Rect destRect = new Rect((bombe.get(i).x * sirina),
						(bombe.get(i).y * visina),
						((bombe.get(i).x + 1) * sirina),
						((bombe.get(i).y + 1) * visina));
				Rect destRect2 = new Rect(
						((bombe.get(i).x - 1) * sirina),
						((bombe.get(i).y - 1) * visina),
						((bombe.get(i).x + 2) * sirina),
						((bombe.get(i).y + 2) * visina));
				if (bombe.get(i).frame >= 20) {
					bombe.remove(i);					
					i--;					
				} 
				else if(bombe.get(i).frame >= 19){
					preveriCollision(bombe.get(i));
				}
				else {
					if (bombe.get(i).frame > 13)
						canvas.drawBitmap(bombaAnimacija,
								bombe.get(i).sourceRect, destRect2, null);
					else
						canvas.drawBitmap(bombaAnimacija,
								bombe.get(i).sourceRect, destRect, null);

					bombe.get(i).frame++;
					bombe.get(i).sourceRect.left = bombe.get(i).frame * 86;
					bombe.get(i).sourceRect.right = bombe.get(i).sourceRect.left + 86;
				}
			}

		}

		public void updateUI(Canvas canvas, int sirina, int visina) // Polepsaj	UI
		{
			//Paint crta = new Paint();
			//crta.setARGB(250, 0, 0, 0);
			//crta.setStrokeWidth(6);

			//Gumb_bomba = Bitmap.createScaledBitmap(Gumb_bomba, 150,
					//getHeight() / 3, true);
			brick = Bitmap.createScaledBitmap(brick, sirina, visina, true);
			field = Bitmap.createScaledBitmap(field, sirina, visina, true);
			pillar = Bitmap.createScaledBitmap(pillar, sirina, visina, true);
			korakH = pillar.getWidth() / 8;
			korakV = pillar.getHeight() / 8;
			//canvas.drawLine(150, 0, 150, getHeight(), crta);
			//canvas.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3,
					//crta);

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 13; j++) {
					switch (igralna_plosca[i][j]) {
					case 0:
						canvas.drawBitmap(brick, j * brick.getWidth(), i
								* brick.getHeight(), null);
						break;
					case 1:
						canvas.drawBitmap(pillar, j * brick.getWidth(), i
								* brick.getHeight(), null);
						break;
					case 2:
						canvas.drawBitmap(field, j * brick.getWidth(), i
								* brick.getHeight(), null);
						break;
					}
				}
			}
			//canvas.drawBitmap(Gumb_bomba, 0, 0, null);
			//canvas.drawBitmap(Gumb_bomba, 0, getHeight() / 3, null);
			//canvas.drawBitmap(Gumb_bomba, 0, getHeight() / 3 + getHeight() / 3,
					//null);

			//canvas.drawLine(0, 3, 153, 3, crta);
			//canvas.drawLine(0, getHeight() / 3 - 3, 153, getHeight() / 3 - 3,
				//	crta);
			//canvas.drawLine(0, getHeight() / 3 + getHeight() / 3 - 3, 153,
				//	getHeight() / 3 + getHeight() / 3 - 3, crta);

		}

		public void updatePlayer(Canvas canvas,int sirina,int visina) {
			if (igralec.animacijaTece) {
				if (igralec.stanje == 1)
					AnimacijaGor(canvas,sirina,visina);
				if (igralec.stanje == 2)
					AnimacijaDesno(canvas,sirina,visina);
				if (igralec.stanje == 3)
					AnimacijaDol(canvas,sirina,visina);
				if (igralec.stanje == 4)
					AnimacijaLevo(canvas,sirina,visina);
			} else {
				Rect destRect = new Rect(igralec.x *sirina, igralec.y *visina,
						(igralec.x+1) * sirina, (igralec.y+1)* visina );
				canvas.drawBitmap(player, NovaIgra.this.sourceRectPlayerSmer,
						destRect, null);
			}
		}

		public void AnimacijaDesno(Canvas canvas,int sirina,int visina) {
			Rect destRect = new Rect(igralec.x *sirina + xs, igralec.y *visina, (igralec.x+1)
					* sirina + xs, (igralec.y+1) * visina );

			if (igralec.frame >7 ) {
				
				igralec.frame = 0;
				xs = 0;
				igralec.premikDesno();
				igralec.animacijaTece = false;
			} else {

				// last_tick = System.currentTimeMillis();
				canvas.drawBitmap(desno, sourceRectPlayer, destRect, null);
				xs += korakH;
				igralec.frame++;
				this.sourceRectPlayer.left = igralec.frame * 86;
				this.sourceRectPlayer.right = this.sourceRectPlayer.left + 86;

				canvas.drawBitmap(desno, sourceRectPlayer, destRect, null);

			}
		}

		public void AnimacijaLevo(Canvas canvas,int sirina,int visina) {
			Rect destRect = new Rect(igralec.x *sirina + xs, igralec.y *visina, (igralec.x+1)
					* sirina + xs, (igralec.y+1) * visina );

			if (igralec.frame >7) {
				
				igralec.frame = 0;
				xs = 0;
				igralec.premikLevo();
				igralec.animacijaTece = false;
			} else {

				// last_tick = System.currentTimeMillis();
				canvas.drawBitmap(levo, sourceRectPlayer, destRect, null);
				xs -= korakH;
				igralec.frame++;
				this.sourceRectPlayer.left = igralec.frame * 86;
				this.sourceRectPlayer.right = this.sourceRectPlayer.left + 86;

				canvas.drawBitmap(levo, sourceRectPlayer, destRect, null);

			}
		}

		public void AnimacijaGor(Canvas canvas,int sirina,int visina) {
			Rect destRect = new Rect(igralec.x *sirina , igralec.y *visina+ys, (igralec.x+1)
					* sirina , (igralec.y+1) * visina + ys);

			if (igralec.frame >7) {
				
				igralec.frame = 0;
				ys = 0; //popravi
				igralec.premikGor();
				igralec.animacijaTece = false;
			} else {

				// last_tick = System.currentTimeMillis();
				canvas.drawBitmap(gor, sourceRectPlayer, destRect, null);
				ys -= korakV;
				igralec.frame++;
				this.sourceRectPlayer.left = igralec.frame * 86;
				this.sourceRectPlayer.right = this.sourceRectPlayer.left + 86;

				canvas.drawBitmap(gor, sourceRectPlayer, destRect, null);

			}
		}

		public void AnimacijaDol(Canvas canvas,int sirina,int visina) {
			Rect destRect = new Rect(igralec.x *sirina , igralec.y *visina+ys, (igralec.x+1)
					* sirina , (igralec.y+1) * visina + ys);

			if (igralec.frame >7) {
				
				igralec.frame = 0;
				ys=0;
				igralec.premikDol();
				igralec.animacijaTece = false;
			} else {

				// last_tick = System.currentTimeMillis();
				canvas.drawBitmap(dol, sourceRectPlayer, destRect, null);
				ys += korakV;
				igralec.frame++;
				this.sourceRectPlayer.left = igralec.frame * 86;
				this.sourceRectPlayer.right = this.sourceRectPlayer.left + 86;
			}
		}

	}

	public class loopthread extends AsyncTask<Integer, Void, Long> {
		@Override
		protected void onPreExecute() {

		}

		protected Long doInBackground(Integer... prviArgument) {
			while (true) {
				long time = (System.currentTimeMillis() - last_tick);
				if (time >= 100) {			
					if (igralec.animacijaTece == false) 
					{
						if(Math.abs(cx)>Math.abs(cy))
						{
							if(cx<-10)//desno
							{
								if (igralec.x < 12) {
									if (igralec.preveriTrk(
										igralna_plosca[igralec.y][igralec.x + 1],Player.PLAYER_STANJE_HODI_DESNO) == false) {
										igralec.animacijaTece = true;
										sourceRectPlayerSmer = new Rect(258, 0, 344, 132);
										igralec.stanje=Player.PLAYER_STANJE_HODI_DESNO;
										//igralec.premikDesno();
									}
								}
							}
							if(cx>10)//levo
							{
								if (igralec.x > 0) {

									if (igralec.preveriTrk(
										igralna_plosca[igralec.y][igralec.x - 1],Player.PLAYER_STANJE_HODI_LEVO) == false) {
										igralec.animacijaTece = true;
										sourceRectPlayerSmer = new Rect(0, 0, 86, 132);
										igralec.stanje=Player.PLAYER_STANJE_HODI_LEVO;
										//igralec.premikLevo();
									}
								}
							}
						}
						else
						{
							if(cy<-10)//gor
							{
								if (igralec.y > 0) {
									if (igralec.preveriTrk(
										igralna_plosca[igralec.y - 1][igralec.x],Player.PLAYER_STANJE_HODI_GOR) == false) {
										igralec.animacijaTece = true;
										sourceRectPlayerSmer = new Rect(172, 0, 258, 132);
										igralec.stanje=Player.PLAYER_STANJE_HODI_GOR;
										//igralec.premikGor();
									}

								}
							}
							if(cy>10)//dol
							{
								if (igralec.y < 8) {
									if (igralec.preveriTrk(
										igralna_plosca[igralec.y + 1][igralec.x],Player.PLAYER_STANJE_HODI_DOL) == false) {
										igralec.animacijaTece = true;
										sourceRectPlayerSmer = new Rect(86, 0, 172, 132);
										igralec.stanje=Player.PLAYER_STANJE_HODI_DOL;
										//igralec.premikDol();
									}
								}
							}
							
						}
	
					}
					last_tick = System.currentTimeMillis();
					m.postInvalidate();
				}

			}
		}

		protected void onPostExecute(Long tretjiArgument) {

		}
	}
}
