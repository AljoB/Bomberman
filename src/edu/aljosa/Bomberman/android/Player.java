package edu.aljosa.Bomberman.android;

import java.sql.Time;
import java.util.Timer;

import android.R.string;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
	public static final int PLAYER_STANJE_MIRUJE = 4;
	public static final int PLAYER_STANJE_HODI_LEVO = 4;
	public static final int PLAYER_STANJE_HODI_DESNO = 2;
	public static final int PLAYER_STANJE_HODI_GOR = 1;
	public static final int PLAYER_STANJE_HODI_DOL = 3;
	public int stanje;
	public int x,y;

	public int frame;
	
	public boolean animacijaTece;
	
	public Player (int x,int y)
	{
		animacijaTece = false;
		stanje = 2;
		frame=0;
		
		this.x = x;
		this.y = y;

	}
	
	public void premikLevo()
	{
		x--;
		stanje=PLAYER_STANJE_HODI_LEVO;
	}
	public void premikDesno()
	{
		x++;
		stanje = PLAYER_STANJE_HODI_DESNO;
	}
	public void premikGor()
	{
		y--;
		stanje = PLAYER_STANJE_HODI_GOR;
	}
	public void premikDol()
	{
		y++;
		stanje = PLAYER_STANJE_HODI_DOL;
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
}
