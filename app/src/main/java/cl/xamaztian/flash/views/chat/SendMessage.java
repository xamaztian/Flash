package cl.xamaztian.flash.views.chat;

import cl.xamaztian.flash.data.CurrentUser;
import cl.xamaztian.flash.data.Nodes;
import cl.xamaztian.flash.models.Chat;
import cl.xamaztian.flash.models.Message;

public class SendMessage {

    public void validateMessage(String message, Chat chat){
        if (message.trim().length() > 0){
            String email = new CurrentUser().email();
            Message msg = new Message();
            msg.setContent(message);
            msg.setOwner(email);

            String key = chat.getKey();

            new Nodes().messages(key).push().setValue(msg);
            new Nodes().userChat(chat.getUid()).child(key).child("notification").setValue(true);
        }
    }
}
