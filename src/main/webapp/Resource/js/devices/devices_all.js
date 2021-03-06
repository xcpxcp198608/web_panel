$(function () {


    /**
     * check in new device
     */
    $('#btCheckIn').click(function () {
        $('#modalCheckIn').modal('show');
    });

    $('#btSubmitCheckIn').click(function () {
        var mac = $('#ipMac').val();
        if(mac.length !== 17){
            showErrorMessage($('#errorCheckIn'), 'mac input error');
            return;
        }
        var url = baseUrl + '/device/all/save';
        $('#modalCheckIn').modal('hide');
        $.ajax({
            type: 'POST',
            url: url,
            data: {'mac': mac},
            dataType: 'json',
            beforeSend:function(){
                showLoading();
                $('#modalCheckIn').modal('hide');
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalCheckIn').modal('hide');
                    insertNewDeviceToTable(response.data);
                }else{
                    $('#modalCheckIn').modal('show');
                    showErrorMessage($('#errorCheckIn'), response.message);
                }
            },
            error:function(){
                hideLoading();
                $('#modalCheckIn').modal('show');
                showErrorMessage($('#errorCheckIn'), 'network error');
            }
        });
    });

    var tbAllDevices = $('#tbAllDevices').get(0).tBodies[0];
    function insertNewDeviceToTable(deviceInfo) {
        var tr = document.createElement('tr');
        var td1 = document.createElement('td');
        td1.innerHTML = deviceInfo['id'];
        var td2 = document.createElement('td');
        td2.innerHTML = deviceInfo['mac'];
        var td3 = document.createElement('td');
        td3.innerHTML = '<span class="text-muted">PENDING</span>';
        var td4 = document.createElement('td');
        td4.innerHTML = deviceInfo['createTime'];
        var td5 = document.createElement('td');
        td5.innerHTML = deviceInfo['checkInTime'];
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tr.append(td4);
        tr.append(td5);
        tbAllDevices.append(tr);
    }





    /**
     *  bath check in new devices
     */
    $('#btBathCheckIn').click(function () {
        $('#modalBathCheckIn').modal('show');
    });

    $('#btSubmitBathCheckIn').click(function () {
        var startMac = $('#ipStartMac').val();
        var endMac = $('#ipEndMac').val();
        if(startMac.length !== 17){
            showErrorMessage($('#errorBathCheckIn'), 'start mac input error');
            return;
        }
        if(endMac.length !== 17){
            showErrorMessage($('#errorBathCheckIn'), 'end mac input error');
            return;
        }
        var url = baseUrl + '/device/all/saves';
        $('#modalBathCheckIn').modal('hide');
        $.ajax({
            type: 'POST',
            url: url,
            data: {'startMac': startMac, "endMac": endMac},
            dataType: 'json',
            beforeSend:function(){
                showLoading();
                $('#modalBathCheckIn').modal('hide');
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalBathCheckIn').modal('hide');
                    window.open(baseUrl + '/device/all', '_self')
                }else{
                    $('#modalBathCheckIn').modal('show');
                    showErrorMessage($('#errorBathCheckIn'), response.message);
                }
            },
            error:function(){
                hideLoading();
                $('#modalBathCheckIn').modal('show');
                showErrorMessage($('#errorBathCheckIn'), 'network error');
            }
        });
    });


    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            var rowsLength = tbAllDevices.rows.length;
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 4; j ++){
                    var content = tbAllDevices.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbAllDevices.rows[k].style.display = "";
                        break
                    }else{
                        tbAllDevices.rows[k].style.display = "none";
                    }
                }
            }
        }
    });

    function showAllRows() {
        var rowsLength = tbAllDevices.rows.length;
        for(var i =0 ; i < rowsLength; i ++){
            tbAllDevices.rows[i].style.display = "";
        }
    }

});