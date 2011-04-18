package edu.aljosa.Bomberman.android;

public class Rezultat {
	private long dbID;
	private int stevilo_poskusov;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getDbID(){
		return dbID;
	}
	
	public void setDbID(long dbID) {
		this.dbID = dbID;
	}
	
	public int getStevilo_poskusov() {
		return stevilo_poskusov;
	}
	
	public void setStevilo_poskusov(int i) {
		stevilo_poskusov = i;
	}
	
	public Rezultat(String ime,int i) {
		name = ime;
		stevilo_poskusov = i;
	}
	public Rezultat() {
		stevilo_poskusov=0;
	}
}
