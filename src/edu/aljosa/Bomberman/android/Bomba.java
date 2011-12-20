package edu.aljosa.Bomberman.android;

import java.util.Timer;

import android.graphics.Rect;
import android.text.format.Time;

public class Bomba {
		public int x,y;
		public int frame;
		public boolean animacijaTece;
		public Rect sourceRect;
		
		public Bomba(int x, int y)
		{
			sourceRect = new Rect(0, 0, 86, 132);	
			this.x = x;
			this.y = y;
			animacijaTece=true;
			frame = 0;
		}
}
