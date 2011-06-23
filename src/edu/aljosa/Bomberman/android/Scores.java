package edu.aljosa.Bomberman.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class Scores extends Activity{
	private TextView[] g;
	private TextView ena,dva,tri,stiri;
	private String a;
	private boolean connected = false;
	private TextView[] labels;
	
	private String[] nizi;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.score);
	        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        //a = new String();
	        //nizi = new String[21];
	        labels = new TextView[]{(TextView)findViewById(R.id.textView1),(TextView)findViewById(R.id.textView2),(TextView)findViewById(R.id.textView3),(TextView)findViewById(R.id.textView4),
	        		(TextView)findViewById(R.id.textView5),(TextView)findViewById(R.id.textView6),(TextView)findViewById(R.id.textView7),(TextView)findViewById(R.id.textView8),(TextView)findViewById(R.id.textView9),
	        		(TextView)findViewById(R.id.textView10),(TextView)findViewById(R.id.textView11),(TextView)findViewById(R.id.textView12),(TextView)findViewById(R.id.textView13),(TextView)findViewById(R.id.textView14),
	        		(TextView)findViewById(R.id.textView15),(TextView)findViewById(R.id.textView16),(TextView)findViewById(R.id.textView17),(TextView)findViewById(R.id.textView18),(TextView)findViewById(R.id.textView19),(TextView)findViewById(R.id.textView20)};
	        
	        
	        GET mt = new GET();
	    	mt.execute(5000);	
	    	
	 }
	 
	@Override
	public void onPause() { 	
		
	 	super.onPause();
	}
	 	
	@Override
	protected void onResume(){
		super.onResume();	
		
	}
	public void razberiNiz()
	{
		for(int i = 0;i<nizi.length;i++)
		{
			if(i==20)
				break;
			labels[i].setText(nizi[i]);
		}
	}

	
	public class GET extends AsyncTask<Integer, Void, Long>
	{
		ProgressDialog dialogWait;
		
		@Override
		protected void onPreExecute()
		{
			dialogWait = ProgressDialog.show(Scores.this, "",
					"Downloading scores. Please wait...", true);	
		}

		protected Long doInBackground(Integer...prviArgument)
		{
			long totalSize=0;
			int t1=prviArgument[0];
			
			String serverIpAddress="164.8.119.69";
			try {
				InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
				Socket socket = new Socket(serverAddr, 8080);
				connected = true;
				if(connected) {
					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
						out.println("GET ");
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
						String podatki=null;
						a=in.readLine();
						//f.setText(in.readLine());
						//wait(1000);
						//in.close();
						//out.close();
						//socket.close();
						connected=false;
					} catch (Exception e) {
						Log.e("ClientActivity", "S: Error", e);
						connected=false;
					}
	            }
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			totalSize=43;
			return totalSize;
		}
		protected void onPostExecute(Long tretjiArgument)
		{
			dialogWait.cancel();
			if(a!=null)
			{
				nizi = a.split(";");
			
				Log.e("", a);
				//labels[0].setText(a);
				//ena.setText(a);
				System.out.print(a);
				//f.getParent().
				razberiNiz();
			}
			else
			{
				//prislo do napake
			}
		}
	}
	public class PUT extends AsyncTask<Integer, Void, Long>
	{
		ProgressDialog dialogWait;
		
		@Override
		protected void onPreExecute()
		{
			dialogWait = ProgressDialog.show(Scores.this, "",
					"Downloading scores. Please wait...", true);	
		}

		protected Long doInBackground(Integer...prviArgument)
		{
			long totalSize=0;
			int t1=prviArgument[0];
			char[] ime = new char[30];
			String serverIpAddress="164.8.119.69";
			try {
				FileReader fReader;
		          try{
		        	  fReader = new FileReader("data\\data\\edu.aljosa.Bomberman.android\\files\\PlayerName.txt");	        	  
		        	  fReader.read(ime);
		        	  fReader.close();
		           }catch(Exception e){e.printStackTrace();}
				InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
				Socket socket = new Socket(serverAddr, 8080);
				connected = true;
				if(connected) {
					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
						out.println("PUT TTT;8653");
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
						String podatki=null;
						//a=in.readLine();
						//f.setText(in.readLine());
						//wait(1000);
						//in.close();
						//out.close();
						//socket.close();
						connected=false;
					} catch (Exception e) {
						Log.e("ClientActivity", "S: Error", e);
						connected=false;
					}
	            }
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			totalSize=43;
			return totalSize;
		}
		protected void onPostExecute(Long tretjiArgument)
		{
			dialogWait.cancel();
			if(a!=null)
			{
				//nizi = a.split(";");
			
				//Log.e("", a);
				//labels[0].setText(a);
				//ena.setText(a);
				//System.out.print(a);
				//f.getParent().
				//razberiNiz();
			}
			else
			{
				//prislo do napake
			}
		}
	}
	
}
