package database;
import java.util.*;
import java.io.Serializable;
public class Relations implements Serializable{
    String name;
    Vector<Object> colones;
    Vector<Row> rows;

    public Relations(){

    }

    public Relations( String n , Vector<Object> c , Vector<Row> r ){
        this.setName(n);
        this.setColumn(c);
        this.setRows(r);
    }

// default check
    public Relations defaultRelations() throws Exception{
        String[] cols = {"*"};
        return this.projection( cols , this );
    }

    public Vector<Object> create(String[] cols,Vector<Integer> inte){
        if( cols.length == 1 && cols[0].equals("*")){
            return createColumn(inte);
        }
        return createColumn(cols);
    }
    public Vector<Object> createColumn(String[] cols){
        Vector<Object> col = arrayToVect(cols);
        return col;  
    }
    public Vector<Object> createColumn(Vector<Integer> integer){
        Vector<Object> col = new Vector<Object>();
        for( int i = 0 ; i < integer.size() ; i++ ){
            col.add( this.getColumn().get((int)integer.get(i)) );
        }
        return col;
    }

    public Vector<Object> arrayToVect(String[] cols){
        Vector<Object> e = new Vector<Object>();
        for(String r : cols){
            e.add(r);
        }
        return e;
    }

    public void addData( Row data , Vector<Row> datas ){
        if( !datas.contains(data) ){
            datas.add( data );
        }
    }

    public boolean isInvalid( Object toCheck ){
        boolean string = ( toCheck == null || ( (String.valueOf(toCheck)).equals("") ) ) ;
        return string;
    }

    public boolean checkLength( Relations two ) throws Exception{
        if( this.getColumn().size() == two.getColumn().size() ){
            return true;
        }
        throw new Exception("Le nombre de colonnes ne sont pas les memes");
    }

// relational operations
    public Relations projection(String[] cols , Relations rel) throws Exception { 
        Vector<Integer> inte = rel.column( cols );
        Vector<Object> column = rel.create( cols , inte);
        Vector<Row> newRel = new Vector<Row>();
        for( int i = 0 ; i < rel.getRows().size()  ; i++ ){
            Row data = new Row();
            for( int e = 0 ; e < inte.size() ; e++ ){
                data.addData( ( rel.getRows().get(i) ).getData().get( (int)inte.get(e) ) );
            }
            addData( data , newRel );
        }
        newRel = removeDoublons(newRel);
        Relations r = new Relations( "Projection" , column , newRel );
        return r;
    }

    public Relations select(Object predicat, Object column) throws Exception{
        int index = ( this.getColumnIndex((String)column).size() > 0 ) ? this.getColumnIndex((String)column).get(0) : 0;
        if(this.isInvalid(predicat) && this.isInvalid(column)){
            return defaultRelations();
        }
        if( index == 0 || ( (this.isInvalid(predicat) && !this.isInvalid(column)) || (this.isInvalid(column) && !this.isInvalid(predicat)) ) ){
            // System.out.println( index );
            throw new Exception("Amarino ny fangatahanao azafady");
        }
        Vector<Row> datas = new Vector<Row>();
        for( int i = 0 ; i < this.getRows().size() ; i++ ){
            predicat = this.parseObject( this.getRows().get(i).getData().get( index - 1 ) , predicat );
            // System.out.println( predicat.getClass() );
            if( ( this.getRows().get(i).getData().get( index - 1 ) ).equals( predicat ) ){
                datas.add( this.getRows().get(i) );
            }
        }
        datas = removeDoublons(datas);
        Relations r = new Relations( "Select" , this.getColumn() , datas );
        return r;
    }

    public Relations multiplication(Relations relationOne , Relations relationsTwo){
        Vector<Object> allColumn = merge( relationOne.getColumn() , relationsTwo.getColumn() );
        Vector<Row> datas = new Vector<Row>();
        for( int i = 0 ; i < (relationOne.getRows()).size() ; i++ ){
            for( int j = 0 ; j < (relationsTwo.getRows()).size() ; j++ ){
                Vector<Object> one = merge( relationOne.getRows().get(i).getData() , relationsTwo.getRows().get(j).getData() );
                Row row = new Row( one );
                datas.add( row );
            }
        }
        Relations r = new Relations( relationOne.createName(relationsTwo.getName()) , allColumn , datas );
        return r;
    }

