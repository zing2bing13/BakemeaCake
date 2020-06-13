package com.example.bakemeacake.util.sharing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

public class Share extends Activity {
    String FILES_AUTHORITY = "com.example.bakemeacake.fileprovider";

    public void sendShareText() {

    }

    public void sendShareFile(String filepath) {
        try {
            File file = new File(filepath);
            Uri uriToImage = FileProvider.getUriForFile(
                    getApplicationContext(),
                    FILES_AUTHORITY,
                    file);

            Intent shareIntent =
                    ShareCompat.IntentBuilder.from(this).setStream(uriToImage).getIntent();

            // Provide read access to target
            shareIntent.setData(uriToImage);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the Share Intent
            startActivity(Intent.createChooser(shareIntent,"Share image to..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getShareText() {

    }

    public void getShareFile() {

    }
}
