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
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
	private TextView f;
	private String a = null;
	private boolean connected = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        f = (TextView) findViewById(R.id.textView1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        f.setText("0.2");

        
    }

    public void prazno(View v)
    {
    
    }
    
    public void newGame(View v)
    {
    	Intent igra=new Intent();
    	igra.setClass(this,NovaIgra.class);
		this.startActivityForResult(igra, 0);

    }
    public void scoreboard(View v)
    {
    	Intent i = new Intent();
		i.setClass(this, Scores.class);
		startActivityForResult(i, 0);
   }
    public void settings(View v)
    {
    	Intent i = new Intent();
		i.setClass(this, MenuPreferences.class);
		startActivityForResult(i, 0);
    	
    }
    public void exit(View v)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                Main.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	switch(resultCode)
    	{
    		case RESULT_CANCELED:
    			break;
    		
    	}
    }
}

