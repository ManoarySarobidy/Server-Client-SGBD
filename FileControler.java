package file;
import java.io.*;
import java.nio.file.*;
import java.util.Vector;
import database.Relations;

public class FileControler{
	String file;
	FileInputStream input;
	FileOutputStream output;

// constructors

	public FileControler( String filePath ){
		this.setFile( filePath );
	}

// functions parts
	public void saveRelation( Relations relations ) throws Exception{
		this.setOutputStream();
		try{
			ObjectOutputStream out = new ObjectOutputStream( this.getOutput() );
			out.writeObject( relations );
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getOutput().close();
		}
	}

	public Vector<Relations> readRelations() throws Exception{
		File fileChecker = new File(this.getFile());
		if( !fileChecker.exists() ){
			return null;
		}
		this.setInputStream();
		if( fileChecker.length() != 0 ){
			Vector<Relations> relations = new Vector<Relations>();
			while(true){
				try{
					ObjectInputStream inputstream = new ObjectInputStream( this.getInput() );
					Relations relation = (Relations) inputstream.readObject();
					relations.add( relation );
				}catch(EOFException e){
					break;
				}
			}
			return relations;
		}
		return null;
	}

// getters and setters 
	
	public void setInputStream() throws IOException{
		File files = new File( this.getFile() );
		this.input = new FileInputStream( files );
	}
	public FileInputStream getInput(){
		return this.input;
	}

	public void setOutputStream() throws IOException{
		File files = new File( this.getFile() );
		if( !files.exists() ){
			files.createNewFile();
		}
		this.output = new FileOutputStream( files , true );
	}

	public FileOutputStream getOutput(){
		return this.output;
	}

	public void setFile( String filePath ){
		this.file = filePath;
	}
	public String getFile(){
		return this.file;
	}
}