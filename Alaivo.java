package operation;
import java.util.*;
import database.*;
public class Alaivo{
    Relations currentRelation;
    String table = null, condition = null, columns = null, conditionValue = null , conditionColumn = null,
    joinColumn = null , relationJoin = null , multiTable1 = null , multiTable2 = null;
    public Alaivo(){}
    
    public String execute(MyDb database,String sisa) throws Exception{
    
        sisa = sisa.trim();
        // alaina daholo izay azo alaina
        this.setTable( this.getTable( sisa ) );        
        this.setColumns( this.getColumn( sisa ) );
        String[] cols = this.getColumns().split(",");
        this.setCurrentRelation( database.checkIfexist( this.getRelationName() ));
        this.joins( database , sisa );
        this.multiplications( database , sisa );
        this.divisions( database , sisa );
        this.unions( database , sisa );
        this.intersections( database , sisa );
        this.differences( database , sisa );
        this.wheres(sisa);
        this.setCurrentRelation( this.getCurrentRelations().projection( cols , this.getCurrentRelations() ) );
        this.getCurrentRelations().showInfo();
        return this.getCurrentRelations().response();
    }

    // andao ndray anao division

    public void resetTable(){
        this.setMultiTable1(null);
        this.setMultiTable2(null);
    }

    public boolean checkExist( Relations r1, Relations r2, MyDb database ) throws Exception{

        if( this.getMultiTable1() != null && !this.getMultiTable1().isEmpty() ){
            r1 = database.checkIfexist( this.getMultiTable1() );
            if( this.getMultiTable2() != null && !this.getMultiTable2().isEmpty() ){
                r2 = database.checkIfexist( this.getMultiTable2());
                if( r1 == null || r2 == null ){
                    return false;
                }
            }
        }
        return true;
    }

    public void multiplications( MyDb database , String sisa ) throws Exception {
        Relations rel1 = null , rel2 = null;
        this.getMultiplication(sisa);
        if( !checkExist( rel1 , rel2 , database ) ){
            throw new Exception(" Tsy misy tompoko ireo fafana ilainao ireo.");
        }
        if( this.getMultiTable1() != null && this.getMultiTable2()!=null ){
            rel1 = database.checkIfexist( this.getMultiTable1() );
            rel2 = database.checkIfexist( this.getMultiTable2() );
            this.setCurrentRelation( rel1.multiplication( rel1 , rel2 ) );
            this.resetTable();
        }
    }

    public void divisions( MyDb database , String sisa ) throws Exception{
        Relations rel1 = null, rel2 = null;
        this.getDivision(sisa);
        if( !checkExist( rel1 , rel2 , database ) ){
            throw new Exception(" Tsy misy tompoko ireo fafana ilainao ireo.");
        }
        if( this.getMultiTable1() != null && this.getMultiTable2()!=null ){
            rel1 = database.checkIfexist( this.getMultiTable1() );
            rel2 = database.checkIfexist( this.getMultiTable2() );
            this.setCurrentRelation( rel1.division( rel2 ) );
            this.resetTable();
        }
    }

    public void unions( MyDb database , String sisa ) throws Exception{
        Relations rel1 = null, rel2 = null;
        this.getUnion(sisa);
        if( !checkExist( rel1 , rel2 , database ) ){
            throw new Exception(" Tsy misy tompoko ireo fafana ilainao ireo.");
        }
        if( this.getMultiTable1() != null && this.getMultiTable2()!=null ){
            rel1 = database.checkIfexist( this.getMultiTable1() );
            rel2 = database.checkIfexist( this.getMultiTable2() );
            this.setCurrentRelation( rel1.union( rel2 ) );
            this.resetTable();
        }
    }

    public void intersections( MyDb database , String sisa ) throws Exception{
        Relations rel1 = null, rel2 = null;
        this.getIntersection(sisa);
        if( !checkExist( rel1 , rel2 , database ) ){
            throw new Exception(" Tsy misy tompoko ireo fafana ilainao ireo.");
        }
        if( this.getMultiTable1() != null && this.getMultiTable2()!=null ){
            rel1 = database.checkIfexist( this.getMultiTable1() );
            rel2 = database.checkIfexist( this.getMultiTable2() );
            this.setCurrentRelation( rel1.intersection( rel2 ) );
            this.resetTable();
        }
    }
    public void differences( MyDb database , String sisa ) throws Exception{
        Relations rel1 = null, rel2 = null;
        this.getDifference(sisa);
        if( !checkExist( rel1 , rel2 , database ) ){
            throw new Exception(" Tsy misy tompoko ireo fafana ilainao ireo.");
        }
        if( this.getMultiTable1() != null && this.getMultiTable2()!=null ){
            rel1 = database.checkIfexist( this.getMultiTable1() );
            rel2 = database.checkIfexist( this.getMultiTable2() );
            this.setCurrentRelation( rel1.difference( rel2 ) );
            this.resetTable();
        }
    }


