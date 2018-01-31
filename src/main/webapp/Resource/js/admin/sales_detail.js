$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;
    var tbDevices = $('#tbDevices').get(0).tBodies[0];

    function setYearAndMonth() {
        $('#aYear').html(currentYear);
        if(currentMonth < 10){
            $('#aMonth').html("0" + currentMonth);
        }else {
            $('#aMonth').html(currentMonth);
        }
    }

    var currentRow = -1;
    var currentSalesId = 0;
    var currentMac = '';
    $('input[name=rdDevice]').each(function () {
        $(this).click(function () {
            currentRow = $(this).attr('currentRow');
            currentSalesId = $(this).val();
            currentMac = tbDevices.rows[currentRow].cells[2].innerHTML;
        });
    });

    $('#btCheck').click(function () {
        if(currentRow < 0){
            showNotice('have no choose device');
        }


    });

});