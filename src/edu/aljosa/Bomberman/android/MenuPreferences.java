package edu.aljosa.Bomberman.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Toast;


public class MenuPreferences extends PreferenceActivity {
	  public static final String TAG = "MenuPreferences";
	  SharedPreferences prefs;
	  GlobalniRazred app;
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  app = (GlobalniRazred) this.getApplication();
	}
	@Override
	 public void onResume() {
			super.onResume();
			
			FileReader fReader;
	          try{
	        	  fReader = new FileReader("data\\data\\edu.aljosa.Bomberman.android\\files\\PlayerName.txt");
	        	  char[] ime = new char[30];
	        	  fReader.read(ime);
	        	  
	        	  app.Ime=ime.toString();
	        	  
	        	  fReader.close();
	        	  //Toast toast1 = Toast.makeText(getApplicationContext(), "  " + ime.toString(), Toast.LENGTH_SHORT);
				  //toast1.show();
	           }catch(Exception e){
	                    e.printStackTrace();
	           }
	           
	           
	 }
	@Override
	public void onPause() {
	  super.onPause();
	  SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(app);
      app.Ime = settings.getString("Ime", "Player");
      FileWriter fWriter;
      try{
           fWriter = new FileWriter("data\\data\\edu.aljosa.Bomberman.android\\files\\PlayerName.txt");
           fWriter.write(app.Ime);
           //Toast toast1 = Toast.makeText(getApplicationContext(), ""+ app.Ime, Toast.LENGTH_SHORT);
		   //toast1.show();
           fWriter.flush();
           fWriter.close();
       }catch(Exception e){
                e.printStackTrace();
       }
	  setResult(RESULT_CANCELED);
	  this.finish();
	  
	  
	}
}