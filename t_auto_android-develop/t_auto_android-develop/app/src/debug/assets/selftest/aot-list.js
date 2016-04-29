$('.aot-channel').click(function t$drive$aot$channel$click(ev) {
    var channelId = $(ev.target).text();
    var playInfo = {};
    var payload = {
        channelId: channelId,
        playInfo: playInfo
    };
    var functionName = 'playChannel';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$aot$channel$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$aot$channel$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$aot$channel$time$out() {
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
