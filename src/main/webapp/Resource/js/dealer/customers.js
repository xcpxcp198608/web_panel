$(function () {

    var tbUsers = $('#tbUsers').get(0).tBodies[0];
    var rowsLength = tbUsers.rows.length;

    showOnlineAndTotalCount();


    var currentRow = 0;
    var currentClientKey = '';
    var currentStatus = '';
    $('input[name=rdUser]').each(function(){
        $(this).click(function(){
            currentClientKey = $(this).val();
            currentRow = $(this).attr('currentRow');
            currentStatus = $(this).attr('currentStatus');
        });
    });
    
    
    $('#btActivate').click(function () {
        if(currentRow === 0 || currentClientKey.length <= 0 || currentStatus.length <= 0){
            showNotice('No user chosen');
            return;
        }
        if(currentStatus === 'canceled'){
            showNotice('this user already canceled');
            return;
        }
        if(currentStatus === 'activate'){
            showNotice('this user already activate');
            return;
        }
        if(currentStatus === 'deactivate'){
            showNotice('this user has rent problem, please check transaction record');
            return;
        }
        console.log('go to activate');
        changeUserStatus('activate', currentClientKey);

    });

    $('#btLimited').click(function () {
        if(currentRow === 0 || currentClientKey.length <= 0 || currentStatus.length <= 0){
            showNotice('No user chosen');
            return;
        }
        if(currentStatus === 'canceled'){
            showNotice('this user already canceled');
            return;
        }
        if(currentStatus === 'limited'){
            showNotice('this user already limited');
            return;
        }
        if(currentStatus === 'deactivate'){
            showNotice('this user has rent problem, please check transaction record');
            return;
        }
        console.log('go to limited');
        changeUserStatus('limited', currentClientKey);
    });
    

    function changeUserStatus(status, key) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/dealer/user/" + status + "/" + key,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"status": status, "key": key}),
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    if('activate' === status){
                        tbUsers.rows[currentRow].cells[8].children[0].innerHTML =
                            '<span class="text-success">' +status+ '</span>';
                    }else if('canceled' === status){
                        tbUsers.rows[currentRow].cells[8].children[0].innerHTML =
                            '<span class="text-secondary">' +status+ '</span>';
                    }else{

                        tbUsers.rows[currentRow].cells[8].children[0].innerHTML =
                            '<span class="text-danger">' +status+ '</span>';
                    }
                    currentStatus = status;
                }
                showNotice(response.message);
            },
            error: function () {
                hideLoading();
                showNotice('communication fail, try again later');
            }
        });
    }


    /**
     * show current online and total user count in table of users
     */
    function showOnlineAndTotalCount() {
        var count = 0;
        var onlineCount = 0;
        for(var x =0 ; x < rowsLength; x ++){
            var status = tbUsers.rows[x].style.display;
            var online = tbUsers.rows[x].cells[9].childNodes[1].getAttribute("online");
            if(status !== 'none'){
                count ++;
                if(online === "true"){
                    onlineCount ++;
                }
            }
        }
        $('#totalUsers').html(''+count);
        $('#onLineUsers').html(''+onlineCount);
    }

    /**
     * display all rows in table of users
     */
    function showAllRows() {
        for(var i =0 ; i < rowsLength; i ++){
            tbUsers.rows[i].style.display = "";
        }
    }

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 2; j < 8; j ++){
                    var content = tbUsers.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbUsers.rows[k].style.display = "";
                        break
                    }else{
                        tbUsers.rows[k].style.display = "none";
                    }
                }
            }
        }
        showOnlineAndTotalCount();
    });


    /**
     * set more click event to show user details for every rows
     */
    for(var i = 0; i < rowsLength; i ++){
        tbUsers.rows[i].cells[10].onclick = function(){
            var key = this.parentNode.cells[2].innerHTML;
            getUserDetailInfoByKey(key)
        }
    }

    var tbUserDetails = $('#tbUserDetails').get(0).tBodies[0];
    /**
     * show user details
     * @param key
     */
    function getUserDetailInfoByKey(key) {
        var url = baseUrl + "/admin/user/" + key;
        showLoading();
        $.get(url,{}, function (response, status) {
            hideLoading();
            if(status === "success") {
                tbUserDetails.rows[0].cells[1].innerHTML = response['clientKey'];
                tbUserDetails.rows[1].cells[1].innerHTML = response['mac'];
                tbUserDetails.rows[2].cells[1].innerHTML = response['category'];
                tbUserDetails.rows[3].cells[1].innerHTML = response['firstName'];
                tbUserDetails.rows[4].cells[1].innerHTML = response['lastName'];
                tbUserDetails.rows[5].cells[1].innerHTML = response['email'];
                tbUserDetails.rows[6].cells[1].innerHTML = response['phone'];
                tbUserDetails.rows[7].cells[1].innerHTML = response['cardNumber'];
                tbUserDetails.rows[8].cells[1].innerHTML = response['deposit'];
                tbUserDetails.rows[9].cells[1].innerHTML = response['firstPay'];
                tbUserDetails.rows[10].cells[1].innerHTML = response['monthPay'];
                tbUserDetails.rows[11].cells[1].innerHTML = response['createTime'];
                tbUserDetails.rows[12].cells[1].innerHTML = response['activateTime'];
                tbUserDetails.rows[13].cells[1].innerHTML = response['expiresTime'];
                tbUserDetails.rows[14].cells[1].innerHTML = response['status'];
                tbUserDetails.rows[15].cells[1].innerHTML = response['country'];
                tbUserDetails.rows[16].cells[1].innerHTML = response['region'];
                tbUserDetails.rows[17].cells[1].innerHTML = response['city'];
                tbUserDetails.rows[18].cells[1].innerHTML = response['timeZone'];
                tbUserDetails.rows[19].cells[1].innerHTML = response['lastOnLineTime'];
                $('#modalDetail').modal('show');
            }else{
                showNotice('communication error')
            }
        })
    }

});