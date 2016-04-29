package com.sktelecom.tdrive.webviewinterface;

import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.WebChromeClient;

import timber.log.Timber;

/**
 * 기본 WebChromeClient.
 */
public class BaseWebChromeClient extends WebChromeClient {

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        final MessageLevel messageLevel = consoleMessage.messageLevel();
        final int level;
        switch (messageLevel) {
            case DEBUG: {
                level = Log.DEBUG;
                break;
            }
            case ERROR: {
                level = Log.ERROR;
                break;
            }
            case LOG: {
                level = Log.VERBOSE;
                break;
            }
            case TIP: {
                level = Log.DEBUG;
                break;
            }
            case WARNING: {
                level = Log.WARN;
                break;
            }
            default: {
                level = Log.DEBUG;
                break;
            }
        }
        final String message = consoleMessage.message();
        final int line = consoleMessage.lineNumber();
        final String sourceId = consoleMessage.sourceId();
        onConsoleMessageInternal(level, sourceId, line, message);
        return super.onConsoleMessage(consoleMessage);
    }

    /**
     * 실제로 로그 출력을 한다.
     *
     * @param level    로그 레벨
     * @param sourceId 로그 출력한 곳.
     * @param line     로그 출력한 줄수
     * @param message  로그 내용
     */
    private void onConsoleMessageInternal(
        int level, @NonNull String sourceId, int line, @NonNull String message) {
        Timber.tag("WEB").log(level, "%d:%d:%s:%s", level, line, sourceId, message);
    }

}
