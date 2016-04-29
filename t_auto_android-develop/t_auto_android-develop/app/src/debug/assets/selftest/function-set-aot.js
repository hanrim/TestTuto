$('#setAOT').click(function t$drive$set$aot$click() {
    var gotoTdrive = $('input[name="gotoTdrive"]:checked').val();
    if (gotoTdrive === 'NULL') {
        gotoTdrive = null;
    }
    var settingVolume = $('input[name="settingVolume"]:checked').val();
    if (settingVolume === 'NULL') {
        settingVolume = null;
    }
    var finishAOT = $('input[name="finishAOT"]:checked').val();
    if (finishAOT === 'NULL') {
        finishAOT = null;
    }
    var activateAutoClose = $('input[name="activateAutoClose"]:checked').val();
    if (activateAutoClose === 'NULL') {
        activateAutoClose = null;
    }
    var channelListOpen = $('input[name="channelListOpen"]:checked').val();
    if (channelListOpen === 'NULL') {
        channelListOpen = null;
    }

    var msg = {
        gotoTdrive: gotoTdrive,
        settingVolume: settingVolume,
        finishAOT: finishAOT,
        activateAutoClose: activateAutoClose,
        channelListOpen: channelListOpen
    };

    var functionName = 'setAOT';
    var parameter = encodeURIComponent(JSON.stringify(msg));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$aot$callback$' + callId;
    var timer;
    // 동적 콜백
    window[callbackName] = function t$drive$set$aot$callback(functionName, callId, result) {
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
                $('#ip').text(result.errorCode);
                return;
            }
            console.log('success');
        }
    };
    // 1초 타이머 구성
    timer = setTimeout(function t$drive$open$sslide$menu$time$out() {
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
