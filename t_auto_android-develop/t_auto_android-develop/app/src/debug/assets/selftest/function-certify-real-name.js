
// 실명 인증
$('#certifyRealNameListener').click(function t$drive$set$player$change$listener$click() {
    var functionName = 'certifyRealNameListener';
    var callId = window['t$drive$get$call$id']();
    // 콜백 함수 명
    var callbackName = $('#certifyRealNameListenerCallback').val();
    window['t$drive$invoke$app$function'](functionName, callId, null, callbackName);
});
// 정적인 콜백
window['t$drive$certify$real$name$chang'] = function t$drive$certify$real$name$chang(functionName, callId, result, callbackName) {
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
        $('#realName').text(JSON.stringify(result));
    }
};
$('#certifyRealNameListenerCallback').val('t$drive$certify$real$name$chang');
