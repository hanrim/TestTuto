$('#closeTab').click(function t$drive$close$tab$click() {
    var functionName = 'closeTab';
    var parameter = "";
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId, parameter);
});
$('#closeTab2').click(function t$drive$close$tab$2$click() {
    var url = $('#closeTab2Url').val();
    var absUrl = t$drive$get$absolute$path(url);
    var payload = { url: absUrl };
    var functionName = 'closeTab';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId, parameter);
});
$('#setCloseUrl').click(function t$drive$set$close$url$click() {
    var functionName = 'setCloseUrl';
    var parameter = "";
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$close$url$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$close$url$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$set$close$url$time$out() {
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
$('#setCloseUrl2').click(function t$drive$set$close$url$2$click() {
    var url = $('#setCloseUrl2Url').val();
    var absUrl = t$drive$get$absolute$path(url);
    var payload = { url: absUrl };
    var functionName = 'setCloseUrl';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$set$close$url$2$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$close$url$2$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$set$close$url$2$time$out() {
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
$('#resetCloseUrl').click(function t$drive$reset$close$url$click() {
    var functionName = 'resetCloseUrl';
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId, null);
});
