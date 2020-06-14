package com.example.bakemeacake.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class IncomingIntentHandler extends AppCompatActivity {

    // For future use
    // Handling for incoming data from other apps
    // Will accept text, html, photos at minimum
    // Maybe recipes in XML if there's time.
    // Probably will also be empty until refactoring stage.
    // UNTESTED AS OF YET

    private String FILES_AUTHORITY = "com.example.bakemeacake.fileprovider";
    private Intent Intent;
    private File recvFile;
    private String recvAction;
    private String recvTitle;
    private String recvType;
    private String recvText;
    private Uri uri;
    private Context mContext;

    public IncomingIntentHandler(Context context) {
        mContext = context;
    }

    public void handleIncoming(Intent recvIntent, Context context) {
        recvAction = recvIntent.getAction();
        recvType = recvIntent.getType();

        if (recvType != null) {
            if (recvIntent.ACTION_SEND.equals(recvAction)) {
                if ("text/plain".equals(recvType)) {
                    handleSendText(recvIntent); // Handle text being sent
                } else if (recvType.startsWith("image/")) {
                    handleSendImage(recvIntent); // Handle single image being sent
                }
            } else if (recvIntent.ACTION_SEND_MULTIPLE.equals(recvAction)) {
                if (recvType.startsWith("image/")) {
                    handleSendMultipleImages(recvIntent);   // Handle multiple images being sent
                }
            } else {
                // Handle other intents, such as being started from the home screen
            }
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }

}
