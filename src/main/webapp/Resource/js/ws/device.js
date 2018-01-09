$(function () {

    var webSocket = null;
    if ('WebSocket' in window) {
        webSocket = new WebSocket("ws://localhost:8080/panel/socket/master");
    }else {
        alert('browser does not support')
    }

    webSocket.onopen = function(evt) {
        console.log("Connection open ...");
    };

    webSocket.onmessage = function(evt) {
        console.log( "Received Message: " + evt.data);
    };

    webSocket.onclose = function(evt) {
        console.log("Connection closed.");
    };

    webSocket.onerror = function(evt) {
        console.log("onerror");
    };

    window.onbeforeunload = function () {
        webSocket.close();
    };

    $('#send').click(function(){
        webSocket.send('0/event/mac');
        // webSocket.send('0/event/5C:41:E7:01:8B:EB');
    });

    $('#close').click(function(){
        webSocket.close()
    });

});