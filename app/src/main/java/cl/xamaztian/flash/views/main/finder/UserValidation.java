package cl.xamaztian.flash.views.main.finder;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.xamaztian.flash.data.CurrentUser;
import cl.xamaztian.flash.data.EmailProcessor;
import cl.xamaztian.flash.data.Nodes;
import cl.xamaztian.flash.data.PhotoPreference;
import cl.xamaztian.flash.models.Chat;
import cl.xamaztian.flash.models.User;

public class UserValidation {

    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email) {
        if (email.trim().length() > 0) {
            if (email.contains("@")) {

                String currentEmail = new CurrentUser().email();
                if (!email.equals(currentEmail)) {
                    findUser(email);
                } else {
                    callback.error("¿Chat contigo mismo?");
                }
            } else {
                callback.error("Email mal escrito");
            }
        } else {
            callback.error("Se necesita email");
        }
    }

    private void findUser(String email) {
        new Nodes().user(new EmailProcessor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User otherUser = dataSnapshot.getValue(User.class);
                if (otherUser != null)
                    createChats(otherUser);
                else
                    callback.notFound();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createChats(User otherUser) {
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();
        String key = new EmailProcessor().keyEmails(otherUser.getEmail());
        String currentUid = currentUser.getUid();
        String otherUid = otherUser.getUid();

        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setNotification(true);
        currentChat.setReceiver(otherUser.getEmail());
        currentChat.setUid(otherUid);

        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setNotification(true);
        otherChat.setReceiver(currentUser.getEmail());
        otherChat.setUid(currentUid);

        new Nodes().userChat(currentUid).child(key).setValue(currentChat);
        new Nodes().userChat(otherUid).child(key).setValue(otherChat);

        callback.success();
    }
}
