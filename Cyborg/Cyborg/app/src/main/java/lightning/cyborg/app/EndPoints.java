package lightning.cyborg.app;

/**
 * Created by Lincoln on 06/01/16.
 */
public class EndPoints {
    // localhost url
    //public static final String BASE_URL = "http://192.168.0.101/gcm_chat/v1";

    public static final String BASE_URL = "http://projectruncyborg.esy.es/v1";
    //public static final String LOGIN = BASE_URL + "/user/login";
    public static final String USER = BASE_URL + "/user/_ID_";
    //  public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
    //  public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
    //   public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";


    public static final String base = "http://nashdomain.esy.es";
    //public static final String BASE_URL = "http://projectruncyborg.esy.es";
    public static final String LOGIN = base + "/temp_User_Login.php";
    public static final String USERr = base +"/updateGcmID.php";
    //public static final String CHAT_ROOMS = "http://projectruncyborg.esy.es/getAllChatRooms.php";
    public static final String CHAT_THREAD = base +"/temp_GetSingleRoom.php";
    public static final String CHAT_ROOM_MESSAGE = base+"/temp_send.php";
    public static final String CHAT_ROOMS = base + "/getNormalChatRooms.php";
    public static final String REQUEST_RESPONSE = base+"/requestHandler.php";
    public static final String ADD_FREIND = base+"/convertChatRoom.php";
    public static final String ADD_INTERESTS = base+"/interests_insert.php";
    public static final String GET_INTERESTS = base+"/interests_get_user.php";

}
