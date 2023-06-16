package com.example.greenplants_pak;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;


public class CalculatorView extends Activity {

    private WebView webView;
    Activity activity ;
    private ProgressDialog progDailog;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_view);



        new AlertDialog.Builder(this)
                .setTitle("Nursery")
                .setMessage("Do You Want to View Nearest Nursery ")
                .setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            activity = CalculatorView.this;

                            progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
                            progDailog.setCancelable(true);







                            webView = (WebView) findViewById(R.id.webview);

                            new AdBlockerWebView.init(CalculatorView.this).initializeWebView(webView);


                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setLoadWithOverviewMode(true);
                            webView.getSettings().setUseWideViewPort(true);
                            webView.setWebViewClient(new WebViewClient(){

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    progDailog.show();
                                    view.loadUrl(url);

                                    return true;
                                }
                                @Override
                                public void onPageFinished(WebView view, final String url) {
                                    progDailog.dismiss();

                                }

                                @SuppressWarnings("deprecation")
                                @Override
                                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                                    return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                                            super.shouldInterceptRequest(view, url);

                                }
                                @Override
                                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                    super.onReceivedError(view, errorCode, description, failingUrl);
                                    Toast.makeText(getApplicationContext(), ""+description.toLowerCase(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            webView.loadUrl("https://www.google.com/search?tbs=lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk02hf1TBBy6zmgZet0r4l2YGDWL2Tg:1618762717416&q=nearest+nursery+to+my+location&rflfq=1&num=10&ved=2ahUKEwiQ5_yFmYjwAhUOa8AKHR2hAc4QtgN6BAgEEAc#rlfi=hd:;si:;mv:[[34.99062900407325,75.3467196015625],[32.70064469492611,70.1501863984375],null,[33.85331055202902,72.748453],8]");



                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() , "net slow" , Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("Full", true));
                        try {

                            activity = CalculatorView.this;
                            progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
                            progDailog.setCancelable(true);







                            webView = (WebView) findViewById(R.id.webview);

                            new AdBlockerWebView.init(CalculatorView.this).initializeWebView(webView);


                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setLoadWithOverviewMode(true);
                            webView.getSettings().setUseWideViewPort(true);
                            webView.setWebViewClient(new WebViewClient(){

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    progDailog.show();
                                    view.loadUrl(url);

                                    return true;
                                }
                                @Override
                                public void onPageFinished(WebView view, final String url) {
                                    progDailog.dismiss();

                                }

                                @SuppressWarnings("deprecation")
                                @Override
                                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                                    return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                                            super.shouldInterceptRequest(view, url);

                                }
                                @Override
                                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                    super.onReceivedError(view, errorCode, description, failingUrl);
                                    Toast.makeText(getApplicationContext(), ""+description.toLowerCase(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            webView.loadUrl("https://www.google.com/search?tbs=lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk02hf1TBBy6zmgZet0r4l2YGDWL2Tg:1618762717416&q=nearest+nursery+to+my+location&rflfq=1&num=10&ved=2ahUKEwiQ5_yFmYjwAhUOa8AKHR2hAc4QtgN6BAgEEAc#rlfi=hd:;si:;mv:[[34.99062900407325,75.3467196015625],[32.70064469492611,70.1501863984375],null,[33.85331055202902,72.748453],8]");



                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext() , "net slow" , Toast.LENGTH_LONG).show();
                        }

                    }


                }).create().show();




    }
}

