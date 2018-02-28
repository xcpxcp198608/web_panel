$(function () {


    var tbMCMDevices = $('#tbMCMDevices').get(0).tBodies[0];

    /**
     *  check in the mcm device
     */
    $('#btCheckIn').click(function () {
        $('#modalCheckIn').modal('show');
    });

    $('#btSubmitCheckIn').click(function () {
        var mac = $('#ipCheckInMac').val();
        var username = $('#ipUsername').val();
        var checkInCode = $('#ipCheckInCode').val();
        if(mac.length !== 17){
            showErrorMessage($('#errorCheckIn'), 'mac input error');
            return;
        }
        if(username.length <= 0){
            showErrorMessage($('#errorCheckIn'), 'username input error');
            return;
        }
        if(checkInCode.length <= 0){
            showErrorMessage($('#errorCheckIn'), 'code input error');
            return;
        }
        var url = baseUrl + '/device/mcm/check';
        $('#modalCheckIn').modal('hide');
        $.ajax({
            type: 'PUT',
            url: url,
            data: {'mac': mac, 'username': username, 'checkInCode': checkInCode},
            dataType: 'json',
            beforeSend:function(){
                showLoading();
                $('#modalCheckIn').modal('hide');
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalCheckIn').modal('hide');
                    insertEnableDeviceToTable(response.data);
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


    function insertEnableDeviceToTable(deviceInfo) {
        var tr = document.createElement('tr');
        var td1 = document.createElement('td');
        td1.innerHTML = (tbMCMDevices.rows.length + 1).toString();
        var td2 = document.createElement('td');
        td2.innerHTML = deviceInfo['mac'];
        var td3 = document.createElement('td');
        td3.innerHTML = deviceInfo['username'];
        var td4 = document.createElement('td');
        td4.innerHTML = deviceInfo['checkInUser'];
        var td5 = document.createElement('td');
        td5.innerHTML = deviceInfo['checkInTime'];
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tr.append(td4);
        tr.append(td5);
        tbMCMDevices.append(tr);
    }


    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            var rowsLength = tbMCMDevices.rows.length;
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 5; j ++){
                    var content = tbMCMDevices.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbMCMDevices.rows[k].style.display = "";
                        break
                    }else{
                        tbMCMDevices.rows[k].style.display = "none";
                    }
                }
            }
        }
    });

    function showAllRows() {
        var rowsLength = tbMCMDevices.rows.length;
        for(var i =0 ; i < rowsLength; i ++){
            tbMCMDevices.rows[i].style.display = "";
        }
    }

});