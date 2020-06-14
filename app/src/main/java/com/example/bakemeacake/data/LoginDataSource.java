package com.example.bakemeacake.data;

import android.content.Context;

import com.example.bakemeacake.DatabaseHandler;
import com.example.bakemeacake.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(Context context, String username, String password) {
        DatabaseHandler myDB = null;
        try {
            myDB = new DatabaseHandler(context);
            LoggedInUser user = myDB.getUser(username);

            if(user.getPassword().matches(password)) {
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
        return new Result.Error(new Exception("Incorrect username or password"));

    }

    public void logout() {
        // TODO: revoke authentication
    }
}
