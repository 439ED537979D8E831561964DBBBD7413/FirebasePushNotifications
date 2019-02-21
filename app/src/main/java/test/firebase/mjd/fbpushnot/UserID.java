package test.firebase.mjd.fbpushnot;

import android.support.annotation.NonNull;

public class UserID {

    public String userID;

    public <T extends UserID> T withId(@NonNull final String id) {
        this.userID = id;
        return (T) this;
    }


}
