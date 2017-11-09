var baseUrl = "http://"+location.host+"/panel";

var dNotice;
var noticeMessage ;
var dDetails;
var btCloseDetails;
var loading;

$(function () {

    dNotice = $('#notice');
    noticeMessage = $('#notice_message');
    dDetails = $('#details');
    btCloseDetails = $('#closeDetails');
    loading = $('#loading');

    btCloseDetails.click(function () {
        dDetails.css('display', 'none');
    });


});

function showErrorNotice(message) {
    noticeMessage.html(message);
    dNotice.css('display', 'block');
    setTimeout(function(){
        noticeMessage.html("");
        dNotice.css('display', 'none');
    },2000);
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
