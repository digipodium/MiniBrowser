package rav.minibrowser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import rav.minibrowser.Contract.HistoryContract;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        if (savedInstanceState!=null)
        {
            webView.restoreState(savedInstanceState);
        }
        else
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);
            webView.setWebViewClient(new ourViewClient());
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view,int newProgress){
                    super.onProgressChanged(view,newProgress);
                    progressBar.setProgress(newProgress);
                    if (newProgress<100 && progressBar.getVisibility()== ProgressBar.GONE){
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    if (newProgress == 100)
                    {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                }

        });

        }

        editText = (EditText) findViewById(R.id.editText);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);
        webView.loadUrl("http://www.google.com");
        editText.setText("http://www.google.com");

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                String userInp = editText.getText().toString();
                if(userInp.contains("http://")){
                    webView.loadUrl(userInp);
                }else {
                    webView.loadUrl("http://" + userInp);
                    editText.setText(webView.getUrl());
                }
                savehistory();

            }
        });
    }

    private void savehistory() {
        HistoryContract contract = new HistoryContract(this);

        String url = editText.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            return;
        }

        long response = contract.add(url);
        //6

    }

    private class ourViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            CookieManager.getInstance().setAcceptCookie(true);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_back:
                if (webView.canGoBack()){
                    editText.setText(webView.getUrl());
                    webView.goBack();

                }
                return true;
            case R.id.action_forward:
                if (webView.canGoForward()){
                    editText.setText(webView.getUrl());
                    webView.goForward();
                }

                return true;
            case R.id.action_home:
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                webView.loadUrl("http://www.google.com");
                editText.setText(webView.getUrl());

                return true;
            case R.id.action_history:
                Intent i =new Intent(MainActivity.this ,BrowserHistory.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
