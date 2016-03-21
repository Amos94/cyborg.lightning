package lightning.cyborg.VOIP;

/**
 * Created by Amos Madalin Neculau on 25/02/2016.
 */

/*
This is a helper class. Help us keep the code clean and parse SIP information between classes.
 */
public class UserInformation {

    /*
    Variables for SIP credentials and SIP server
     */
    protected String username;
    protected String password;
    protected String server;

/*
Constructor where will initialize the variables with the SIP credentials and server.
 */

    public UserInformation(String username, String password, String server){
        this.username = username;
        this.password = password;
        this.server = server;
    }
    /*
   The info might be empty, if the user don't want/ have a SIP account.
   We also handle this.
     */
    public UserInformation(){
        username = "";
        password = "";
        server = "";
    }

    //Return the SIP username
    public String getUsername(){
        return username;
    }

    //Return the SIP password
    public String getPassword(){
        return password;
    }

    //Return the SIP server
    public String getServer(){
        return server;
    }

    /*
    If you want to set another SIP username, you can simply do this using this method.
    Easy to use.
    Update the database using the some methods in the .activity package.
     */
    public void setUsername(String newUsername){
        username = newUsername;
    }

    /*
    If you want to set another SIP password, you can simply do this using this method.
    Easy to use.
    Update the database using the some methods in the .activity package.
     */
    public void setPassword(String newPassword){
        password = newPassword;
    }

    /*
   In case the client will want to change the SIP provider.
   Easy to use.
   Update the database using the some methods in the .activity package.
    */
    public void setServer(String newServer){
        server = newServer;
    }


}
