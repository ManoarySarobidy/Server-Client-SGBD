package server;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import database.*;
public class Main{
    public static void main(String[] args) throws Exception{

        MyDb database = new MyDb("Test");
        // database.addRelations(rela());
        // database.addRelations(relb());
        // database.addRelations(student());
        // database.addRelations(course());
        // database.addRelations(CEN());
        // database.addRelations(CJH());
        // database.addRelations(CS());
        // database.addRelations(ENA());

        // String query = "Alaivo * anaty ENA mira CJH izay Adresse=Nice ";
        // database.executeQuery(query);

        ServerSocket server = new ServerSocket(5487);
        run(server,database);
        server.close();



        // System.out.println(database.getRelations().size());
    }
    public static void run(ServerSocket server , MyDb database) throws Exception{
        Socket client = server.accept();
        while(true){
            InputStream stream = client.getInputStream();
            String message = "";
            byte[] mes = new byte[50];
            stream.read(mes);
            message = new String( mes );
            message = message.trim();
            
            DataOutputStream response = new DataOutputStream( client.getOutputStream() );
            System.out.println(message);
            String retour = database.executeQuery(message);
            response.writeUTF(retour);
            
        }
    }

// data test

        public static Relations rela(){
        Vector<Object> cols = new Vector<Object>();
        cols.add("A");
        cols.add("B");
        // cols.add("A3");
        // cols.add("A4");

        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("a1");
        data.addData("x1");
        // data.addData("a3");
        datas.add(data);

        data = new Row();
        data.addData("a2");
        data.addData("x2");
        // data.addData("b3");
        datas.add(data);

        data = new Row();
        data.addData("a3");
        data.addData("x1");
        // data.addData("c3");
        datas.add(data);

        data = new Row();
        data.addData("a1");
        data.addData("x2");
        // data.addData("d3");
        datas.add(data);

        data = new Row();
        data.addData("a2");
        data.addData("x1");
        // data.addData("d3");
        datas.add(data);

        Relations rel = new Relations( "R1" , cols , datas );
        return rel;
    }

    public static Relations relb(){
        Vector<Object> cols = new Vector<Object>();
        cols.add("B");
        
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("x1");
        // data.addData("a3");
        datas.add(data);

        data = new Row();
        data.addData("x2");
        datas.add(data);
        
        Relations rel = new Relations( "R2" , cols , datas );
        return rel;
    }

    public static Relations student(){
        Vector<Object> cols = new Vector<Object>();
        cols.add("Name");
        cols.add("Course");

        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("System");
        data.addData("Btech");
        datas.add(data);

        data = new Row();
        data.addData("Database");
        data.addData("Mtech");
        datas.add(data);
        
        data = new Row();
        data.addData("Database");
        data.addData("Btech");
        datas.add(data);

        data = new Row();
        data.addData("Algebra");
        data.addData("Btech");
        datas.add(data);

        Relations rel = new Relations( "Class" , cols , datas );
        return rel;
    }

    public static Relations course(){
        Vector<Object> cols = new Vector<Object>();
        cols.add("Course");
       
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("Btech");
        datas.add(data);
        data = new Row();
        data.addData("Mtech");
        datas.add(data);

        Relations rel = new Relations( "Subject" , cols , datas );
        return rel;
    }
    public static Relations CEN(){
        Vector<Object> cols = new Vector<Object>();
        cols.add( "IdCours" );
        cols.add( "IdEtudiant" );
        cols.add( "Note" );
        
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("Archi");
        data.addData(100);
        data.addData("A");
        datas.add(data);

        data = new Row();
        data.addData("Archi");
        data.addData(300);
        data.addData("A");
        datas.add(data);

        data = new Row();
        data.addData("Syst");
        data.addData(100);
        data.addData("B");
        datas.add(data);

        data = new Row();
        data.addData("Syst");
        data.addData(200);
        data.addData("B");
        datas.add(data);

        data = new Row();
        data.addData("Syst");
        data.addData(300);
        data.addData("C");
        datas.add(data);

        data = new Row();
        data.addData("Algo");
        data.addData(100);
        data.addData("C");
        datas.add(data);

        data = new Row();
        data.addData("Algo");
        data.addData(200);
        data.addData("A");
        datas.add(data);


        Relations relation = new Relations( "CEN" , cols , datas);
        return relation;
    }
    public static Relations CJH(){
        Vector<Object> cols = new Vector<Object>();
        cols.add( "IdCours" );
        cols.add( "Jour" );
        cols.add( "Heure" );
        
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("Archi");
        data.addData("Lu");
        data.addData("9h");
        datas.add(data);

        data = new Row();
        data.addData("Algo");
        data.addData("Ma");
        data.addData("9h");
        datas.add(data);

        data = new Row();
        data.addData("Algo");
        data.addData("Ve");
        data.addData("9h");
        datas.add(data);

        data = new Row();
        data.addData("Syst");
        data.addData("Ma");
        data.addData("14h");
        datas.add(data);

        Relations relation = new Relations( "CJH" , cols , datas);
        return relation;
    }
    public static Relations CS(){
        Vector<Object> cols = new Vector<Object>();
        cols.add( "IdCours" );
        cols.add( "Salle" );
        
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData("Archi");
        data.addData("S1");
        datas.add(data);

        data = new Row();
        data.addData("Algo");
        data.addData("S2");
        datas.add(data);

        data = new Row();
        data.addData("Syst");
        data.addData("S1");
        datas.add(data);

        Relations relation = new Relations( "CS" , cols , datas);
        return relation;
    }
    public static Relations ENA(){
        Vector<Object> cols = new Vector<Object>();
        cols.add( "IdEtudiant" );
        cols.add( "Nom" );
        cols.add( "Adresse" );
        
        Vector<Row> datas = new Vector<Row>();
        Row data = new Row();
        data.addData(100);
        data.addData("Toto");
        data.addData("Nice");
        datas.add(data);

        data = new Row();
        data.addData(200);
        data.addData("Tata");
        data.addData("Paris");
        datas.add(data);

        data = new Row();
        data.addData(300);
        data.addData("Titi");
        data.addData("Rome");
        datas.add(data);

        Relations relation = new Relations( "ENA" , cols , datas);
        return relation;
    }


}