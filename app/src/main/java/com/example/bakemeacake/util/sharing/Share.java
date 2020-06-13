package com.example.bakemeacake.util.sharing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;

import com.example.bakemeacake.R;

import java.io.File;

public class Share extends Activity {
    String FILES_AUTHORITY = "com.example.bakemeacake.fileprovider";
    String shareTitle;
    File shareFile;
    Intent shareIntent;
    Uri shareUri;

    public void sendShareText(String shareText) {
        try {
            shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setType("text/plain")
                            .setText(shareText)
                            .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendShareHTML(String shareHtmlSubject,
                              String shareHtmlText,
                              String shareHtmlTo) {
        try {
            shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setType("text/html")
                            .setText(shareHtmlText)
                            .setSubject(shareHtmlSubject)
                            .addEmailTo(shareHtmlTo)
                            .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendShareFile(String filepath) {
        try {
            shareTitle = getString(R.string.share_generic);
            shareFile = new File(filepath);
            shareUri = FileProvider.getUriForFile(
                            getApplicationContext(),
                            FILES_AUTHORITY,
                            shareFile
                        );

            shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setStream(shareUri)
                            .getIntent();

            // Provide read access to target
            shareIntent.setData(shareUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the Share Intent
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent,shareTitle));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getShareText() {

    }

    public void getShareFile() {

    }
}