    public Relations union( Relations one) throws Exception{
        Vector<Object> column = this.getColumn();
        Vector<Row> datas = new Vector<Row>();
        datas.addAll(this.getRows());
        if( this.checkLength(one) ){
            for( int i = 0 ; i < one.getRows().size(); i++){
                datas.add( one.getRows().get( i ) );
            }
            datas.addAll(one.getRows());
        }
        // mila alana ny doublons
        datas = removeDoublons( datas );
        Relations o = new Relations("Union",column,datas);
        return o;
    }

    public Relations join(Relations toJoin , String joinClause) throws Exception{
        if( toJoin == null || (joinClause==null || joinClause.equals("")) ){
            throw new Exception("Tsy afaka tanterahina ny fangatahinao");
        }
        Relations multi = multiplication( this , toJoin );
        Vector<Row> newRows = new Vector<Row>();
        Vector<Integer> index = multi.getColumnIndex( joinClause );
        for( int i = 0 ; i < multi.getRows().size() ; i++ ){
            Row e = multi.getRows().get(i);
            if( ( e.getData().get( index.get(0) - 1 ) ).equals( e.getData().get( index.get(1) - 1 ) ) ){
                newRows.add(e);
            }
        }
        Relations joins = new Relations( "Join" , multi.getColumn() , newRows );
        return joins;
    }

    public Relations difference( Relations toRemove ) throws Exception{
        if( this.getColumn().size() != toRemove.getColumn().size() ){
            throw new Exception(" Miala tsiny ! Tsy mitovy ny isan'ny fafana ampiasainao. Amarino ny zavatra ataonao");
        }
        Vector<Row> newData = new Vector<Row>();
        for( int i = 0 ; i < this.getRows().size() ; i++ ){
            boolean inside = false;
            for( int j = 0 ; j < toRemove.getRows().size() ; j++ ){
                if( checkIfSame( this.getRows().get(i).getData() , toRemove.getRows().get(j).getData() ) ){
                    inside = true;
                    break;
                }
            }
            if( !inside ) newData.add( this.getRows().get(i) ); 
        }
        Relations diff = new Relations( " Difference " , this.getColumn() , newData  );
        return diff;
    }

    public Relations intersection( Relations toIntersect ) throws Exception{
        if( this.getColumn().size() != toIntersect.getColumn().size() ){
            throw new Exception("Tsy mitovy ny firafitry ny fafana");
        }

        // Relations r1 = thi;
        Relations r2 = this.difference(toIntersect);
        Relations r3 = this.difference(r2);
        return r3;
    }

    public Relations division( Relations toDivide ) throws Exception{
        Vector<Object> newColumn = Relations.differenceColumn( this.getColumn() , toDivide.getColumn() );
        Relations r1 = this.projection( createCols(newColumn) , this  );
        Relations r2 = this.multiplication( toDivide , r1 );
        Relations r3 = r2.difference(this);
        Relations r4 = this.projection( createCols(newColumn) , r3 );
        Relations r5 = r1.difference(r4);
        
        return r5;
    }

// utilities

    // mila parsena io zavatra io
    
    // mila ananana zany ny objet de reference

    public Object parseObject(Object reference , Object toCast){
        if( reference.getClass() == Integer.class ){
            toCast = Integer.valueOf( String.valueOf(toCast) );
        }else if( reference.getClass() == Double.class ){
            toCast = Double.valueOf( String.valueOf( toCast ) );
        }
        return toCast;
    }

    public Vector<Object> merge( Vector<Object> one , Vector<Object> two ){
        Vector<Object> ones = new Vector<Object>();
        ones.addAll(one);
        ones.addAll(two);
        return ones;
    }
    
