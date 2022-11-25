package database;
import java.util.*;
import java.io.Serializable;
public class Row implements Serializable{
	Vector<Object> data;
	boolean passed;
	public Row(){
		this.setData( new Vector<Object>() );
		this.setPassed( false );
	}
	public Row( Vector<Object> row ){
		this.setData(row);
		this.setPassed(false);
	}

	public void setData( Vector<Object> data ){
		this.data = data;
	}
	public Vector<Object> getData(){
		return this.data;
	}

	public void addData( Object dat ){
		this.getData().add(dat);
	}

	public void setPassed( boolean pass ){
		this.passed = pass;
	}
	public boolean getPassed(){
		return this.passed;
	}

	public String toString(){
		String string = "";
		for( int i = 0 ; i < this.getData().size() ; i++ ){
			string += String.valueOf(this.getData().get(i) );
			if( i!=this.getData().size()-1 ){
				string += "::";
			}
		}
		return string;
	}
}