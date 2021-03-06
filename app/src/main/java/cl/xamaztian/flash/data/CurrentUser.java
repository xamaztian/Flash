package cl.xamaztian.flash.data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Xamaztian on 26-04-2018.
 */

public class CurrentUser {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String email() {
        return currentUser.getEmail();
    }

    public Uri getPhoto() {
        return currentUser.getPhotoUrl();
    }



    public String getUid()
    {
        return currentUser.getUid();
    }
}
