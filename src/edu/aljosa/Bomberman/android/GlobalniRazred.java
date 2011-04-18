package edu.aljosa.Bomberman.android;

import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;
import edu.aljosa.Bomberman.data.DBAdapterRezultat;




public class GlobalniRazred extends Application{
	RezultatArrayAdapter rezultati;
	DBAdapterRezultat db;
	public String Ime;
	
	public ArrayList<Rezultat> lista;
	Rezultat identifikator;
	Rezultat stInc,stDec;
	@Override
	public void onCreate() {
        super.onCreate(); //ne pozabi
        db = new DBAdapterRezultat(this);
        lista = new ArrayList<Rezultat>(); //inicializirat
        fillFromDB();
        rezultati = new RezultatArrayAdapter(this, R.layout.rezultat_layout,lista); //Step 4.10 Globalna lista    
	}
	
	public void init() {
	}
	
	public void fillFromDB() {
		db.open();
		Cursor c = db.getAll();
		Rezultat tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new Rezultat();
			tmp.setName(c.getString(DBAdapterRezultat.POS_NAME));
			tmp.setStevilo_poskusov(c.getInt(DBAdapterRezultat.POS_VALUE));
			//tmp.setTezavnost(c.getString(DBAdapterRezultat.POS_TEZAVNOST));
			tmp.setDbID(c.getLong(DBAdapterRezultat.POS__ID));
			lista.add(tmp); 
		}
		c.close();
		db.close();
	}
	
	public void add(Rezultat a) {
		//lista.add(a);
		rezultati.add(a);  //Step 4.10 Globalna lista
		addDB(a);
	}

	public void remove(Rezultat a) {
		//lista.add(a);
		if (a!=null)
		rezultati.remove(a);  //Step 4.10 Globalna lista
	}
	public void addDB(Rezultat s) {
		db.open();
		s.setDbID(db.insertRezultat(s));
		db.close();	
	}
}
