package edu.aljosa.Bomberman.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class RezultatListActivity extends ListActivity implements OnItemClickListener{
	GlobalniRazred app;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rezultat_list_activity);
		app = (GlobalniRazred) getApplication();
		setListAdapter(app.rezultati);
		this.getListView().setOnItemClickListener(this);

	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//Toast.makeText(this, "Pritisnili ste:"+app.stevci.getItem(position).getStanje(), Toast.LENGTH_LONG).show();
		
	}
}
