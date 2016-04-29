$('#getPlayState').click(function t$drive$get$play$state$click() {
    var functionName = 'getPlayState';
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$get$play$state$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$get$play$state$callback(functionName, callId, result) {
        // 타이머 제거
        clearTimeout(timer);
        // 동적 콜백 제거
        delete window[callbackName];
        console.log('callbackName:' + callbackName);
        console.log('functionName:' + functionName);
        console.log('callId:' + callId);
        console.log('rawResult:' + result);
        if (result) {
            result = decodeURIComponent(result);
        }
        console.log('result:' + result);
        if (result) {
            // 응답 값이 있을때
            result = JSON.parse(result);
            if (result.errorCode) {
                // errorCode 가 있으면 오류로 판단
                console.log('error: ' + result.errorCode + ' :' + result.errorMessage);
                $('#playState').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            var playState = result.playState;
            $('#playState').text(playState);
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$get$play$state$time$out() {
        var callback = window[callbackName];
        if (callback) {
            // 등록한 콜백함수가 있는 경우 TIMEOUT 오류 전달
            var timeOutResult = {'errorCode': 'TIMEOUT', 'errorMessage': 'timeout!'};
            var encodedResult = encodeURIComponent(JSON.stringify(timeOutResult));
            callback(functionName, callId, encodedResult);
        } else {
            console.log(callbackName + ' callback not found!!');
        }
    }, 1000);
    // 로그 변경
    $('#playState').text('request');
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
$('#getChannel').click(function t$drive$get$channel$click() {
    var functionName = 'getChannel';
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$get$channel$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$get$channel$callback(functionName, callId, result) {
        // 타이머 제거
        clearTimeout(timer);
        // 동적 콜백 제거
        delete window[callbackName];
        console.log('callbackName:' + callbackName);
        console.log('functionName:' + functionName);
        console.log('callId:' + callId);
        console.log('rawResult:' + result);
        if (result) {
            result = decodeURIComponent(result);
        }
        console.log('result:' + result);
        if (result) {
            // 응답 값이 있을때
            result = JSON.parse(result);
            if (result.errorCode) {
                // errorCode 가 있으면 오류로 판단
                console.log('error: ' + result.errorCode + ' :' + result.errorMessage);
                $('#getChannelInfo').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#getChannelInfo').text(JSON.stringify(result));
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$get$channel$time$out() {
        var callback = window[callbackName];
        if (callback) {
            // 등록한 콜백함수가 있는 경우 TIMEOUT 오류 전달
            var timeOutResult = {'errorCode': 'TIMEOUT', 'errorMessage': 'timeout!'};
            var encodedResult = encodeURIComponent(JSON.stringify(timeOutResult));
            callback(functionName, callId, encodedResult);
        } else {
            console.log(callbackName + ' callback not found!!');
        }
    }, 1000);
    // 로그 변경
    $('#getChannelInfo').text('request');
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
$('#setChannel').click(function t$drive$set$channel$click() {
    var channelId = $('#setChannelChannelId').val();
    var playInfo = JSON.parse($('#setChannelPlayInfo').val());
    var payload = {
        channelId: channelId,
        playInfo: playInfo
    };
    var functionName = 'setChannel';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$channel$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$channel$callback(functionName, callId, result) {
        clearTimeout(timer);
        delete window[callbackName];
        console.log('callbackName:' + callbackName);
        console.log('functionName:' + functionName);
        console.log('callId:' + callId);
        console.log('rawResult:' + result);
        if (result) {
            result = decodeURIComponent(result);
        }
        console.log('result:' + result);
        if (result) {
            result = JSON.parse(result);
            if (result.errorCode) {
                console.log('error: ' + result.errorCode + ' :' + result.errorMessage);
                return;
            }
            console.log('success');
        }
    };
    timer = setTimeout(function t$drive$set$channel$time$out() {
      var callback = window[callbackName];
      if (callback) {
          var timeOutResult = {'errorCode': 'TIMEOUT', 'errorMessage': 'timeout!'};
          var encodedResult = encodeURIComponent(JSON.stringify(timeOutResult));
          callback(functionName, callId, encodedResult);
      } else {
          console.log(callbackName + ' callback not found!!');
      }
    }, 1000);
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});
$('#playChannelChannelId').keyup(function t$drive$play$channel$key$up(event) {
    if (event.keyCode == 13) {
        $('#playChannel').click();
    }
});
$('#playChannel').click(function t$drive$play$channel$click() {
    var channelId = $('#playChannelChannelId').val();
    var playInfo = JSON.parse($('#playChannelPlayInfo').val());
    var payload = {
        channelId: channelId,
        playInfo: playInfo
    };
    var functionName = 'playChannel';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$play$channel$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$play$channel$callback(functionName, callId, result) {
        clearTimeout(timer);
        delete window[callbackName];
        console.log('callbackName:' + callbackName);
        console.log('functionName:' + functionName);
        console.log('callId:' + callId);
        console.log('rawResult:' + result);
        if (result) {
            result = decodeURIComponent(result);
        }
        console.log('result:' + result);
        if (result) {
            result = JSON.parse(result);
            if (result.errorCode) {
                console.log('error: ' + result.errorCode + ' :' + result.errorMessage);
                return;
            }
            console.log('success');
        }
    };
    timer = setTimeout(function t$drive$play$channel$time$out() {
      var callback = window[callbackName];
      if (callback) {
          var timeOutResult = {'errorCode': 'TIMEOUT', 'errorMessage': 'timeout!'};
          var encodedResult = encodeURIComponent(JSON.stringify(timeOutResult));
          callback(functionName, callId, encodedResult);
      } else {
          console.log(callbackName + ' callback not found!!');
      }
    }, 1000);
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});
$('#stopChannel').click(function t$drive$stop$channel$click() {
    var functionName = 'stopChannel';
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId);
});
$('#resetPlayerChangeListener').click(function t$drive$reset$player$change$listener$click() {
    var functionName = 'resetPlayerChangeListener';
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId, null, null);
});
$('#setPlayerChangeListenerCallback').keyup(function t$drive$set$player$change$listener$callback$key$up(event) {
    if (event.keyCode == 13) {
        $('#setPlayerChangeListener').click();
    }
});
// 플레이어 변경 리스너 등록
$('#setPlayerChangeListener').click(function t$drive$set$player$change$listener$click() {
    var functionName = 'setPlayerChangeListener';
    var callId = window['t$drive$get$call$id']();
    // 콜백 함수 명
    var callbackName = $('#setPlayerChangeListenerCallback').val();
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
// 정적인 콜백
window['t$drive$on$song$changed'] = function t$drive$on$song$changed(functionName, callId, result, callbackName) {
    console.log('callbackName:' + callbackName);
    console.log('functionName:' + functionName);
    console.log('callId:' + callId);
    console.log('rawResult:' + result);
    if (result) {
        result = decodeURIComponent(result);
    }
    console.log('result:' + result);
    if (result) {
        result = JSON.parse(result);
        if (result.errorCode) {
            console.log('error: ' + result.errorCode + ' :' + result.errorMessage);
            $('#dingaPlayerLastChange').text(result.errorCode);
            return;
        }
        console.log('success');
        $('#dingaPlayerLastChange').text(JSON.stringify(result));
        switch (result.eventType) {
            case 'SET_LISTENER': {
                $('#dingaPlayerLastSetListener').text(JSON.stringify(result));
                break;
            }
            case 'SONG_CHANGE': {
                $('#dingaPlayerLastSongChange').text(JSON.stringify(result));
                break;
            }
            case 'STATE_CHANGE': {
                $('#dingaPlayerLastStateChange').text(JSON.stringify(result));
                break;
            }
        }
    }
};
$('#setPlayerChangeListenerCallback').val('t$drive$on$song$changed');
