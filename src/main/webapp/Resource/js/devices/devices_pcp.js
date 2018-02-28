$(function () {

    var tbPCPDevices = $('#tbPCPDevices').get(0).tBodies[0];


    var currentMac = '';
    var currentRow = 0;
    $('input[name=rdDevice]').each(function(){
        $(this).click(function(){
            currentMac = $(this).val();
            currentRow = $(this).attr('currentRow');
        });
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
        var url = baseUrl + '/device/pcp/check';
        $.ajax({
            type: 'POST',
            url: url,
            data: {'mac': mac},
            dataType: 'json',
            beforeSend:function(){
                $('#modalCheckIn').modal('hide');
                showLoading();
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalCheckIn').modal('hide');
                    insertIntoTable(response.data);
                }else{
                    $('#modalCheckIn').modal('show');
                    showErrorMessage($('#errorCheckIn'), response.message);
                }
            },
            error:function(){
                hideLoading();
                showErrorMessage($('#errorCheckIn'), 'network error');
            }
        });
    });


    function insertIntoTable(devicePCPInfo) {
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        td1.innerHTML = devicePCPInfo['id'];
        var td2 = document.createElement("td");
        td2.innerHTML = devicePCPInfo['mac'];
        var td3 = document.createElement("td");
        td3.innerHTML = devicePCPInfo['salesName'];
        var td4 = document.createElement("td");
        td4.innerHTML = devicePCPInfo['createTime'];
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tr.append(td4);
        tbPCPDevices.prepend(tr)
    }


    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            var rowsLength = tbPCPDevices.rows.length;
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 4; j ++){
                    var content = tbPCPDevices.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbPCPDevices.rows[k].style.display = "";
                        break
                    }else{
                        tbPCPDevices.rows[k].style.display = "none";
                    }
                }
            }
        }
    });

    function showAllRows() {
        var rowsLength = tbPCPDevices.rows.length;
        for(var i =0 ; i < rowsLength; i ++){
            tbPCPDevices.rows[i].style.display = "";
        }
    }


});