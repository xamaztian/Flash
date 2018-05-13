package cl.xamaztian.flash.views.login;

import com.google.firebase.auth.FirebaseUser;

import cl.xamaztian.flash.data.CurrentUser;

/**
 * Created by Xamaztian on 12-05-2018.
 */

public class LoginValidator {
    private LoginCallback callback;

    public LoginValidator(LoginCallback loginCallback){
        callback = loginCallback;
    }

    public void checkLogin()
    {
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();

        if(currentUser != null)
        {
            callback.logged();
        }
        else
        {
            callback.signUp();
        }
    }
}
