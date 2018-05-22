$(function () {

    var tbDevices = $('#tbDevices').get(0).tBodies[0];

    setCheck();
    function setCheck(){
        var length = tbDevices.rows.length;
        for(var i = 0; i < length; i ++){
            var x = tbDevices.rows[i].cells[5].innerHTML;
            if(x.search('success') >= 0){
                tbDevices.rows[i].cells[0].firstChild.setAttribute('disabled', 'disabled');
            }
        }
    }



    $('#btCheck').click(function () {
        var currentRows = [];
        var currentIds = [];
        var currentSalesId = $('#ipCurrentSalesId').val();
        $('input[name=cbDevice]:checked').each(function (index) {
            currentRows[index] = parseInt($(this).attr('currentRow'));
            currentIds[index] = tbDevices.rows[parseInt($(this).attr('currentRow'))].cells[1].innerHTML;
        });
        if(currentRows.length <= 0){
            showNotice('have no choose device');
            return;
        }
        if(currentIds.length <= 0){
            showNotice('have no choose device');
            return;
        }

        $('#modalCheck').modal('show');
        $('#btSubmitCheck').click(function () {
            var checkNumber = $('#ipCheckNumber').val();
            if(checkNumber.length <= 0){
                showErrorMessage($('#errorCheck'), 'check number error');
                return;
            }
            checkDevice(currentIds, currentSalesId, checkNumber, currentRows);
        });
    });

    
    function checkDevice(currentIds, currentSalesId, checkNumber, currentRows) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/admin/sales/commission/check",
            data: {"ids": currentIds, 'salesId': currentSalesId, 'checkNumber': checkNumber},
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
                        tbDevices.rows[parseInt(row)].cells[5].innerHTML = '<span class="text-success"><i class="fa fa-check-circle"></i></span>';
                        tbDevices.rows[parseInt(row)].cells[6].innerHTML = '<span>'+ checkNumber +'</span>';
                        tbDevices.rows[parseInt(row)].cells[0].firstChild.setAttribute('disabled', 'disabled');
                    }
                    $('#ipCheckNumber').val('');
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