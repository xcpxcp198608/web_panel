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
    var currentSDCN = false;
    $('input[name=rdDevice]').each(function () {
        $(this).click(function () {
            currentRow = $(this).attr('currentRow');
            currentSDCN = $(this).attr('currentSDCN');
            currentSalesId = $(this).val();
            currentMac = tbDevices.rows[currentRow].cells[2].innerHTML;
        });
    });

    $('#btCheck').click(function () {
        if(currentRow < 0){
            showNotice('have no choose device');
            return;
        }
        if(currentSDCN !== 'true'){
            showNotice('this device can not check');
            return;
        }
        $('#modalCheck').modal('show');
    });
    
    $('#btSubmitCheck').click(function () {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/admin/devices/check",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"mac": currentMac, 'salesId': currentSalesId}),
            dataType: "json",
            beforeSend: function () {
                $('#modalCheck').modal('hide');
                showLoading();
            },
            success: function (response) {
                $('#btSubmitCheck').removeAttr('disabled');
                hideLoading();
                if(response.code === 200) {
                    $('#modalCheck').modal('hide');
                    tbDevices.rows[parseInt(currentRow)].cells[6].innerHTML = '<span class="text-secondary"><i class="fa fa-times-circle"></i></span>';
                    tbDevices.rows[parseInt(currentRow)].cells[7].innerHTML = '<span class="text-success"><i class="fa fa-check-circle"></i></span>';
                }else{
                    $('#modalCheck').modal('show');
                    showErrorMessage($('#errorCheck'), response.message);
                }
            },
            error: function () {
                $('#btSubmitCheck').removeAttr('disabled');
                $('#modalCheck').modal('show');
                showErrorMessage($('#errorCheck'), 'communication fail, try again later');
            }
        });
    });

});