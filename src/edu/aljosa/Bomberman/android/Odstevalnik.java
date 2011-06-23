package edu.aljosa.Bomberman.android;

import java.util.Timer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.Toast;

public class Odstevalnik extends Timer{
	private Canvas canvas;
	private Bitmap levo;	
	private Bitmap desno;	
	private Bitmap gor;	
	private Bitmap dol;	
	private Rect sourceRect;
	public int x,y;
	public int trenuten_frame;
	
	public Odstevalnik(long millisInFuture, long countDownInterval, Canvas c, int x,int y, Bitmap d) {
		canvas = new Canvas();
		canvas = c;

		
		trenuten_frame=0;
		this.x = x;
		this.y = y;

		desno = d;
		sourceRect = new Rect(0, 0, 17, 27);
	}

	public void onTick(long millisUntilFinished) {
		/*Toast toast2 = Toast.makeText(,"vc" , Toast.LENGTH_SHORT);
		toast2.show();*/
		
		Rect destRect = new Rect(x+156, y+6, x + 17+156, y + 27+6);
		canvas.drawBitmap(desno, sourceRect, destRect, null);	
		trenuten_frame++;
		this.sourceRect.left = trenuten_frame * 17;
		this.sourceRect.right = this.sourceRect.left + 17;
	}
}