    public void getMultiplication(String sisa){
        String[] splited = sisa.split(" ");
        for( int i = 0; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.MIRA ) &&  i + 1 < splited.length && i - 1 >= 0 && !KeyWord.isKeyword( splited[ i + 1 ] ) && !KeyWord.isKeyword( splited[ i - 1 ] ) ){
                this.setMultiTable1( splited[ i - 1 ] );
                this.setMultiTable2( splited[ i + 1 ] );
                return;
            }
        }
    }
    public void getDivision(String sisa){
        String[] splited = sisa.split(" ");
        for( int i = 0; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.ZARAO ) &&  i + 1 < splited.length && i - 1 >= 0 && !KeyWord.isKeyword( splited[ i + 1 ] ) && !KeyWord.isKeyword( splited[ i - 1 ] ) ){
                this.setMultiTable1( splited[ i - 1 ] );
                this.setMultiTable2( splited[ i + 1 ] );
                return;
            }
        }
    }

    public void getUnion(String sisa){
        String[] splited = sisa.split(" ");
        for( int i = 0; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.MIRAY ) &&  i + 1 < splited.length && i - 1 >= 0 && !KeyWord.isKeyword( splited[ i + 1 ] ) && !KeyWord.isKeyword( splited[ i - 1 ] ) ){
                this.setMultiTable1( splited[ i - 1 ] );
                this.setMultiTable2( splited[ i + 1 ] );
                return;
            }
        }
    }

    public void getIntersection(String sisa){
        String[] splited = sisa.split(" ");
        for( int i = 0; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.IRAISANY ) &&  i + 1 < splited.length && i - 1 >= 0 && !KeyWord.isKeyword( splited[ i + 1 ] ) && !KeyWord.isKeyword( splited[ i - 1 ] ) ){
                this.setMultiTable1( splited[ i - 1 ] );
                this.setMultiTable2( splited[ i + 1 ] );
                return;
            }
        }
    }

    public void getDifference(String sisa){
        String[] splited = sisa.split(" ");
        for( int i = 0; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.ANALAO ) &&  i + 1 < splited.length && i - 1 >= 0 && !KeyWord.isKeyword( splited[ i + 1 ] ) && !KeyWord.isKeyword( splited[ i - 1 ] ) ){
                this.setMultiTable1( splited[ i - 1 ] );
                this.setMultiTable2( splited[ i + 1 ] );
                return;
            }
        }
    }


    public void joins(MyDb database , String sisa ) throws Exception{
        Relations joinRelation = null;
        this.setRelations( this.getJoinRelation( sisa ) );
        this.setJoinColumn( this.getColumnJoin( sisa ) );
        if( !this.getRelationJoin().isEmpty() ){
            joinRelation = database.checkIfexist( this.getRelationJoin() );
            if( !this.getJoinColumn().isEmpty() ){
                this.setCurrentRelation(this.getCurrentRelations().join( joinRelation , this.getJoinColumn()));
            }
        }
    }

    public void setConditions(String sisa) throws Exception{
        String notSplittedConditions = this.checkWhereClause(sisa);
        String[] splited = this.splitCondition(notSplittedConditions);
        if( splited.length > 1 ){
            this.setConditionColumn(splited[0]);
            this.setConditionValue(splited[1]);
            return;
        }else if( splited.length == 1 ){
            return;
        }
        throw new Exception(" Amarino ny condition nosoratanao tompoko ");
    }

    public void wheres(String sisa) throws Exception{
        this.setConditions(sisa);
        if( this.getConditionColumn() != null && !this.getConditionColumn().isEmpty() ){
            if( this.getConditionValue() != null && !this.getConditionValue().isEmpty() ){
                this.setCurrentRelation( this.getCurrentRelations().select( this.getConditionValue() , this.getConditionColumn() ) );
                // System.out.println("I'm here");
            }
        }
    }
    
    public String getJoinRelation(String toSearch) throws Exception{
        String[] splited = toSearch.split(" ");
        for( int i = 0 ; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase(KeyWord.ATAMBARO) && i + 1 < splited.length  && !KeyWord.isKeyword( splited[ i + 1 ] ) ){
                return splited[ i + 1 ];
            }else if( splited[i].equalsIgnoreCase( KeyWord.ATAMBARO ) && i + 1 < splited.length  && KeyWord.isKeyword(splited[ i + 1 ]) ){
                throw new Exception(" Misy diso ny soratanao ");
            }
        }
        return "";
    }

    public String getColumnJoin(String toSearch) throws Exception{
        String[] splited = toSearch.split(" ");
        for( int i = 0 ; i < splited.length ; i++ ){
            if( splited[i].equalsIgnoreCase( KeyWord.AMINY ) && i + 1 < splited.length  && !KeyWord.isKeyword( splited[ i + 1 ] ) ){
                return splited[ i + 1 ];
            }else if( splited[i].equalsIgnoreCase( KeyWord.AMINY ) && i + 1 < splited.length  && KeyWord.isKeyword(splited[ i + 1 ]) ){
                throw new Exception("Amarino ny fehezanteny nosoratanao");
            }
        }
        return "";
    }

    public String getTable(String e) throws Exception{
        String[] a = e.split(" ");
        for( int i = 0 ; i < a.length ; i++ ){
            if( a[i].equalsIgnoreCase( KeyWord.ANATY ) && i + 1 < a.length  && !KeyWord.isKeyword(a[ i + 1 ]) ){
                return a[ i + 1 ];
            }else if( a[i].equalsIgnoreCase( KeyWord.ANATY ) && i + 1 < a.length  && KeyWord.isKeyword(a[ i + 1 ]) ){
                throw new Exception(" Misy diso ny soratanao : adino ny Fafana afaran'ny \" ANATY \" ");
            }
        }
        return "";
    }

    public String getColumn(String e) throws Exception{
        String[] a = e.split(" ");
        String cols = "";
        for( int i = 0 ; i < a.length ; i++ ){
            if( a[i].equalsIgnoreCase( KeyWord.ANATY ) && i - 1 >= 0  && !KeyWord.isKeyword( a[ i - 1 ] ) ){
                cols = a[ i - 1 ];
                break;
            }else if( a[i].equalsIgnoreCase( KeyWord.ANATY ) && i - 1 >= 0  && KeyWord.isKeyword(a[ i - 1 ]) ){
                throw new Exception(" Misy diso ny soratanao tompoko");
            }
        }
        return cols;
    }

    public String checkWhereClause(String e) throws Exception{
        String[] a = e.split(" ");
        for( int i = 0 ; i < a.length ; i++ ){
            if( a[i].equalsIgnoreCase( KeyWord.IZAY ) && i + 1 < a.length  && !KeyWord.isKeyword( a[ i + 1 ] ) ){
                return a[ i + 1 ];
            }else if( a[i].equalsIgnoreCase(KeyWord.IZAY) && ( i + 1 >= a.length || KeyWord.isKeyword( a[ i + 1 ] ))){
                throw new Exception("Misy diso afaran'ny \"Aiza\" ");
            }
        }
        return "";
    }

    public String[] splitCondition(String notSplitted) throws Exception{
        String condition = notSplitted.trim();
        String[] condSplited = condition.split("=|>=|<=|>|<");
        if(condSplited.length == 0){
            throw new Exception("Diso ny condition anao");
        }
        return condSplited;
        // rehefa nsplitena de alaina : ny nom no [0] dia ny valeur no [1]
    }

