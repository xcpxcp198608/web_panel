var baseUrl = "http://"+location.host+"/panel";

var dNotice;
var noticeMessage ;
var dDetails;
var btCloseDetails;
var loading;

$(function () {

    document.oncontextmenu = function () {
        return false
    };

    $('[data-toggle="tooltip"]').tooltip();

    dNotice = $('#notice');
    noticeMessage = $('#notice_message');

    loading = $('#loading');

    $('input[name=mac]').keyup(function (e) {
        var code = e.keyCode;
        if(code === 8){
            return true;
        }
        if(code === 32){
            return;
        }
        var mac = $(this).val();
        if(mac.length === 2){
            $(this).val(mac + ':');
            return;
        }
        if(mac.length === 5){
            $(this).val(mac + ':');
            return;
        }
        if(mac.length === 8){
            $(this).val(mac + ':');
            return;
        }
        if(mac.length === 11){
            $(this).val(mac + ':');
            return;
        }
        if(mac.length === 14){
            $(this).val(mac + ':');
        }
    });
});

function showErrorMessage(node, message) {
    node.hide().html(message).show(300);
}

function showLoading() {
    loading.css('display', 'block')
}

function hideLoading() {
    loading.css('display', 'none')
}


function showNotice(message) {
    noticeMessage.html(message);
    dNotice.css('display', 'block');
    setTimeout(function(){
        noticeMessage.html("");
        dNotice.css('display', 'none');
    }, 1500);
}

function getDaysOfCurrentMonth() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var d = new Date(year, month, 0);
    return d.getDate();
}

function getDaysOfYearAndMonth(year, month) {
    var d = new Date(year, month, 0);
    return d.getDate();
}

function validateEmail(email) {
    var regExp = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-_.])+[A-Za-z\d]{1,10}$/gi;
    return regExp.test(email);
}


function validateDateFormat(date) {
    var regExp = /^2\d\d\d-\d\d-\d\d 00:00:00$/gi;
    return regExp.test(date);
}
