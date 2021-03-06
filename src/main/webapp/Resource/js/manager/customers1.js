$(function () {

    var tbUsers = $('#tbUsers').get(0).tBodies[0];
    var rowsLength = tbUsers.rows.length;

    var userList = [];
    getUserList();
    function getUserList() {
        var url = baseUrl + "/manager/users/list";
        $.get(url, function (data) {
            if(data.code === 200){
                userList = data.dataList;
                console.log(userList)
            }
        })
    }

    function createTableContent(start){
        if(userList.length <= 0){
            return;
        }
        for(var i = start ; i < start + 50; i ++){
            var userInfo = userList[i];
            var tr = document.createElement("tr");
            var td1 = document.createElement("td");
            td1.innerHTML = '<input type="radio" name="rdUser" value="'+userInfo['id']+'"\n' +
                ' currentRow="'+ (i+1) +'" currentStatus="'+userInfo['emailStatus']+'">';
            tr.appendChild(td1)



        }
    }


    showOnlineAndTotalCount();


    var currentRow = 0;
    var currentId = 0;
    var updateLevel = 0;
    var currentStatus = 0;
    $('input[name=rdUser]').each(function(){
        $(this).click(function(){
            currentId = $(this).val();
            currentRow = $(this).attr('currentRow');
            currentStatus = $(this).attr('currentStatus');
        });
    });
    
    
    $('#btActivate').click(function () {
        if(currentId <= 0){
            showNotice('No user choose');
            return;
        }
        if(currentStatus === '1'){
            showNotice('the user already activate');
            return;
        }
        $.ajax({
            type: "PUT",
            url: baseUrl + "/manager/activate/" + currentId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    tbUsers = $('#tbUsers').get(0).tBodies[0];
                    var tr = tbUsers.rows[currentRow];
                    tr.cells[8].removeChild(tr.cells[8].children[0]);
                    tr.cells[8].innerHTML = '<i class="fa fa-check text-muted" status="1"></i>';
                    tr.cells[0].children[0].setAttribute("currentStatus", "1");
                    currentStatus = "1";
                }
                showNotice(response.message);
            },
            error: function () {
                hideLoading();
                showNotice('communication fail, try again later');
            }
        });
    });

    $('#btLimited').click(function () {
        if(currentId <= 0){
            showNotice('have no choose user');
            return;
        }
        updateUserLevel(currentId, 0, "");
    });

    $('#seUpdateLevel').change(function () {
        updateLevel = $(this).val();
    });

    var expiresTime = "";
    $('#ipExpiresTime').change(function () {
        expiresTime = $(this).val();
        expiresTime = expiresTime + ' 00:00:00';
    });

    $('#btUpdateLevel').click(function () {
        if(currentId <= 0){
            showNotice('have no choose user');
            return;
        }
        if(updateLevel <= 0 ){
            showNotice('have no choose level');
            return;
        }
        if(expiresTime.length < 19 ){
            showNotice('have no choose expires date');
            return;
        }
        console.log(expiresTime);
        if(!validateDateFormat(expiresTime)){
            showNotice('expires date format error');
            return;
        }
        updateUserLevel(currentId, updateLevel, expiresTime)
    });

    function updateUserLevel(userId, level, expiresTime) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/manager/update/level/" + userId + "/" + level,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({'expiresTime': expiresTime}),
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    tbUsers = $('#tbUsers').get(0).tBodies[0];
                    var td = tbUsers.rows[currentRow].cells[7];
                    td.removeChild(td.children[0]);
                    if(level === 0){
                        td.innerHTML = "<span style=\"color: #a01c34\"><i class=\"fa fa-lock text-danger\"></i></span>"
                    }else if (level === '5'){
                        td.innerHTML = "<span>fto</span>";
                        tbUsers.rows[currentRow].cells[6].innerHTML = response['data']['expiresTime'];
                    }else{
                        td.innerHTML = "<span>" + level + "</span>";
                        tbUsers.rows[currentRow].cells[6].innerHTML = response['data']['expiresTime'];
                    }
                }
                showNotice(response.message);
            },
            error: function () {
                hideLoading();
                showNotice('communication fail, try again later');
            }
        });
    }



    $('#btDelete').click(function () {
        if(currentId <= 0){
            showNotice('have no choose user');
            return;
        }
        $('#modalDelete').modal('show');
    });

    $('#btSubmitDelete').click(function () {
        deleteUser();
    });

    function deleteUser() {
        if(currentId <= 0){
            showNotice('have no choose user');
            return;
        }
        $.ajax({
            type: "DELETE",
            url: baseUrl + "/manager/delete/" + currentId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {
                showLoading();
                $('#modalDelete').modal('hide');
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    $('#modalDelete').modal('hide');
                    tbUsers = $('#tbUsers').get(0).tBodies[0];
                    var tr = tbUsers.rows[currentRow];
                    tbUsers.removeChild(tr);
                }else{
                    $('#modalDelete').modal('show');
                    showErrorMessage($('#errorDelete'), response.message);
                }
            },
            error: function () {
                hideLoading();
                $('#modalDelete').modal('show');
                showErrorMessage($('#errorDelete'), 'communication fail, try again later');
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
        showOnlineAndTotalCount();
    }

    var currentSearchIndex = 0;
    $('input[name=searchRadio]').each(function(){
        $(this).click(function(){
            currentSearchIndex = parseInt($(this).val());
        });
    });

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        filterSearch(key, currentSearchIndex);
    });

    $('#ipLevelAll').click(function () {
        showAllRows();
    });
    $('#ipLevelFto').click(function () {
        filterSearch('fto', 7);
    });
    $('#ipLevel4').click(function () {
        filterSearch('4', 7);
    });
    $('#ipLevel3').click(function () {
        filterSearch('3', 7);
    });
    $('#ipLevel2').click(function () {
        filterSearch('2', 7);
    });
    $('#ipLevel1').click(function () {
        filterSearch('1', 7);
    });
    $('#ipLevel0').click(function () {
        filterSearch('0', 7);
    });

    $('#ipStatusAll1').click(function () {
        showAllRows();
    });
    $('#ipStatusActivate').click(function () {
        filterSearch('1', 8);
    });
    $('#ipStatusDeactivate').click(function () {
        filterSearch('0', 8);
    });

    $('#ipStatusAll').click(function () {
        showAllRows();
        showOnlineAndTotalCount();
    });
    $('#ipStatusOnline').click(function () {
        filterSearch('true', 9);
    });
    $('#ipStatusOffline').click(function () {
        filterSearch('false', 9);
    });
    
    function filterSearch(key, currentSearchIndex) {
        if(key.length <= 0){
            showAllRows();
        }else{
            console.log(key);
            if(currentSearchIndex > 0) {
                for (var m = 0; m < rowsLength; m ++) {
                    var content = tbUsers.rows[m].cells[currentSearchIndex].innerHTML.toLowerCase();
                    if (content.search(key) >= 0) {
                        tbUsers.rows[m].style.display = "";
                    } else {
                        tbUsers.rows[m].style.display = "none";
                    }
                }
            }else if(currentSearchIndex === 0){
                for (var k = 0; k < rowsLength; k++) {
                    for (var j = 1; j < 8; j++) {
                        var content1 = tbUsers.rows[k].cells[j].innerHTML.toLowerCase();
                        if (content1.search(key) >= 0) {
                            tbUsers.rows[k].style.display = "";
                            break
                        } else {
                            tbUsers.rows[k].style.display = "none";
                        }
                    }
                }
            }
        }
        showOnlineAndTotalCount();
    }


    /**
     * set more click event to show user details for every rows
     */
    for(var i = 0; i < rowsLength; i ++){
        tbUsers.rows[i].cells[10].onclick = function(){
            var key = this.parentNode.cells[1].innerHTML;
            getUserDetailInfoByKey(key)
        }
    }

    var tbUserDetails = $('#tbUserDetails').get(0).tBodies[0];
    /**
     * show user details
     * @param userId user id
     */
    function getUserDetailInfoByKey(userId) {
        var url = baseUrl + "/manager/user/" + userId;
        showLoading();
        $.get(url,{}, function (response, status) {
            hideLoading();
            if(status === "success") {
                tbUserDetails.rows[0].cells[1].innerHTML = response['username'];
                tbUserDetails.rows[1].cells[1].innerHTML = response['password'];
                tbUserDetails.rows[2].cells[1].innerHTML = response['mac'];
                tbUserDetails.rows[3].cells[1].innerHTML = response['firstName'];
                tbUserDetails.rows[4].cells[1].innerHTML = response['lastName'];
                tbUserDetails.rows[5].cells[1].innerHTML = response['email'];
                tbUserDetails.rows[6].cells[1].innerHTML = response['phone'];
                tbUserDetails.rows[7].cells[1].innerHTML = response['createTime'];
                tbUserDetails.rows[8].cells[1].innerHTML = response['activeTime'];
                tbUserDetails.rows[9].cells[1].innerHTML = response['level'];
                tbUserDetails.rows[10].cells[1].innerHTML = response['expiresTime'];
                tbUserDetails.rows[11].cells[1].innerHTML = response['status'];
                tbUserDetails.rows[12].cells[1].innerHTML = response['deviceModel'];
                tbUserDetails.rows[13].cells[1].innerHTML = response['romVersion'];
                tbUserDetails.rows[14].cells[1].innerHTML = response['uiVersion'];
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

    $('#btPrint').click(function () {
        $('#dTable').printThis();
    });
    
    
    $('#btExport').click(function () {
        var macs = [];
        var length = tbUsers.rows.length;
        for(var i = 0; i < length; i ++){
            var tr = tbUsers.rows[i];
            if(tr.style.display !== "none"){
                var mac = tr.cells[3].innerHTML;
                macs.push(mac);
            }
        }
        if(macs.length <= 0){
            showNotice('no user data');
            return;
        }
        $.ajax({
            type: "PUT",
            url: baseUrl + "/manager/users/export",
            data: {"macs": macs},
            dataType: "json",
            beforeSend: function () {
                showLoading();
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    var url = response['message'];
                    window.open(url, '_blank')
                }else{
                    showNotice(response.message);
                }
            },
            error: function () {
                hideLoading();
                showNotice('communication fail, try again later');
            }
        });
    });


});