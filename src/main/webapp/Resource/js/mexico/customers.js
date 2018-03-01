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
    
    $('#btCanceled').click(function () {
        if(currentRow === 0 || currentClientKey.length <= 0 || currentStatus.length <= 0){
            showNotice('No user chosen');
            return;
        }
        if(currentStatus === 'canceled'){
            showNotice('this user already canceled');
            return;
        }
        if(currentStatus === 'activate'){
            showNotice('this user in activate, can not cancel');
            return;
        }
        $('#modalCancel').modal('show');
        $('#btSubmitCancel').click(function () {
            $('#modalCancel').modal('hide');
            changeUserStatus('canceled', currentClientKey);
        });
    });

    function changeUserStatus(status, key) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/mexico/customer/" + status + "/" + key,
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
                        tbUsers.rows[currentRow].cells[6].children[0].innerHTML =
                            '<span class="text-success">' +status+ '</span>';
                    }else if('canceled' === status){
                        tbUsers.rows[currentRow].cells[6].children[0].innerHTML =
                            '<span class="text-secondary">' +status+ '</span>';
                    }else{

                        tbUsers.rows[currentRow].cells[6].children[0].innerHTML =
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

    $('#btCashActivate').click(function () {
        if(currentRow === 0 || currentClientKey.length <= 0 || currentStatus.length <= 0){
            showNotice('No user chosen');
            return;
        }
        if(currentStatus === 'canceled'){
            showNotice('this user already canceled');
            return;
        }
        if(currentStatus === 'activate'){
            showNotice('this user already activated');
            return;
        }
        if(currentStatus === 'limited'){
            showNotice('this user already limited');
            return;
        }
        $('#modalCashActivate').modal('show');
        $('#btSubmitCashActivate').click(function () {
            var password = $('#ipAdminPassword').val();
            if(password.length <= 0){
                showErrorMessage($('#errorCashActivate'), 'password input error');
                return;
            }
            activateWithCash(currentClientKey, password);
        });
    });
    
    function activateWithCash(currentClientKey, password) {
        $.ajax({
            type: "PUT",
            url: baseUrl + "/mexico/customer/cash_activate",
            data: {"password": password, "key": currentClientKey},
            dataType: "json",
            beforeSend: function () {
                showLoading();
                $('#modalCashActivate').modal('hide');
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    $('#modalCashActivate').modal('hide');
                    tbUsers.rows[currentRow].cells[6].children[0].innerHTML =
                        '<span class="text-success">activate</span>';
                    currentStatus = status;
                }else{
                    $('#modalCashActivate').modal('show');
                    showErrorMessage($('#errorCashActivate'), response.message);
                }
            },
            error: function () {
                hideLoading();
                $('#modalCashActivate').modal('show');
                showErrorMessage($('#errorCashActivate'), 'communication fail, try again later');
            }
        });
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

    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 2; j < 7; j ++){
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
     * show current online and total user count in table of users
     */
    function showOnlineAndTotalCount() {
        var count = 0;
        var onlineCount = 0;
        for(var x =0 ; x < rowsLength; x ++){
            var status = tbUsers.rows[x].style.display;
            var online = tbUsers.rows[x].cells[7].childNodes[1].getAttribute("online");
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


    
    $('#btCreate').click(function () {
        $('#modalCreate').modal('show');
    });

    $('#btSubmitCreate').click(function () {
        var mac = $('#ipMac').val();
        var firstName = $('#ipFirstName').val();
        var lastName = $('#ipLastName').val();
        var email = $('#ipEmail').val();
        var phone = $('#ipPhone').val();
        if(mac.length !== 17){
            showErrorMessage($('#errorCreate'), 'mac input error');
            return;
        }
        if(firstName.length <= 0){
            showErrorMessage($('#errorCreate'), 'first name input error');
            return;
        }
        if(lastName.length <= 0){
            showErrorMessage($('#errorCreate'), 'last name input error');
            return;
        }
        if(email.length <= 0){
            showErrorMessage($('#errorCreate'), 'email input error');
            return;
        }
        if(!validateEmail(email)){
            showErrorMessage($('#errorCreate'), 'email format error');
            return;
        }
        if(phone.length <= 0){
            showErrorMessage($('#errorCreate'), 'phone input error');
            return;
        }
        $('#errorCreate').hide();
        var url = baseUrl + '/mexico/customer/create';
        $.ajax({
            type: 'POST',
            url: url,
            data: {'mac': mac, 'firstName': firstName, 'lastName': lastName,
                'email': email, 'phone': phone},
            dataType: 'json',
            beforeSend:function(){
                showLoading();
                $('#modalCreate').modal('hide');
            },
            success:function(response){
                hideLoading();
                if(response.code === 200) {
                    $('#modalCreate').modal('hide');
                    window.open(baseUrl + "/mexico/customers", "_self")
                }else{
                    $('#modalCreate').modal('show');
                    showErrorMessage($('#errorCreate'), response.message);
                }
            },
            error:function(){
                hideLoading();
                $('#modalCreate').modal('show');
                showErrorMessage($('#errorCreate'), 'communication error');
            }
        });
    });

});