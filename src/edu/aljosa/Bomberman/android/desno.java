package edu.aljosa.Bomberman.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.animation.Animation;

public class desno extends Animation{
	private Canvas canvas;
	private Bitmap levo;	
	private Bitmap hoja_desno;	
	private Bitmap gor;	
	private Bitmap dol;	
	private Rect sourceRect;
	public int x,y;
	public int trenuten_frame;
	public desno(int x, int y, Canvas c, Bitmap d)
	{
		canvas = new Canvas();
		canvas = c;	
		trenuten_frame=0;
		this.x = x;
		this.y = y;
		hoja_desno = d;
		sourceRect = new Rect(0, 0, 17, 27);
	}
	
	@Override
	public void setDuration(long durationMillis) {
		// TODO Auto-generated method stub
		super.setDuration(durationMillis);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		
		Rect destRect = new Rect(x+156, y+6, x + 17+156, y + 27+6);
		
		canvas.drawBitmap(hoja_desno, sourceRect, destRect, null);	
		trenuten_frame++;
		this.sourceRect.left = trenuten_frame * 17;
		this.sourceRect.right = this.sourceRect.left + 17;
		
	}
}