// getters and setters
    public void setMultiTable1(String tb1){
        this.multiTable1 = tb1;
    }
    public void setMultiTable2(String tb2){
        this.multiTable2 = tb2;
    }
    public void setTable(String name){
        this.table = name;
    }
    public void setCondition(String cond){
        this.condition = cond;
    }
    public void setColumns(String cols){
        this.columns = cols;
    }
    public void setConditionValue(String condV){
        this.conditionValue = condV;
    }
    public void setConditionColumn(String condC){
        this.conditionColumn = condC;
    }
    public void setJoinColumn(String joinC){
        this.joinColumn = joinC;
    }
    public void setRelations(String realJ){
        this.relationJoin = realJ;
    }
    public void setCurrentRelation(Relations c){
        this.currentRelation = c;
    }

    public Relations getCurrentRelations(){     return this.currentRelation;}
    public String getRelationName(){            return this.table;}
    public String getCondition(){               return this.condition;}
    public String getColumns(){                 return this.columns;}
    public String getConditionValue(){          return this.conditionValue;}
    public String getConditionColumn(){         return this.conditionColumn;}
    public String getJoinColumn(){              return this.joinColumn;}
    public String getRelationJoin(){            return this.relationJoin;}
    public String getMultiTable1(){             return this.multiTable1;}
    public String getMultiTable2(){             return this.multiTable2;}

}