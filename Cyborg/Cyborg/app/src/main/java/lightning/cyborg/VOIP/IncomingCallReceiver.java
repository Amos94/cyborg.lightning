package lightning.cyborg.VOIP;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;

import lightning.cyborg.activity.CallActivity;
import lightning.cyborg.activity.ChatRoomActivity;

/**
 * Created by Amos Madalin Neculau on 26/02/2016.
 */
/*
    IncomingCallReceiver class helps the user receiving calls.
 */
public class IncomingCallReceiver extends BroadcastReceiver {
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * CallActivity.
     *
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    /*
    This SipAudioCall object will be instantiated later with a call via a context.
    The call will be instantiated with was made by other user.
    Remember, this is a receiver.
     */
    public static SipAudioCall incomingCall;

    @Override
    public void onReceive(Context context, Intent intent) {
        incomingCall = null;
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {

                        //give the user 30 seconds to respond to the call
                        call.answerCall(30);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            //Take the call activity from the CallActivity context
            //this allows the communication bewteen these two classes: IncomingCallReceiver and .activity.CallActivity
            CallActivity wtActivity = (CallActivity) context;

            //instantiating incomingCall by calling the takeAudioCall which helps two user initiating a call
            incomingCall = wtActivity.manager.takeAudioCall(intent, listener);
            //setting the listener made above. This will give 30 seconds to the user to respond to an incoming call.
            incomingCall.setListener(listener, true);

            //showIncomingCallGui(intent, context);
            wtActivity.call = incomingCall;
            //Possibility for the user to answer or reject the incomingCall
            answerIncomingCall();


        } catch (Exception e) {
            //Helper to detect bugs. Will print the error.
            e.printStackTrace();

            /*
            There might be a call also running.
            With this, we don't let the user having more than one call active.
            If another call is active, we close that call.
            */
            if (incomingCall != null) {
                e.printStackTrace();
                incomingCall.close();
            }
        }
    }

    /*
        This method will make an allow us to display an special GUI for the user in order to
        give him the possibility to answer or reject a call. The call is parse via context.
     */
    public void showIncomingCallGui(Intent intent, Context context) {

        Intent incomingCall = new Intent(context, IncomingGui.class);
        context.startActivity(incomingCall);
    }
    /*
    This method allow the user to answer a call, starting the audio between two users, and make some
    other default operations for them at the start of the call. Such as set the speaker to false.
     */
    public static void answerIncomingCall() {

        try {
            incomingCall.answerCall(30);
            incomingCall.startAudio();
            incomingCall.setSpeakerMode(false);

            if (incomingCall.isMuted()) {
                incomingCall.toggleMute();

            }
        } catch (Exception e) {

            System.out.println(e.toString());
        }

    }
    /*
    This method allow the user to reject a call, also that call will be closed, which means the
    other user won't wait for answering if he or she rejected the call.
     */

    public static void rejectIncomingCall() {

        try {
            if (incomingCall != null) {

                incomingCall.endCall();
                incomingCall.close();
            }

        } catch (Exception e) {

            System.out.println(e.toString());
        }
    }
}
