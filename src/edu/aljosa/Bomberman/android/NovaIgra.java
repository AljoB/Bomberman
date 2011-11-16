package edu.aljosa.Bomberman.android;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

public class NovaIgra extends Activity{
		public IzrisiPlosco m;
		public Player igralec;
		public Boolean sprememba_na_mapi;
		public int[][] igralna_plosca;
		public int premik;
		public Bitmap levo,desno,dol,gor;
		private Timer timerAnimacija;
		private Canvas c;
		private boolean mIsPlaying = false;
		public Rect sourceRectSmer;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        sprememba_na_mapi= true;
	        c = new Canvas();
	        m = new IzrisiPlosco(this);
	        setContentView(m);
	        sourceRectSmer = new Rect(86,0,172,132);
	        igralna_plosca = new int[][]{
	    			{2,2,2,2,2,2,2,2,2,0,2,2,2},
	    			{0,1,2,1,2,1,0,1,0,1,0,1,2},
	    			{0,0,2,2,2,0,2,0,2,0,2,0,2},
	    			{0,1,0,1,2,1,0,1,0,1,0,1,0},
	    			{0,0,0,0,2,0,0,0,0,0,0,0,2},
	    			{0,1,0,1,2,1,0,1,0,1,0,1,0},
	    			{2,0,2,0,2,0,2,0,0,0,0,0,0},
	    			{0,1,0,1,2,1,0,1,0,1,0,1,0},
	    			{0,0,2,2,2,0,2,0,0,0,2,0,0}};	
	        premik=3;
	        levo = BitmapFactory.decodeResource(getResources(), R.drawable.levop);
	        desno = BitmapFactory.decodeResource(getResources(), R.drawable.desnop);
	        dol = BitmapFactory.decodeResource(getResources(), R.drawable.naprejp);
	        gor = BitmapFactory.decodeResource(getResources(), R.drawable.gorp);
	        igralec = new Player(0,0,levo,desno);
	    }
		
	 	@Override
		public void onPause() { 	
	 		super.onPause();
	    }
	 	
		@Override
	    protected void onResume(){
			super.onResume();		
	    }
		private void TimerMethod()
		{
	    	this.runOnUiThread(Timer_Tick);
		}

		private Runnable Timer_Tick = new Runnable() {
			public void run() {
				if(igralec.trenuten_frame<9)
					igralec.draw(null);
			}
		};
		
		/*public boolean onTouchEvent(MotionEvent event)
		{
			//za bombe
			return true;
		}*/
		
		
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	super.onKeyDown( keyCode,  event);
	    	if(mIsPlaying==false)
	    	{
	    	switch (keyCode) {
	            case KeyEvent.KEYCODE_DPAD_DOWN:
	            	if(igralec.y<8)
	            	{
	            		if(igralec.preveriTrk(igralna_plosca[igralec.y+1][igralec.x], 3)==false)
	            		{	 
	            			mIsPlaying=true;
	            			sourceRectSmer = new Rect(86,0,172,132);
	            			premik =3;    			
	            			igralec.premikDol();
	            		}
	            	
	            	}
		          	break;      
	            case KeyEvent.KEYCODE_DPAD_LEFT:
	            	if(igralec.x>0)
	            	{
	            		if(igralec.preveriTrk(igralna_plosca[igralec.y][igralec.x-1], 4)==false)
	            		{
	            			mIsPlaying=true;
	            			sourceRectSmer = new Rect(0,0,86,132);
	            			premik =4;    			
	            			igralec.premikLevo();
	            		}
	            	}
	            	      
		          	break;       
	            case KeyEvent.KEYCODE_DPAD_RIGHT:
	            	if(igralec.x<12)
	            	{
	            		if(igralec.preveriTrk(igralna_plosca[igralec.y][igralec.x+1], 2)==false)
	            		{
	            			mIsPlaying=true;
	            			sourceRectSmer = new Rect(258,0,344,132);
	            			premik =2;    			
	            			igralec.premikDesno();
	            		}	
	            	}
		          	break;     
	            case KeyEvent.KEYCODE_DPAD_UP:
	            	if(igralec.y>0)
	            	{
	            		if(igralec.preveriTrk(igralna_plosca[igralec.y-1][igralec.x], 1)==false)
	            		{
	            			mIsPlaying=true;
	            			sourceRectSmer = new Rect(172,0,258,132);
	            			premik =1;    			
	            			igralec.premikGor();
	            		}
	            		
	            	}
	            	

	            	break;      
	            case KeyEvent.KEYCODE_BACK:
	            	setResult(RESULT_CANCELED);
	            	finish();
	            break;
	        }    
	    	m.invalidate();
	    	}
	        return false;
	    }
	    //////////////////////////////
	    public class IzrisiPlosco extends View {
	    	public Bitmap ozadje;
	    	private Bitmap bomba;
	    	private Bitmap Gumb_bomba;
	    	private Bitmap brick;
	    	private Bitmap field;
	    	private Bitmap pillar;
	    	private Bitmap zajcek;
	    	private Bitmap player;
			private int xs=156,ys=6;
			private int korakH,korakV;
			
			private int play_frame = 0;
		    private long last_tick = 0;
			private Rect sourceRect;
			//private Rect destRect;
			
			public IzrisiPlosco(Context context) {
				super(context);
				//ozadje = new Bitmap(
				Gumb_bomba = BitmapFactory.decodeResource(getResources(), R.drawable.gumb_bomba);
				bomba = BitmapFactory.decodeResource(getResources(), R.drawable.bomba);
				brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
				field = BitmapFactory.decodeResource(getResources(), R.drawable.field);
				pillar = BitmapFactory.decodeResource(getResources(), R.drawable.pillar);
				zajcek = BitmapFactory.decodeResource(getResources(), R.drawable.zajcek);
				player = BitmapFactory.decodeResource(getResources(), R.drawable.stoje);
				
				sourceRect = new Rect(0, 0, 86, 132);	
			}
			
			@Override
	        protected void onDraw(Canvas canvas) {
				Paint crta = new Paint();
			    crta.setARGB(250, 0, 0, 0);
			    crta.setStrokeWidth(6);
			      
				int sirina= canvas.getWidth()/13-12;
				int visina= canvas.getHeight()/9-12;
				Gumb_bomba = Bitmap.createScaledBitmap(Gumb_bomba, 150, getHeight()/3, true);
				brick = Bitmap.createScaledBitmap(brick, sirina, visina, true);
				field = Bitmap.createScaledBitmap(field, sirina, visina, true);
				pillar = Bitmap.createScaledBitmap(pillar, sirina, visina, true);
				zajcek = Bitmap.createScaledBitmap(zajcek, sirina, visina, true);
				bomba = Bitmap.createScaledBitmap(bomba, sirina, visina, true);
				//player = Bitmap.createScaledBitmap(player, sirina, visina, true);
				korakH = pillar.getWidth()/9;
				korakV = pillar.getHeight()/9;
				canvas.drawLine(150, 0, 150, getHeight(), crta);
				canvas.drawLine(0, getHeight()-3, getWidth(), getHeight()-3, crta);
				
				for(int i = 0 ;i<9;i++)
				{
					for(int j = 0; j<13;j++)
					{
						switch(igralna_plosca[i][j])
						{
						case 0:
							canvas.drawBitmap(brick, j*brick.getWidth()+156 ,i*brick.getHeight()+6, null);
							break;
						case 1:
							canvas.drawBitmap(pillar, j*brick.getWidth()+156 ,i*brick.getHeight()+6, null);
							break;
						case 2:
							canvas.drawBitmap(field, j*brick.getWidth()+156 ,i*brick.getHeight()+6, null);
							break;
						}
					}
				}	
				canvas.drawBitmap(Gumb_bomba, 0 ,0, null);
				canvas.drawBitmap(Gumb_bomba, 0 ,getHeight()/3, null);
				canvas.drawBitmap(Gumb_bomba, 0 ,getHeight()/3+getHeight()/3, null);

				canvas.drawLine(0, 3, 153, 3, crta);
				canvas.drawLine(0, getHeight()/3-3, 153, getHeight()/3-3, crta);
				canvas.drawLine(0, getHeight()/3+getHeight()/3-3, 153, getHeight()/3+getHeight()/3-3, crta);


				Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
				
				//canvas.drawBitmap(player, sourceRect, destRect, null);
				
		        if (mIsPlaying)
		        {   
		        	if(premik==1)
		        		AnimacijaGor(canvas);
		        	if(premik==2)
		        		AnimacijaDesno(canvas);
		        	if(premik==3)
		        		AnimacijaDol(canvas);
		        	if(premik==4)
		        		AnimacijaLevo(canvas);
		        }
		        else
		        {
		        	canvas.drawBitmap(player, NovaIgra.this.sourceRectSmer, destRect, null);	
            		
		        	//canvas.drawBitmap(player, igralec.x+xs ,igralec.y+ys, null);
		        }
	
			}   
			public void AnimacijaDesno(Canvas canvas)
			{
				Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
				
				if (play_frame >= 9)
	            {
	                mIsPlaying = false;
	                play_frame=0;                
	                xs+=korakH;
	                //canvas.drawBitmap(player, sourceRect, destRect, null);
	                m.invalidate();
	            }
	            else
	            {
	                long time = (System.currentTimeMillis() - last_tick);
	                if (time >= 100)
	                {
	                    last_tick = System.currentTimeMillis();		            		
	                    //Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	            		canvas.drawBitmap(desno, sourceRect, destRect, null);	
	            		xs+=korakH;
	            		play_frame++;
	            		this.sourceRect.left = play_frame * 86;
	            		this.sourceRect.right = this.sourceRect.left + 86;	                    
	                    postInvalidate();
	                }
	                else //still within delay.  redraw current frame
	                {
	                	//Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	                	canvas.drawBitmap(desno, sourceRect, destRect, null);	
	            		postInvalidate();
	                }
	            }
			}
			public void AnimacijaLevo(Canvas canvas)
			{
				Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
				
				if (play_frame >= 9)
	            {
	                mIsPlaying = false;
	                play_frame=0;
	                m.invalidate();
	                xs-=korakH;
	                //canvas.drawBitmap(player, sourceRect, destRect, null);
	                m.invalidate();
			        //postInvalidate();
	            }
	            else
	            {
	                long time = (System.currentTimeMillis() - last_tick);
	                if (time >= 100)
	                {
	                    last_tick = System.currentTimeMillis();		            		
	                    //Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	            		canvas.drawBitmap(levo, sourceRect, destRect, null);	
	            		xs-=korakH;
	            		play_frame++;
	            		this.sourceRect.left = play_frame * 86;
	            		this.sourceRect.right = this.sourceRect.left + 86;	                    
	                    postInvalidate();
	                }
	                else //still within delay.  redraw current frame
	                {
	                	canvas.drawBitmap(levo, sourceRect, destRect, null);	
	            		postInvalidate();
	                }
	            }
			}
			public void AnimacijaGor(Canvas canvas)
			{
				Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
				
				if (play_frame >= 9)
	            {
	                mIsPlaying = false;
	                play_frame=0;                
	                ys-=korakV;
	                //canvas.drawBitmap(player, sourceRect, destRect, null);
	                m.invalidate();
	            }
	            else
	            {
	                long time = (System.currentTimeMillis() - last_tick);
	                if (time >= 100)
	                {
	                    last_tick = System.currentTimeMillis();		            		
	                    //Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	            		canvas.drawBitmap(gor, sourceRect, destRect, null);	
	            		ys-=korakV;
	            		play_frame++;
	            		this.sourceRect.left = play_frame * 86;
	            		this.sourceRect.right = this.sourceRect.left + 86;	                    
	                    postInvalidate();
	                }
	                else //still within delay.  redraw current frame
	                {
	                	//Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	                	canvas.drawBitmap(gor, sourceRect, destRect, null);	
	            		postInvalidate();
	                }
	            }
			}
			public void AnimacijaDol(Canvas canvas)
			{
				Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
				
				if (play_frame >= 9)
	            {
	                mIsPlaying = false;
	                play_frame=0;                
	                ys+=korakV;
	                //canvas.drawBitmap(player, sourceRect, destRect, null);
	                m.invalidate();
	            }
	            else
	            {
	                long time = (System.currentTimeMillis() - last_tick);
	                if (time >= 100)
	                {
	                    last_tick = System.currentTimeMillis();		            		
	                    //Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	            		canvas.drawBitmap(dol, sourceRect, destRect, null);	
	            		ys+=korakV;
	            		play_frame++;
	            		this.sourceRect.left = play_frame * 86;
	            		this.sourceRect.right = this.sourceRect.left + 86;	                    
	                    postInvalidate();
	                }
	                else //still within delay.  redraw current frame
	                {
	                	//Rect destRect = new Rect(igralec.x+xs, igralec.y+ys,igralec.x + 25+xs, igralec.y + 40+ys);
	                	canvas.drawBitmap(dol, sourceRect, destRect, null);	
	            		postInvalidate();
	                }
	            }
				
				invalidate();
			}
		
	    }
}

	

