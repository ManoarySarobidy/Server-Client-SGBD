package operation;
import java.util.*;
public class KeyWord{

    // fanombohany
   static String ALAIVO = "Alaivo";
   static String SOLOY = "Soloy";
   static String FAFAO = "Fafao";

    // ANATY //
    static String ANATY = "anaty";
    
    // Condition // fahamarinana
    static String IZAY = "Izay";

    // JOIN and ON
    static String ATAMBARO = "Atambaro";
    static String AMINY = "Amin'ny";
    
    // soloy keyWord
    static String SOLONY = "Solony";
    static String NY = "Ny";

    // DIVISION
    static String ZARAO = "Zaraina";

    // DIFFERENCE
    static String ANALAO = "Analana";

    //UNION
    static String MIRAY = "Miray";

    // INTERSECTION
    static String IRAISANY = "Iraisany"; 

    // MULTIPLICATION
    static String MIRA = "Mira";

    public static String[] getFirst(){
        String[] keyWord = new String[3];
        keyWord [0] = ALAIVO; 
        keyWord [1] = SOLOY; 
        keyWord [2] = FAFAO;
        return keyWord; 
    }
    public static Vector<String> getAll(){
        Vector<String> keyWord = new Vector<String>();
        keyWord.add(ALAIVO);
        keyWord.add(SOLOY);
        keyWord.add(FAFAO);
        keyWord.add(ANATY);
        keyWord.add(IZAY);
        keyWord.add(SOLONY);
        keyWord.add(NY);
        keyWord.add(ATAMBARO);
        keyWord.add(AMINY);
        keyWord.add(ZARAO);
        keyWord.add(ANALAO);
        keyWord.add(MIRAY);
        keyWord.add(IRAISANY);
        keyWord.add(MIRA);
        return keyWord; 
    }
    public static String isFirst(String e){
        String[] key = KeyWord.getFirst();
        for(String r : key){
            if( (r.toLowerCase()).equals(e.toLowerCase()) ){
                return r;
            }
        }
        return null;
    }
    public static boolean isKeyword(String e){
        Vector<String> keyWord = getAll();
        for( int i = 0 ; i < keyWord.size() ; i++ ){
            if( (e.toLowerCase()).equals(keyWord.get(i).toLowerCase()) ){
                return true;
            }
        }
        return false;
    }
}