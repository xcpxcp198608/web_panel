$(function () {

    var tbDisableDevices = $('#tbDisableDevices').get(0).tBodies[0];


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
        var url = baseUrl + '/manager/device/save';
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

    function insertNewDeviceToTable(deviceInfo) {
        var rows = tbDisableDevices.rows.length;
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        td1.innerHTML = (rows+1).toString();
        var td2 = document.createElement("td");
        td2.innerHTML = deviceInfo['mac'];
        var td3 = document.createElement("td");
        td3.innerHTML = '<span class="text-muted"><i class="fa fa-times-circle"></i></span>';
        var td4 = document.createElement("td");
        td4.innerHTML = deviceInfo['createTime'];
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tr.append(td4);
        tbDisableDevices.append(tr);
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
        var url = baseUrl + '/manager/devices/save';
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
                    window.open(baseUrl + '/manager/devices', '_self')
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





    /**
     *  enable the device
     */
    $('#btEnable').click(function () {
        $('#modalEnable').modal('show');
    });

    $('#btSubmitEnable').click(function () {
        var mac = $('#ipEnableMac').val();
        var username = $('#ipUsername').val();
        if(mac.length !== 17){
            showErrorMessage($('#errorEnable'), 'mac input error');
            return;
        }
        var url = baseUrl + '/manager/device/enable';
        $('#modalEnable').modal('hide');
        $.ajax({
            type: 'PUT',
            url: url,
            data: {'mac': mac, "username": username},
            dataType: 'json',
            beforeSend:function(){
                showLoading();
                $('#modalEnable').modal('hide');
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalEnable').modal('hide');
                    insertEnableDeviceToTable(response.data);
                }else{
                    $('#modalEnable').modal('show');
                    showErrorMessage($('#errorEnable'), response.message);
                }
            },
            error:function(){
                hideLoading();
                $('#modalEnable').modal('show');
                showErrorMessage($('#errorEnable'), 'network error');
            }
        });
    });


    var tbEnableDevices = $('#tbEnableDevices').get(0).tBodies[0];
    function insertEnableDeviceToTable(deviceInfo) {
        var tr = document.createElement('tr');
        var td1 = document.createElement('td');
        td1.innerHTML = (tbEnableDevices.rows.length + 1).toString();
        var td2 = document.createElement('td');
        td2.innerHTML = deviceInfo['mac'];
        var td3 = document.createElement('td');
        td3.innerHTML = '<span class="text-muted"><i class="fa fa-check-circle"></i></span>';
        var td4 = document.createElement('td');
        td4.innerHTML = deviceInfo['enableName'];
        var td5 = document.createElement('td');
        td5.innerHTML = deviceInfo['enableTime'];
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tr.append(td4);
        tr.append(td5);
        tbEnableDevices.append(tr);
    }

});