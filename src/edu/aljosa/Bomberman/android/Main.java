package edu.aljosa.Bomberman.android;


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
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    public void newGame(View v)
    {
    	Intent klic=new Intent(this,mapa.class);
		this.startActivityForResult(klic, RESULT_CANCELED);
    }
    public void scoreboard(View v)
    {
    	
    	
    	MojTask mt = new MojTask();
    	mt.execute(5000);
    	
    	//dialog.cancel();
    	
    	
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
    			//Toast toast1 = Toast.makeText(getApplicationContext(), "Cancel  ", Toast.LENGTH_SHORT);
				//toast1.show();
    			break;
    		
    	}
    }
    
	public class MojTask extends AsyncTask<Integer, Void, Long>
	{
		ProgressDialog dialogWait;
		@Override
		protected void onPreExecute()
		{
			dialogWait = ProgressDialog.show(Main.this, "",
					"Downloading scores. Please wait...", true);	
		}

		protected Long doInBackground(Integer...prviArgument)
		{
			long totalSize=0;
			int t1=prviArgument[0];
			try
			{
				Thread.sleep(t1);
			}
			catch(InterruptedException e)
			{
				Log.e("ERROR","Thread Interrupted");
			}
			totalSize=43;
			return totalSize;
		}
		protected void onPostExecute(Long tretjiArgument)
		{
			dialogWait.cancel();
		}

	}
}

