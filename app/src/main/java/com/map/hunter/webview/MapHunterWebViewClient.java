package com.map.hunter.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import org.apache.commons.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import com.map.hunter.MainActivity;
import com.map.hunter.R;
import com.map.hunter.helper.Helper;
import static android.content.Context.MODE_PRIVATE;

public class MapHunterWebViewClient extends WebViewClient {

    private final Activity activity;
    private final CoordinatorLayout rootView;

    public MapHunterWebViewClient(Activity activity){
        this.activity = activity;
        rootView = activity.findViewById(R.id.main_layout);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {

        if (url.contains("google-analytics") || url.contains("google.com") || url.contains("gstatic.com") ) {
            ByteArrayInputStream nothing = new ByteArrayInputStream("".getBytes());
            return new WebResourceResponse("text/javascript", "utf-8", nothing);
        }
        else{
            return super.shouldInterceptRequest(view, url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        String title = Helper.getTitle(activity, url);
        activity.setTitle(title);
        if( MainActivity.fromMenu){
            MainActivity.lastVisited = url;
            MainActivity.canGoBack = true;
            MainActivity.fromMenu = false;
        }else{
            MainActivity.canGoBack = false;
        }

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).showProgressDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request,
                                WebResourceError error) {
        if (!isConnected()) {
            final Snackbar snackBar = Snackbar.make(rootView, activity.getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction(activity.getString(R.string.enable_data), v -> {
                activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                view.loadUrl("javascript:window.location.reload( true )");
                snackBar.dismiss();
            });
            snackBar.show();
        }

        super.onReceivedError(view, request, error);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedHttpError(WebView view,
                                    WebResourceRequest request, WebResourceResponse errorResponse) {
        if (!isConnected()) {
            final Snackbar snackBar = Snackbar.make(rootView, activity.getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction(activity.getString(R.string.enable_data), v -> {
                activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                view.loadUrl("javascript:window.location.reload( true )");
                snackBar.dismiss();
            });
            snackBar.show();
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } else {
            return false;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Helper.recordLocationFromUrl(activity,  url);
        ((MainActivity)activity).hideProgressDialog();
    }
}
