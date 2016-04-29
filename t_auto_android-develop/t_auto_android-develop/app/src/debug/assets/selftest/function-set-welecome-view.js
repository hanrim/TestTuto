$('#setWelecomeView_1').click(function t$drive$set$welecome$view$1$click() {

    var msg = {
        close: 0
    };

    var functionName = 'setWelecomeView';
    var parameter = encodeURIComponent(JSON.stringify(msg));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$welecome$view$1$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$set$welecome$view$1$callback(functionName, callId, result) {
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
                $('#setWelecomeViewView').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#setWelecomeViewView').text('success');
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$set$welecome$view$1$time$out() {
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
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});

$('#setWelecomeView_2').click(function t$drive$set$welecome$view$2$click() {
    var msg = {
            close: 1
        };

    var functionName = 'setWelecomeView';
    var parameter = encodeURIComponent(JSON.stringify(msg));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$welecome$view$2$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$set$welecome$view$2$callback(functionName, callId, result) {
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
                $('#setWelecomeViewView').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#setWelecomeViewView').text('success');
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$set$welecome$view$2$time$out() {
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
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});

$('#setWelecomeView_3').click(function t$drive$set$welecome$view$3$click() {
   var msg = {
           close: 2
       };

   var functionName = 'setWelecomeView';
   var parameter = encodeURIComponent(JSON.stringify(msg));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$welecome$view$3$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$set$welecome$view$3$callback(functionName, callId, result) {
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
                $('#setWelecomeViewView').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#setWelecomeViewView').text('success');
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$set$welecome$view$3$time$out() {
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
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});

$('#setWelecomeView_4').click(function t$drive$set$welecome$view$3$click() {
   var msg = {
           close: 3
       };

   var functionName = 'setWelecomeView';
   var parameter = encodeURIComponent(JSON.stringify(msg));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$welecome$view$3$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$set$welecome$view$3$callback(functionName, callId, result) {
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
                $('#setWelecomeViewView').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#setWelecomeViewView').text('success');
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$set$welecome$view$3$time$out() {
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
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, parameter, callbackName);
});