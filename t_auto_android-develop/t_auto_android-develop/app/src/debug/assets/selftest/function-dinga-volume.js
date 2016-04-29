$('#getVolume').click(function t$drive$get$volume$click() {
    var functionName = 'getVolume';
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$get$volume$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$get$volume$callback(functionName, callId, result) {
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
                $('#getVolumeVolume').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            var volume = result.volume;
            $('#getVolumeVolume').text(volume);
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$get$volume$time$out() {
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
    $('#getVolumeVolume').text('request');
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
$('#setVolumeVolume').keyup(function t$drive$set$volume$key$up(event) {
    if (event.keyCode == 13) {
        $('#setVolume').click();
    }
});
$('#setVolume').click(function t$drive$set$volume$click() {
    var volume = $('#setVolumeVolume').val();
    var payload = { volume: volume };
    var functionName = 'setVolume';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$volume$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$volume$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$set$volume$time$out() {
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
$('#upVolume').click(function t$drive$up$volume$click() {
    var functionName = 'upVolume';
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId);
});
$('#downVolume').click(function t$drive$down$volume$click() {
    var functionName = 'downVolume';
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId);
});
