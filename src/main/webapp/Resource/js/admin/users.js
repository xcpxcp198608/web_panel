$(function () {

    var tBody = $('#tbUsers').get(0).tBodies[0];
    var tDetailsBody = $('#tbUserDetails').get(0).tBodies[0];
    var rowsLength = tBody.rows.length;

    showOnlineAndTotalCount();
    for(var i = 0; i < rowsLength; i ++){
        tBody.rows[i].cells[10].onclick = function(){
            var key = this.parentNode.cells[1].innerHTML;
            getUserDetailInfoByKey(key)
        }
    }

    /**
     * show user details
     * @param key
     */
    function getUserDetailInfoByKey(key) {
        var url = baseUrl + "/admin/user/" + key;
        loading.css('display', 'block');
        $.get(url,{}, function (response, status) {
            loading.css('display', 'none');
            if(status === "success") {
                tDetailsBody.rows[0].cells[1].innerHTML = response['clientKey'];
                tDetailsBody.rows[1].cells[1].innerHTML = response['mac'];
                tDetailsBody.rows[2].cells[1].innerHTML = response['category'];
                tDetailsBody.rows[3].cells[1].innerHTML = response['firstName'];
                tDetailsBody.rows[4].cells[1].innerHTML = response['lastName'];
                tDetailsBody.rows[5].cells[1].innerHTML = response['email'];
                tDetailsBody.rows[6].cells[1].innerHTML = response['phone'];
                tDetailsBody.rows[7].cells[1].innerHTML = response['cardNumber'];
                tDetailsBody.rows[8].cells[1].innerHTML = response['deposit'];
                tDetailsBody.rows[9].cells[1].innerHTML = response['firstPay'];
                tDetailsBody.rows[10].cells[1].innerHTML = response['monthPay'];
                tDetailsBody.rows[11].cells[1].innerHTML = response['createTime'];
                tDetailsBody.rows[12].cells[1].innerHTML = response['activateTime'];
                tDetailsBody.rows[13].cells[1].innerHTML = response['expiresTime'];
                tDetailsBody.rows[14].cells[1].innerHTML = response['status'];
                tDetailsBody.rows[15].cells[1].innerHTML = response['country'];
                tDetailsBody.rows[16].cells[1].innerHTML = response['region'];
                tDetailsBody.rows[17].cells[1].innerHTML = response['city'];
                tDetailsBody.rows[18].cells[1].innerHTML = response['timeZone'];
                tDetailsBody.rows[19].cells[1].innerHTML = response['lastOnLineTime'];
                dDetails.css('display', 'block')
            }else{
                showNotice('communication error')
            }
        })
    }

    function showOnlineAndTotalCount() {
        var count = 0;
        var onlineCount = 0;
        for(var x =0 ; x < rowsLength; x ++){
            var status = tBody.rows[x].style.display;
            var online = tBody.rows[x].cells[9].childNodes[1].getAttribute("online");
            if(status !== 'none'){
                count ++;
                if(online === "true"){
                    onlineCount ++;
                }
            }
        }
        $('#spTotalCount').html(''+count);
        $('#spOnlineCount').html(''+onlineCount);
    }

    function showAllRows() {
        for(var i =0 ; i < rowsLength; i ++){
            tBody.rows[i].style.display = "";
        }
    }

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 7; j ++){
                    var content = tBody.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tBody.rows[k].style.display = "";
                        break
                    }else{
                        tBody.rows[k].style.display = "none";
                    }
                }
            }
        }
        showOnlineAndTotalCount();
    });


    $('#seCategory').change(function () {
        selectChangeListener($(this).val(), 7);
    });

    $('#seStatus').change(function () {
        selectChangeListener($(this).val(), 8);
    });

    function selectChangeListener(key, cellIndex) {
        if(key.length >0){
            for(var i =0 ; i < rowsLength; i ++){
                if(tBody.rows[i].cells[cellIndex].innerHTML === key){
                    tBody.rows[i].style.display = "";
                }else{
                    tBody.rows[i].style.display = "none";
                }
            }
        }else{
            showAllRows();
        }
        showOnlineAndTotalCount();
    }


});