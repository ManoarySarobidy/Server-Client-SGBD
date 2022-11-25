package operation;
import database.*;
import java.util.*;
public class Soloy{
    public void execute(MyDb database,String sisa) throws Exception{
        // 1-On va verifier si la table existe
        sisa = sisa.trim();
        String table = getTable(sisa);
        String condition = checkWhereClause(sisa);
        String columns = getColumn(sisa);
        Relations ilaina = database.checkIfexist(table);
        Relations vaovao = ilaina.projection(columns.split(" ") , ilaina);
        // mila jerena indray hoe misy condition ve sa tsia
        System.out.println( "Fafana : " + table );
        System.out.println( "Condition : " + condition );
        System.out.println( "Colones : " + columns );
        vaovao.showInfo();
        // System.out.println( "Relations : " + vaovao );
    }
    public String getTable(String e) throws Exception{
        String[] a = e.split(" ");
        for( int i = 0 ; i < a.length ; i++ ){
            if( (a[i].toLowerCase()).equals((KeyWord.SOLOY).toLowerCase()) && i + 1 < a.length  && !KeyWord.isKeyword(a[i+1]) ){
                return a[i+1];
            }
        }
        throw new Exception(" Misy diso ny soratanao : adino ny Fafana afaran'ny \" ANATY \" ");
        // return null;
    }

    public String getColumn(String e){
        String[] a = e.split(" ");
        String cols = "";
        for( int i = 0 ; i < a.length ; i++ ){
            if( (a[i].toLowerCase()).equals((KeyWord.ANATY).toLowerCase()) && i - 1 >= 0  && !KeyWord.isKeyword(a[i-1]) ){
                cols = a[i-1];
                break;
            }
        }
        return cols;
    }

    public String getValue(String e){
        String[] a = e.split(" ");
        String cols = "";
        for( int i = 0 ; i < a.length ; i++ ){
            if( (a[i].toLowerCase()).equals((KeyWord.SOLONY).toLowerCase()) && i - 1 >= 0  && !KeyWord.isKeyword(a[i-1]) ){
                cols = a[i-1];
                break;
            }
        }
        return cols;
    }

    public String checkWhereClause(String e) throws Exception{
        String[] a = e.split(" ");
        for( int i = 0 ; i < a.length ; i++ ){
            if( ( a[i].toLowerCase()).equals((KeyWord.IZAY).toLowerCase()) && i + 1 < a.length  && !KeyWord.isKeyword(a[i+1]) ){
                return a[i+1];
            }else if(( a[i].toLowerCase()).equals((KeyWord.IZAY).toLowerCase()) && (i+1>=a.length || KeyWord.isKeyword(a[i+1]))){
                throw new Exception("Misy diso afaran'ny \"Aiza\" ");
            }
        }
        return "";
    }
}