package database;
import java.util.*;
import java.lang.reflect.*;
import file.*;
import operation.*;
public class MyDb {
    

    String name;
    Vector<Relations> tables;
    String file;

    public MyDb(){
        this.setFile();
        this.setRelations();
    }

    public MyDb( String nom ) throws Exception{
        
        this.setName( nom );
        this.setFile();
        this.loadRelations();
    
    }

    public void loadRelations() throws Exception{
        FileControler file = new FileControler( this.getFile() );
        Vector<Relations> relations = file.readRelations();
        if( relations == null ){
            this.setRelations();
        }else{
            this.setRelations( relations );
        }
    }

    public void setName(String nom){
        this.name = nom;
    }

    public String getName(){
        return this.name;
    }

    public void setFile(){
        this.file = "/home/sarobidy/Documents/Base de données/relations.bid";
    }

    public String getFile(){
        return this.file;
    }

    public String getString(){
        return this.file;
    }

    public void setRelations(){
        this.tables = new Vector<Relations>();
    }

    public void setRelations( Vector<Relations> relations ){
        this.tables = relations;
    }

    public void addRelations(Relations toAdd){
        this.getRelations().add(toAdd);
        try{
            FileControler file = new FileControler( this.getFile() );
            file.saveRelation( toAdd );
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Relations> getRelations(){
        return this.tables;
    }
    public boolean checkBrackets( String e ){
        int c = 0;
        for( char r : e.toCharArray() ){
            if( r == '(' ){
                c++;
            }else if( r == ')' ){
                c--;
            }
        }
        return ( c == 0 ) ? true : false;
    }

    public String checkFirstSyntax(String query) throws Exception{
        String classPath = "", packages = "operation." , className = "";
        String[] queryTest = query.split(" ");
        String response = "";
        try{
            className = queryTest[0];

            classPath = packages + KeyWord.isFirst( className );
            
            String[] splited = query.split(className);
            
            Class<?> o = Class.forName(classPath);
            
            Method r = o.getMethod("execute",this.getClass(),String.class);
            
            response = (String) r.invoke( o.getDeclaredConstructor().newInstance() , this , splited[1]);
        
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return response;
    }

    public Relations checkIfexist(String table) throws Exception{
        for( int i = 0 ; i < this.getRelations().size() ; i++ ){
            if((this.getRelations().get(i).getName().toLowerCase()).equals(table.toLowerCase())){
                return this.getRelations().get(i);
            }
        }
        throw new Exception("Tsy misy ny fafana ilainao tompoko");
    }

    public String executeQuery(String query) throws Exception{
        return this.checkFirstSyntax(query);
    }

}