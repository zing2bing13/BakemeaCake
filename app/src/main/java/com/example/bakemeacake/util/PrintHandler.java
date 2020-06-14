package com.example.bakemeacake.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.bakemeacake.R;

import static android.content.ContentValues.TAG;

public class PrintHandler extends Activity {
    // Print handling; requires API version 19 (KitKat)
    private Context mContext;
    private Integer apiLevel;
    private WebView mWebView;

    public PrintHandler(Context context) {
        mContext = context;
    }

    public void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(mContext);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        photoPrinter.printBitmap("ic_launcher.png - test print", bitmap);
    }

    public void doWebViewPrint(String htmlDocument) {
        // check if the device is capable of printing
        apiLevel = Build.VERSION.SDK_INT;

        if (apiLevel < 21) {
            Toast.makeText(mContext, "Error: Printing not supported on this OS version.", Toast.LENGTH_LONG).show();
        } else if (apiLevel >= 21) {
            // Create a WebView object specifically for printing
            WebView webView = new WebView(mContext);

            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.i(TAG, "page finished loading " + url);
                    createWebPrintJob(view);
                    mWebView = null;
                }
            });

            // Generate an HTML document on the fly:
            /*
            String htmlTestDoc = mContext.getResources().getString(R.string.print_testDoc);
            webView.loadDataWithBaseURL(
                    null,
                    htmlTestDoc,
                    mContext.getResources().getString(R.string.sMime_html),
                    mContext.getResources().getString(R.string.html_encoding),
                    null);
             */

            webView.loadDataWithBaseURL(
                    null,
                    htmlDocument,
                    mContext.getResources().getString(R.string.sMime_html),
                    mContext.getResources().getString(R.string.html_encoding),
                    null);

            // Print an existing web page (remember to request INTERNET permission!):
            //webView.loadUrl(mContext.getResources().getString(R.string.print_testWeb));

            // Keep a reference to WebView object until you pass
            // the PrintDocumentAdapter to the PrintManager
            mWebView = webView;
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {
        boolean boolContinue = false;

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
        String jobName = mContext.getResources().getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        try {
            PrintJob printJob = printManager.print(jobName,
                    printAdapter, new PrintAttributes.Builder().build());

            // Save the job object for later status checking
            // documentation does not explain where at all this came from !!!
            //printJobs.add(printJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