    public Vector<Integer> column(String[] columns) throws Exception{
        Vector<Integer> integer = new Vector<Integer>();
        for( String s : columns ){
            s = s.trim();
            for( int i = 0 ; i < this.getColumn().size() ; i++ ){
                String t = (String)this.getColumn().get(i);
                if( t.equalsIgnoreCase(s)  || s.equals("*")) {
                    integer.add(i);
                }
            }
        }
        if(integer.isEmpty() ) throw new Exception("Les colonnes n'existent pas");
        return integer;
    }

    public Vector<Integer> getColumnIndex(String cols){
        Vector<Integer> index = new Vector<Integer>(); 
        for( int i = 0 ; i < this.getColumn().size() ; i++ ){
            cols = cols.trim();
            String e = (String)this.getColumn().get(i); 
            if( e.equalsIgnoreCase(cols) ){
                index.add( i + 1 );
            }
        }
        return index;
    }

    public boolean checkIfSame( Vector<Object> one , Vector<Object> two ){
        boolean a = one.containsAll(two);
        boolean b = two.containsAll(one);
        return ( a && b);
    }

    public Vector<Row> removeDoublons(Vector<Row> toUse){ // phase experimental mila manao test bobaka be avy any 67
        Vector<Row> newRows = new Vector<Row>();
        for( int i = 0 ; i < toUse.size() ; i++ ){
            boolean inside = false;
            for( int j = 0; j < newRows.size() ; j++ ){
                if( checkIfSame( toUse.get(i).getData() , newRows.get(j).getData() ) ){
                    inside = true;
                    break;
                }
            }
            if( !inside ) newRows.add(toUse.get(i));
        }
        return newRows;
    }

    public static Vector<Object> differenceColumn(  Vector<Object> first , Vector<Object> second ){
        Vector<Object> newObjects = new Vector<Object>(first);
        newObjects.removeAll(second);
        return newObjects;
    }

    public String[] createCols( Vector<Object> cols){
        String[] columns = new String[ cols.size() ];
        for( int i = 0 ; i < cols.size() ; i++ ){
            columns[i] = String.valueOf( cols.get(i) );
        }
        return columns;
    }

// print informations
    public void showInfo(){
        this.printColumn();
        this.printRow();
    }
    public void printColumn(){
        for( int i = 0 ; i < this.getColumn().size() ; i++ ){
            System.out.print(this.getColumn().get(i)+"\t");
        }
        System.out.println();
    }
    public void printRow(){
        for( int i = 0 ; i < this.getRows().size() ; i++ ){
            for( int j = 0 ; j < this.getRows().get(i).getData().size() ; j++ ){
                System.out.print(this.getRows().get(i).getData().get(j) + " \t");
            }
            System.out.println();
        }
    }

    public String response(){
        String cols = "";
        String rows = "";
        for( int i = 0 ; i < this.getColumn().size() ; i++ ){
            cols += String.valueOf(this.getColumn().get(i)) + "\t";
        }
        cols+="\n";
        for( int i = 0 ; i < this.getRows().size() ; i++ ){
            for( int j = 0 ; j < this.getRows().get(i).getData().size() ; j++ ){
                rows += String.valueOf(this.getRows().get(i).getData().get(j)) + "\t";
            }
            rows += "\n";
        }
        cols = cols.concat(rows);
        return cols;
    }

    public String createName(String n ){
        return this.getName().concat(n);
    }
// getters and setters 

    public void setName(String n){
        this.name = n;
    }
    public String getName(){
        return this.name;
    }

    public void setColumn(Vector<Object> cols){
        for( int i = 0 ; i < cols.size() ; i++ ){
            ((String)cols.get(i)).toLowerCase();
        }
        this.colones = cols;
    }

    public void setRows(Vector<Row> rows){
        this.rows = rows;
    }

    public Vector<Object> getColumn(){
        return this.colones;
    }
    public Vector<Row> getRows(){
        return this.rows;
    }

}
