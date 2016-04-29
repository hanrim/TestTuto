package com.sktelecom.tdrive.webviewinterface;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * 웹뷰 연동 함수 테스트를 위한 페이지를 로드할 수 있도록 하는 WebViewClient.
 */
public class BaseWebViewClient extends WebViewClient {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final String url = request.getUrl().toString();
        final WebResourceResponse response = shouldInterceptRequestInternal(view, url);
        if (response != null) {
            return response;
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        final WebResourceResponse webResourceResponse = shouldInterceptRequestInternal(view, url);
        if (webResourceResponse != null) {
            return webResourceResponse;
        }
        return super.shouldInterceptRequest(view, url);
    }

    /**
     * 실제 웹뷰의 리소스 요청을 다른 내용으로 바꾼다.
     *
     * @param view 웹뷰
     * @param url  입력 URL
     * @return 내부 테스트를 위한 응답
     */
    @Nullable
    private WebResourceResponse shouldInterceptRequestInternal(
        @NonNull WebView view, @Nullable String url) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http://selftest/")) {
            return null;
        }
        final String url2 = url.split("\\?")[0];
        final StringBuilder builder = new StringBuilder(url2.replace("http://", ""));
        if (url2.endsWith("/")) {
            builder.append("index.html");
        }
        final String assetPath = builder.toString();
        try {
            final AssetManager assetManager = view.getContext().getApplicationContext().getAssets();
            final InputStream stream = assetManager.open(assetPath);
            return new WebResourceResponse("", "", stream);
        } catch (IOException ex) {
            // PASS
        }
        return null;
    }

}
