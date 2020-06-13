package com.example.bakemeacake.util.sharing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;

import com.example.bakemeacake.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Share extends Activity {
    String FILES_AUTHORITY = "com.example.bakemeacake.fileprovider";
    ShareCompat.IntentReader intentReader;
    File shareFile;
    Intent shareIntent;
    String shareTitle;
    String shareType;
    String shareText;
    Uri shareUri;

    public void sendShareText(String sendText) {
        try {
            shareType = getString(R.string.sMime_text);

            shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setType(shareType)
                            .setText(sendText)
                            .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendShareHTML(String sendHtmlSubject,
                              String sendHtmlText,
                              String sendHtmlTo) {
        try {
            shareType = getString(R.string.sMime_html);

            shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setType(shareType)
                            .setText(sendHtmlText)
                            .setSubject(sendHtmlSubject)
                            .addEmailTo(sendHtmlTo)
                            .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendShareFile(String sendFile) {
        try {
            shareTitle = getString(R.string.share_generic);
            shareFile = new File(sendFile);

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
        try {
            intentReader = ShareCompat.IntentReader.from(this);

            if (intentReader.isShareIntent()) {
                shareType = intentReader.getType();
                shareText = intentReader.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // example method to receive HTML data from a ShareIntent and start creating an email.
    public void getShareHtml() {
        try {
            intentReader = ShareCompat.IntentReader.from(this);

            if (intentReader.isShareIntent()) {
                String[] emailTo = intentReader.getEmailTo();
                String subject = intentReader.getSubject();
                String text = intentReader.getHtmlText();

                // compose an email
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getShareFile() {
        boolean recvFile;

        try {
            intentReader = ShareCompat.IntentReader.from(this);
            shareType = intentReader.getType();
            shareUri = intentReader.getStream();
            recvFile = isValidImageType(shareType);

            if (recvFile) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(shareUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // Do something with the file here

                } catch (FileNotFoundException fe) {
                    // inform the user that things have gone horribly wrong.
                    fe.printStackTrace();
                }
            } else {
                // inform the user that the file type is not valid for this workflow.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidImageType(String fileType) {
        // used to determine if we should process the incoming file
        // will not accept anything but images
        // would use values defined in strings.xml, but requires constants...
        boolean isValid = false;

        switch (fileType) {
            case "image/bmp":
                isValid = true;
                break;
            case "image/gif":
                isValid = true;
                break;
            case "image/jpeg":
                isValid = true;
                break;
            case "image/png":
                isValid = true;
                break;
            case "image/svg+xml":
                isValid = true;
                break;
            case "image/tiff":
                isValid = true;
                break;
            case "image/webp":
                isValid = true;
                break;
            default:
                //do nothing
                break;
        }

        return isValid;
    }
}
