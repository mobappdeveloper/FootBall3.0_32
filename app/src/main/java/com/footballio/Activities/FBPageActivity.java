package com.footballio.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.Loader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FBPageActivity extends AppCompatActivity {

    @BindView(R.id.fb_profile_agn_back)
    LinearLayout fbProfileAgnBack;
    @BindView(R.id.webview)
    WebView webview;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        myContext = this;

        /*WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);*/

        webview.loadUrl("https://football3.io/agb/");
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                if (progress == 100) {
                    Loader.showLoad(myContext,false);

                } else {
                    Loader.showLoad(myContext,true);

                }
            }
        });
        fbProfileAgnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
