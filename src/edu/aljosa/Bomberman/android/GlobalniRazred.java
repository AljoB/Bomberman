package edu.aljosa.Bomberman.android;

import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;


public class GlobalniRazred extends Application{


	public String Ime;
	
	public ArrayList<Rezultat> lista;
	Rezultat identifikator;
	Rezultat stInc,stDec;
	@Override
	public void onCreate() {
        super.onCreate(); //ne pozabi
        lista = new ArrayList<Rezultat>(); //inicializirat
	}
	
	public void init() {
	}
	

}
