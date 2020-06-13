package com.example.bakemeacake.util;


import android.app.Activity;
import android.content.Context;
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

public class ShareHandler extends Activity {

    private String FILES_AUTHORITY = "com.example.bakemeacake.fileprovider";
    private ShareCompat.IntentReader intentReader;
    private File shareFile;
    private Intent sendIntent;
    private Intent shareIntent;
    private String shareTitle;
    private String shareType;
    private String shareText;
    private Uri shareUri;
    private Context mContext;

    public ShareHandler(Context context) {
        mContext = context;
    }

    public void shareText(String sendText) {
        try {
            shareTitle = mContext.getResources().getString(R.string.share_text);
            shareType = mContext.getResources().getString(R.string.sMime_text);

            sendIntent = new Intent();
            sendIntent.setType(shareType);
            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(Intent.EXTRA_TEXT, sendText);

            shareIntent = Intent.createChooser(sendIntent, shareTitle);

            if (shareIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareHTML(String sendHtmlTo,
                              String sendHtmlSubject,
                              String sendHtmlText) {
        try {
            shareTitle = mContext.getResources().getString(R.string.share_email);
            shareType = mContext.getResources().getString(R.string.sMime_html);

            sendIntent = new Intent();
            sendIntent.setType(shareType);
            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(Intent.EXTRA_EMAIL, sendHtmlTo);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, sendHtmlSubject);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sendHtmlText);

            shareIntent = Intent.createChooser(sendIntent, shareTitle);

            if (shareIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sharePhoto(String photoPath) {
        try {
            shareTitle = mContext.getResources().getString(R.string.share_photo);
            shareFile = new File(photoPath);

            // figure out the mime type for the file
            // for now will only send image/jpeg
            shareType = mContext.getResources().getString(R.string.sMime_jpeg);

            // get the uri for the specified photo
            shareUri = FileProvider.getUriForFile(mContext,FILES_AUTHORITY,shareFile);

            sendIntent = new Intent();
            sendIntent.setType(shareType);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, shareUri);

            shareIntent = Intent.createChooser(sendIntent, shareTitle);

            if (shareIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Passing context to ShareCompat is either impossible or very poorly documented.
    // These methods all throw null pointer errors for the base context
    // unless implemented in the main activity
    /*
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

            if (shareIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareIntent);
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
    */

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
