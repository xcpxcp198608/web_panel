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



    $('#btCheck').click(function () {
        var currentRows = [];
        var currentMacs = [];
        var currentSalesId = $('#ipCurrentSalesId').val();
        $('input[name=cbDevice]:checked').each(function (index) {
            currentRows[index] = parseInt($(this).attr('currentRow'));
            currentMacs[index] = tbDevices.rows[parseInt($(this).attr('currentRow'))].cells[2].innerHTML;
        });
        console.log(currentSalesId);
        console.log(currentRows);
        console.log(currentMacs);
        if(currentRows.length <= 0){
            showNotice('have no choose device');
            return;
        }
        if(currentMacs.length <= 0){
            showNotice('have no choose device');
            return;
        }

        $('#modalCheck').modal('show');
        $('#btSubmitCheck').click(function () {
            var checkNumber = $('#ipCheckNumber').val();
            if(checkNumber.length <= 0){
                showNotice('check number error');
                return;
            }
            checkDevice(currentMacs, currentSalesId, checkNumber, currentRows);
        });
    });

    
    function checkDevice(currentMacs, currentSalesId, checkNumber, currentRows) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/admin/devices/check",
            data: {"macs": currentMacs, 'salesId': currentSalesId, 'checkNumber': checkNumber},
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
                    for(var i = 0; i < currentRows.length; i ++) {
                        var row = currentRows[i];
                        tbDevices.rows[parseInt(row)].cells[6].innerHTML = '<span class="text-secondary"><i class="fa fa-times-circle"></i></span>';
                        tbDevices.rows[parseInt(row)].cells[7].innerHTML = '<span class="text-success"><i class="fa fa-check-circle"></i></span>';
                    }
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
    }

});