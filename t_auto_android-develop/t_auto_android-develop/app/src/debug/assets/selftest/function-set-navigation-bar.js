$('#setNavigationBar').click(function t$drive$set$navigation$bar$click() {
    var functionName = 'setNavigationBar';
    var callId = window['t$drive$get$call$id']();
    var parameter = null;
    var callbackName = 't$drive$set$navigation$bar$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$navigation$bar$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$set$navigation$bar$time$out() {
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
$('#setNavigationBar2').click(function t$drive$set$navigation$bar$2$click() {
    var leftButtonChecked = $('input[name="leftButtonType"]:checked');
    var leftButtonType = leftButtonChecked.val();
    var leftButtonUrl = leftButtonChecked.parent().find('input[name="leftButtonUrl"]').val();
    var centerButtonChecked = $('input[name="centerButtonType"]:checked');
    var centerButtonType = centerButtonChecked.val();
    var rightButtonChecked = $('input[name="rightButtonType"]:checked');
    var rightButtonType = rightButtonChecked.val();
    var rightButtonUrl = rightButtonChecked.parent().find('input[name="rightButtonUrl"]').val();
    var payload = {
        leftButtonType: leftButtonType,
        leftButtonUrl: leftButtonUrl,
        centerButtonType: centerButtonType,
        rightButtonType: rightButtonType,
        rightButtonUrl: rightButtonUrl
    };
    var functionName = 'setNavigationBar';
    var callId = window['t$drive$get$call$id']();
    var parameter = encodeURIComponent(JSON.stringify(payload));
    var callbackName = 't$drive$set$navigation$bar$2$callback$' + callId;
    var timer;
    window[callbackName] = function t$drive$set$navigation$bar$2$callback(functionName, callId, result) {
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
    timer = setTimeout(function t$drive$set$navigation$bar$2$time$out() {
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
$('#setNavigationBar2').click();
