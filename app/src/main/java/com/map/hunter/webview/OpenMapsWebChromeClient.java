package com.map.hunter.webview;
/* Copyright 2019 Thomas Schneider
 *
 * This file is a part of OpenMultiMaps
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * OpenMultiMaps is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with OpenMultiMaps; if not,
 * see <http://www.gnu.org/licenses>. */

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.map.hunter.MainActivity;


/**
 * Created by Thomas
 * Custom WebChromeClient
 */

public class OpenMapsWebChromeClient extends WebChromeClient implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private FrameLayout videoViewContainer;
    private CustomViewCallback videoViewCallback;

    private ToggledFullscreenCallback toggledFullscreenCallback;

    private final WebView webView;
    private final View activityNonVideoView;
    private final ViewGroup activityVideoView;
    private boolean isVideoFullscreen;
    private final Activity activity;


    public interface ToggledFullscreenCallback {
        void toggledFullscreen(boolean fullscreen);
    }

    public OpenMapsWebChromeClient(Activity activity, WebView webView, FrameLayout activityNonVideoView, ViewGroup activityVideoView) {
        this.activity = activity;
        this.isVideoFullscreen = false;
        this.webView = webView;
        this.activityNonVideoView = activityNonVideoView;
        this.activityVideoView = activityVideoView;
    }



    //FULLSCREEN VIDEO
    //Code from https://stackoverflow.com/a/16179544/3197259

    /**
     * Set a callback that will be fired when the video starts or finishes displaying using a custom view (typically full-screen)
     *
     * @param callback A VideoEnabledWebChromeClient.ToggledFullscreenCallback callback
     */
    public void setOnToggledFullscreen(ToggledFullscreenCallback callback) {
        this.toggledFullscreenCallback = callback;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin,
                                                   final GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, true);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        ((MainActivity)activity).setProgressDialog(newProgress);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (view instanceof FrameLayout) {
            if (((AppCompatActivity) activity).getSupportActionBar() != null)
                ((AppCompatActivity) activity).getSupportActionBar().hide();
            // A video wants to be shown
            FrameLayout frameLayout = (FrameLayout) view;
            View focusedChild = frameLayout.getFocusedChild();

            // Save video related variables
            isVideoFullscreen = true;
            this.videoViewContainer = frameLayout;
            this.videoViewCallback = callback;

            // Hide the non-video view, add the video view, and show it
            activityNonVideoView.setVisibility(View.INVISIBLE);
            activityVideoView.addView(videoViewContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            activityVideoView.setVisibility(View.VISIBLE);
            if (focusedChild instanceof android.widget.VideoView) {
                // android.widget.VideoView (typically API level <11)
                android.widget.VideoView videoView = (android.widget.VideoView) focusedChild;
                // Handle all the required events
                videoView.setOnCompletionListener(this);
                videoView.setOnErrorListener(this);
            } else {
                // Other classes, including:
                // - android.webkit.HTML5VideoFullScreen$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 11-18)
                // - android.webkit.HTML5VideoFullScreen$VideoTextureView, which inherits from android.view.TextureView (typically API level 11-18)
                // - com.android.org.chromium.content.browser.ContentVideoView$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 19+)

                // Handle HTML5 video ended event only if the class is a SurfaceView
                // Test case: TextureView of Sony Xperia T API level 16 doesn't work fullscreen when loading the javascript below
                if (webView != null && webView.getSettings().getJavaScriptEnabled() && focusedChild instanceof SurfaceView) {
                    // Run javascript code that detects the video end and notifies the Javascript interface
                    String js = "javascript:";
                    js += "var _ytrp_html5_video_last;";
                    js += "var _ytrp_html5_video = document.getElementsByTagName('video')[0];";
                    js += "if (_ytrp_html5_video != undefined && _ytrp_html5_video != _ytrp_html5_video_last) {";
                    {
                        js += "_ytrp_html5_video_last = _ytrp_html5_video;";
                        js += "function _ytrp_html5_video_ended() {";
                        {
                            js += "_VideoEnabledWebView.notifyVideoEnd();"; // Must match Javascript interface name and method of VideoEnableWebView
                        }
                        js += "}";
                        js += "_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);";
                    }
                    js += "}";
                    webView.loadUrl(js);
                }
            }
            // Notify full-screen change
            if (toggledFullscreenCallback != null) {
                toggledFullscreenCallback.toggledFullscreen(true);
            }
        }
    }

    // Available in API level 14+, deprecated in API level 18+
    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        if (((AppCompatActivity) activity).getSupportActionBar() != null)
            ((AppCompatActivity) activity).getSupportActionBar().show();
        // This method should be manually called on video end in all cases because it's not always called automatically.
        // This method must be manually called on back key press (from this class' onBackPressed() method).
        if (isVideoFullscreen) {
            // Hide the video view, remove it, and show the non-video view
            activityVideoView.setVisibility(View.INVISIBLE);
            activityVideoView.removeView(videoViewContainer);
            activityNonVideoView.setVisibility(View.VISIBLE);
            // Call back (only in API level <19, because in API level 19+ with chromium webview it crashes)
            if (videoViewCallback != null && !videoViewCallback.getClass().getName().contains(".chromium.")) {
                videoViewCallback.onCustomViewHidden();
            }

            // Reset video related variables
            isVideoFullscreen = false;
            videoViewContainer = null;
            videoViewCallback = null;

            // Notify full-screen change
            if (toggledFullscreenCallback != null) {
                toggledFullscreenCallback.toggledFullscreen(false);
            }
        }
    }

    // Video will start loading
    @Override
    public View getVideoLoadingProgressView() {
        return super.getVideoLoadingProgressView();
    }

    // Video finished playing, only called in the case of android.widget.VideoView (typically API level <11)
    @Override
    public void onCompletion(MediaPlayer mp) {
        onHideCustomView();
    }

    // Error while playing video, only called in the case of android.widget.VideoView (typically API level <11)
    @Override    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false; // By returning false, onCompletion() will be called
    }

}

