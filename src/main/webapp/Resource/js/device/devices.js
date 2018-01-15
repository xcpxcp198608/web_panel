$(function () {

    var tbDevices = $('#tbDevices').get(0).tBodies[0];
    var rowsLength = tbDevices.rows.length;

    showTotalCount();
    /**
     * show current online and total user count in table of users
     */
    function showTotalCount() {
        var count = 0;
        for(var x =0 ; x < rowsLength; x ++){
            var status = tbDevices.rows[x].style.display;
            if(status !== 'none'){
                count ++;
            }
        }
        $('#spTotalCount').html(''+count);
    }

    /**
     * display all rows in table of users
     */
    function showAllRows() {
        for(var i =0 ; i < rowsLength; i ++){
            tbDevices.rows[i].style.display = "";
        }
    }

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 5; j ++){
                    var content = tbDevices.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbDevices.rows[k].style.display = "";
                        break
                    }else{
                        tbDevices.rows[k].style.display = "none";
                    }
                }
            }
        }
        showTotalCount();
    });

    var currentMac = '';
    var currentRow = 0;
    $('input[name=rdDevice]').each(function(){
        $(this).click(function(){
            currentMac = $(this).val();
            currentRow = $(this).attr('currentRow');
        });
    });
    
    $('#btCheckIn').click(function () {
        showDetail();
    });

    $('#ipMac').keyup(function (e) {
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

    var errorMessage = $('#errorMessage');
    $('#btSubmitCheckIn').click(function () {
        errorMessage.html(response.message);
        var mac = $('#ipMac').val();
        var salesId = $('#ipSalesId').val();
        if(mac.length !== 17){
            errorMessage.html('mac input error');
            return;
        }
        if(salesId <= 0){
            errorMessage.html('No sales choose');
            return;
        }
        var url = baseUrl + '/device/save/';
        $.ajax({
            type: 'POST',
            url: url,
            data: {'mac': mac, 'salesId': salesId},
            dataType: 'json',
            beforeSend:function(){
                showLoading()
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    showNotice("Successfully");
                    window.open(baseUrl + "/device/home", "_self")
                }else{
                    errorMessage.html(response.message);
                }
            },
            error:function(){
                hideLoading()
            }
        });
    });

});