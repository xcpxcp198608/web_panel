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
    dDetails = $('#details');
    btCloseDetails = $('#closeDetails');
    loading = $('#loading');

    btCloseDetails.click(function () {
        dDetails.css('display', 'none');
    });

    $('#navigation').find('> ul >li > a').each(function (index, value) {
        $(this).mouseover(function () {
            $(this).prev().css('color', '#2377b9')
        })
        $(this).mouseout(function () {
            $(this).prev().css('color', '#000')
        })
    })


});

function showLoading() {
    loading.css('display', 'block')
}

function hideLoading() {
    loading.css('display', 'none')
}

function showDetail() {
    dDetails.css('display', 'block')
}

function hideDetail() {
    dDetails.css('display', 'none');
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
