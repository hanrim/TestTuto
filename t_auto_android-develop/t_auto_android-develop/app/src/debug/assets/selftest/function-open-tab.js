$('#openTabUrl').keyup(function t$drive$open$tab$url$key$up(event) {
    if (event.keyCode == 13) {
        $('#openTab').click();
    }
});
window['t$drive$close$tab$result$3'] = function t$drive$close$tab$result$3(parameter) {
    if (parameter) {
        parameter = 'result : ' + decodeURIComponent(parameter);
    } else {
        parameter = '';
    }
    $('#closeTabResult3').text(parameter);
};
$('#openTab').click(function t$drive$open$tab$click() {
    var url = $('#openTabUrl').val();
    var absUrl = window['t$drive$get$absolute$path'](url);
    var payload = { url: absUrl };
    var functionName = 'openTab';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$open$tab$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$open$tab$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$open$tab$time$out() {
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
    window['t$drive$close$tab$result$3']('');
});
$('#openTab2').click(function t$drive$open$tab$click$2() {
    var functionName = 'openTab';
    var parameter = null;
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$open$tab$2$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$open$tab$2$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$open$tab$2$time$out() {
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
    window['t$drive$close$tab$result$3']('');
});
$('#openTab3').click(function t$drive$open$tab$click$3() {
    var payload = {};
    var functionName = 'openTab';
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callId = window['t$drive$get$call$id']();
    var callbackName = 't$drive$open$tab$3$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$open$tab$3$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$open$tab$3$time$out() {
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
    window['t$drive$close$tab$result$3']('');
});
