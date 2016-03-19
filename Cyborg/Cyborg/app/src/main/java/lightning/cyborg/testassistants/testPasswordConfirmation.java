package lightning.cyborg.testassistants;

/**
 * Created by Name on 19/03/2016.
 */
public class testPasswordConfirmation {
    
    public testPasswordConfirmation(){

    }

    public static boolean testPasswordValidationParser(String password, String confirmPassword) {

            boolean valid_pass1=true;
            boolean valid_pass2=true;
            SQLiStringChecker checker = new SQLiStringChecker();

            if(checker.checkerFoo(password)==false) valid_pass1=false;
            if(checker.checkerFoo(confirmPassword)== false) valid_pass2=false;

            if ((password.toString().equals(confirmPassword.toString())&&valid_pass1==true) && ((!password.equals("") || !confirmPassword.equals("")))&&valid_pass2==true) {
                return true;
            }
           // passwordET.setError("Please Enter same password");
            return false;
        }
    public boolean validName(String x){

        if(x.equals("")){
           // nameET.setError("Please Enter Name");
            return false;
        }

        return true;
    }
}
