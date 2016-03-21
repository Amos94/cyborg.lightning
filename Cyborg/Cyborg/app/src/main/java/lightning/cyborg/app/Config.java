package lightning.cyborg.app;

/**
 * Created by Lincoln on 05/01/16.
 */
public class Config {

    // flag to identify whether to show single line
    // or multi line test push notification tray
    public static boolean appendNotificationMessages = true;

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    //key used for post filters
   public static final String USER_ID = "user_id";
    public static final String TYPE ="type";
    public static final String CHAT_ROOM_ID ="chat_room_id";



    // type of push messages
    public static final int PUSH_TYPE_CHATROOM = 1;
    //public static final int PUSH_TYPE_USER = 2;
    public static final int PUSH_TYPE_CHAT_REQUEST =3;

    // id to handle the notification in the notification try
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
}
