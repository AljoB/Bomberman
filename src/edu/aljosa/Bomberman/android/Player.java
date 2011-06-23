package edu.aljosa.Bomberman.android;

import java.sql.Time;
import java.util.Timer;

import android.R.string;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
	Timer animacija = new Timer();
	private Bitmap levo;	
	private Bitmap desno;	
	private Bitmap gor;	
	private Bitmap dol;	
	private int gf;
	private Rect sourceRect;
	public int x,y;
	public Canvas c;
	public int trenuten_frame;
	public Player (int x,int y,Bitmap l, Bitmap d)
	{
		gf = 17;
		c = new Canvas();
		trenuten_frame=0;
		this.x = x;
		this.y = y;
		levo = l;
		desno = d;
		sourceRect = new Rect(0, 0, 17, 27);
	}
	
	public void premikLevo()
	{
		x--;
	}
	public void premikDesno()
	{
		x++;
	}
	public void premikGor()
	{
		y--;
	}
	public void premikDol()
	{
		y++;
	}
	public void namestiBombo()
	{
	
	}
	public boolean preveriTrk(int vrednost,int smer)
	{
		switch(smer)
		{
		case 1://Gor
			if(vrednost == 2 && y>0)
			{
				
				return false;
			}
			break;
		case 2://Desno
			if(vrednost == 2 && x<12)
			{
				
				return false;
			}
			break;
		case 3://Dol
			if(vrednost == 2 && y<8)
			{
				
				return false;
			}
			break;
		case 4://Levo
			if(vrednost == 2 && x>0)
			{
				
				return false;
			}
			break;
		}
		return true;
	}
	public void draw(Canvas canvas) {
		// where to draw the sprite
		Rect destRect = new Rect(x+156, y+6, x + 17+156, y + 27+6);
		c.drawBitmap(desno, sourceRect, destRect, null);	
		
		trenuten_frame++;
		this.sourceRect.left = trenuten_frame * 17;
		this.sourceRect.right = this.sourceRect.left + 17;
	}
	
	/*
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			
			trenuten_frame++;
			//x+=2;
			if (trenuten_frame==9) {
				break;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}*/
}
