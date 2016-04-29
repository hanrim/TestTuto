$('#tdriveFinish').click(function t$drive$tdrive$finish$click() {
    var functionName = 'tdriveFinish';
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$tdrive$finish$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$tdrive$finish$callback(functionName, callId, result) {
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
                $('#tdriveFinishMenu').text(result.errorCode);
                return;
            }
            console.log('success');
            // 상태 표시
            $('#tdriveFinishMenu').text("success");
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$tdrive$finish$time$out() {
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
    $('#tdriveFinishMenu').text('request');
    // 함수 실행
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
