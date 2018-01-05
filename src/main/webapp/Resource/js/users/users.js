$(function () {

    var tbUsers = $('#tbUsers').get(0).tBodies[0];
    var tbUserDetails = $('#tbUserDetails').get(0).tBodies[0];
    var rowsLength = tbUsers.rows.length;

    showOnlineAndTotalCount();
    /**
     * set more click event to show user details for every rows
     */
    for(var i = 0; i < rowsLength; i ++){
        tbUsers.rows[i].cells[11].onclick = function(){
            var userId = this.parentNode.cells[1].innerHTML;
            getUserDetailInfoById(userId)
        }
    }

    /**
     * show user details
     * @param userId
     */
    function getUserDetailInfoById(userId) {
        var url = baseUrl + "/manager/user/" + userId;
        loading.css('display', 'block');
        $.get(url,{}, function (response, status) {
            loading.css('display', 'none');
            if(status === "success") {
                tbUserDetails.rows[0].cells[1].innerHTML = response['username'];
                tbUserDetails.rows[1].cells[1].innerHTML = response['password'];
                tbUserDetails.rows[2].cells[1].innerHTML = response['mac'];
                tbUserDetails.rows[3].cells[1].innerHTML = response['firstName'];
                tbUserDetails.rows[4].cells[1].innerHTML = response['lastName'];
                tbUserDetails.rows[5].cells[1].innerHTML = response['email'];
                tbUserDetails.rows[6].cells[1].innerHTML = response['phone'];
                tbUserDetails.rows[7].cells[1].innerHTML = response['createTime'];
                tbUserDetails.rows[8].cells[1].innerHTML = response['activateTime'];
                tbUserDetails.rows[9].cells[1].innerHTML = response['level'];
                tbUserDetails.rows[10].cells[1].innerHTML = response['expiresTime'];
                tbUserDetails.rows[11].cells[1].innerHTML = response['status'];
                tbUserDetails.rows[12].cells[1].innerHTML = response['country'];
                tbUserDetails.rows[13].cells[1].innerHTML = response['region'];
                tbUserDetails.rows[14].cells[1].innerHTML = response['city'];
                tbUserDetails.rows[15].cells[1].innerHTML = response['timeZone'];
                tbUserDetails.rows[16].cells[1].innerHTML = response['lastOnLineTime'];
                dDetails.css('display', 'block')
            }else{
                showNotice('communication error')
            }
        })
    }

    /**
     * show current online and total user count in table of users
     */
    function showOnlineAndTotalCount() {
        var count = 0;
        var onlineCount = 0;
        rowsLength = tbUsers.rows.length;
        for(var x =0 ; x < rowsLength; x ++){
            var status = tbUsers.rows[x].style.display;
            var online = tbUsers.rows[x].cells[10].childNodes[1].getAttribute("online");
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

    /**
     * display all rows in table of users
     */
    function showAllRows() {
        rowsLength = tbUsers.rows.length;
        for(var i =0 ; i < rowsLength; i ++){
            tbUsers.rows[i].style.display = "";
        }
    }

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            rowsLength = tbUsers.rows.length;
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 8; j ++){
                    var content = tbUsers.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tbUsers = $('#tbUsers').get(0).tBodies[0];
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


    $('#seLevel').change(function () {
        var key = $(this).val();
        if(key >= 0){
            rowsLength = tbUsers.rows.length;
            for(var i =0 ; i < rowsLength; i ++){
                tbUsers = $('#tbUsers').get(0).tBodies[0];
                if(tbUsers.rows[i].cells[9].children[0].innerHTML === key){
                    tbUsers.rows[i].style.display = "";
                }else{
                    tbUsers.rows[i].style.display = "none";
                }
            }
        }else{
            showAllRows();
        }
        showOnlineAndTotalCount();
    });




    function selectChangeListener(key, cellIndex) {
        if(key.length >0){
            for(var i =0 ; i < rowsLength; i ++){
                if(tbUsers.rows[i].cells[cellIndex].innerHTML === key){
                    tbUsers.rows[i].style.display = "";
                }else{
                    tbUsers.rows[i].style.display = "none";
                }
            }
        }else{
            showAllRows();
        }
        showOnlineAndTotalCount();
    }


    var currentRow = 0;
    var currentId = 0;
    var updateLevel = 0;
    var updateDays = -1;
    var currentStatus = 0;

    $('input[name=rdUser]').each(function(){
        $(this).click(function(){
            currentId = $(this).val();
            currentRow = $(this).attr('currentRow');
            currentStatus = $(this).attr('currentStatus');
        });
    });

    $('#seUpdateLevel').change(function () {
        updateLevel = $(this).val();
    });

    $('#seDays').change(function () {
        updateDays = $(this).val();
    });
    
    
    $('#btActivate').click(function () {
        if(currentId <= 0){
            showNotice('have no choose user');
            return;
        }
        console.log(currentStatus);
        if(currentStatus === '1'){
            showNotice('already activate');
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
                    tr.cells[8].innerHTML = "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\" style=\"color: #9f9f9f\"\n" +
                        "                                  status=\"1\"></span>"
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
        updateUserLevel(currentId, 0, 0);
    });

    $('#btDelete').click(function () {
        if(currentId < 0){
            showNotice('have no choose user');
            return;
        }
        $.ajax({
            type: "DELETE",
            url: baseUrl + "/manager/delete/" + currentId,
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
                    tbUsers.removeChild(tr);
                }
                showNotice(response.message);
            },
            error: function () {
                hideLoading();
                showNotice('communication fail, try again later');
            }
        });
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
        if(updateDays < 0 ){
            showNotice('have no choose days');
            return;
        }
        updateUserLevel(currentId, updateLevel, updateDays)
    });

    function updateUserLevel(userId, level, days) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/manager/update/level/" + userId + "/" + level + "/" + days,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    tbUsers = $('#tbUsers').get(0).tBodies[0];
                    var td = tbUsers.rows[currentRow].cells[9];
                    td.removeChild(td.children[0]);
                    if(level === 0){
                        td.innerHTML = "<span style=\"color: #a01c34\">0</span>"
                    }else if (level === '5'){
                        td.innerHTML = "<span>fto</span>";
                        tbUsers.rows[currentRow].cells[7].innerHTML = response['data']['expiresTime'];
                    }else{
                        td.innerHTML = "<span>" + level + "</span>";
                        tbUsers.rows[currentRow].cells[7].innerHTML = response['data']['expiresTime'];
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

});