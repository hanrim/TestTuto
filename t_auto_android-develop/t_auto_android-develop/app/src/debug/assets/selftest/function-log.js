$('#logMsg').keyup(function t$drive$log$msg$key$up(event) {
    if (event.keyCode == 13) {
        $('#log').click();
    }
});
$('#log').click(function t$drive$log$click() {
    var msg = $('#logMsg').val();
    var functionName = 'log';
    var parameter = encodeURIComponent(msg);
    var callId = window['t$drive$get$call$id']();
    window['t$drive$invoke$app$function'](functionName, callId, parameter);
});
