package cl.xamaztian.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.xamaztian.flash.data.CurrentUser;
import cl.xamaztian.flash.data.EmailProcessor;
import cl.xamaztian.flash.data.Nodes;
import cl.xamaztian.flash.data.PhotoPreference;
import cl.xamaztian.flash.models.User;

public class UploadPhoto {
    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void ToFirebase(String path) {
        final CurrentUser currentUser = new CurrentUser();

        String folder = new EmailProcessor().sanitizedEmail(currentUser.email() + "/");
        String photoName = "avatar.jpg";
        String baseUrl = "gs://flash-58593.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);

        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               @SuppressWarnings("VisibleForTests") String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];

                new PhotoPreference(context).photoSave(url);

                User user = new User();
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.getUid());
                String key = new EmailProcessor().sanitizedEmail(currentUser.email());
                new Nodes().user(key).setValue(user);
            }
        });

    }
}
