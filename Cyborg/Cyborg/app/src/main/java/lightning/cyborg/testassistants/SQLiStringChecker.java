package lightning.cyborg.testassistants;

/**
 * Created by Name on 19/03/2016.
 */
public class SQLiStringChecker {

    public SQLiStringChecker(){

    }

    public boolean checkerFoo(String string){

        if(string.contains("=")) return false;
        else if(string.contains("%")) return false;
        else if(string.contains("'")) return false;
        else if(string.contains("\"")) return false;
        else if(string.contains(";")) return false;
        else if(string.contains(":")) return false;
        else return true;
    }


}
