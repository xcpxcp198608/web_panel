$(function () {

    // var now = new Date();
    // var currentYear = now.getFullYear();
    // var currentMonth = now.getMonth() + 1;
    var tbSales = $('#tbSales').get(0).tBodies[0];
    var errorMessage = $('#errorMessage');
    // var topLimit = 5;
    //
    // setYearAndMonth();
    // function setYearAndMonth() {
    //     $('#aYear').html(currentYear);
    //     if(currentMonth < 10){
    //         $('#aMonth').html("0" + currentMonth);
    //     }else {
    //         $('#aMonth').html(currentMonth);
    //     }
    // }

    // /**
    //  * get all sales commission by month
    //  */
    // var tbCommissionByMonth = $('#tbCommissionByMonth').get(0).tBodies[0];
    // getAllSalesCommissionByMonth(currentYear, currentMonth);
    // function getAllSalesCommissionByMonth(year, month) {
    //     $('#btPreviousMonth').attr('disabled', 'disabled');
    //     $('#btNextMonth').attr('disabled', 'disabled');
    //     clearTableCommissionByMonth();
    //     var url = baseUrl + '/admin/chart/sales/commission/' + year + "/" + month;
    //     $.get(url, function (allSalesCommissionInfoList, status) {
    //         var length = allSalesCommissionInfoList.length;
    //         for(var i = 0; i < length; i ++){
    //             var commissionInfo = allSalesCommissionInfoList[i];
    //             var tdObj = document.createElement('td');
    //             tdObj.innerHTML = commissionInfo['salesUsername'];
    //             tbCommissionByMonth.rows[0].append(tdObj);
    //             var tdObj1 = document.createElement('td');
    //             tdObj1.innerHTML = commissionInfo['commission'];
    //             tbCommissionByMonth.rows[1].append(tdObj1);
    //         }
    //         $('#btPreviousMonth').removeAttr('disabled');
    //         $('#btNextMonth').removeAttr('disabled');
    //     })
    // }
    //
    // function clearTableCommissionByMonth() {
    //     var length = tbCommissionByMonth.rows[0].cells.length - 2;
    //     for(var i = 0; i < length; i ++){
    //         tbCommissionByMonth.rows[0].removeChild(tbCommissionByMonth.rows[0].lastChild);
    //         tbCommissionByMonth.rows[1].removeChild(tbCommissionByMonth.rows[1].lastChild);
    //     }
    // }

    // /**
    //  * set button event
    //  */
    // $('#btPreviousMonth').click(function () {
    //     currentMonth --;
    //     if(currentMonth < 1){
    //         currentYear --;
    //         currentMonth = 12;
    //     }
    //     setYearAndMonth();
    //     getAllSalesCommissionByMonth(currentYear, currentMonth);
    // });
    // $('#btNextMonth').click(function () {
    //     currentMonth ++;
    //     if(currentMonth > 12){
    //         currentYear ++;
    //         currentMonth = 1;
    //     }
    //     setYearAndMonth();
    //     getAllSalesCommissionByMonth(currentYear, currentMonth);
    // });

    /**
     * button create click event
     */
    $('#btCreate').click(function () {
        showDetail();
    });

    /**
     * radio click event
     */
    var currentSalesId = 0;
    var currentRow = 0;
    $('input[name=update]').each(function(){
        $(this).click(function(){
            currentSalesId = $(this).val();
            currentRow = $(this).attr('currentRow');
        });
    });

    /**
     * button update click event
     */
    $('#btUpdate').click(function () {
        if(currentSalesId <= 0){
            showNotice('no sales was choose');
            return;
        }
        var newPassword = tbSales.rows[currentRow].cells[3].firstChild.value;
        if(newPassword.length < 6){
            showNotice('password format error, length < 6');
            return;
        }
        $.ajax({
            type: "PUT",
            url: baseUrl + "/admin/dealer/update",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"id": currentSalesId, "password": newPassword}),
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    showNotice(response.message);
                }else{
                    errorMessage.html(response.message);
                }
            },
            error: function () {
                hideLoading();
                errorMessage.html('communication fail, try again later');
            }
        });
    });

    /**
     * submit create sales
     */
    $('#btSubmitCreate').click(function () {
        var username = $('#ipUsername').val();
        var ssn = $('#ipSSN').val();
        var password = $('#ipPassword').val();
        var password1 = $('#ipPassword1').val();
        var firstName = $('#ipFirstName').val();
        var lastName = $('#ipLastName').val();
        var email = $('#ipEmail').val();
        var phone = $('#ipPhone').val();
        if(username.length <= 0){
            errorMessage.html('username no type in');
            return;
        }
        if(ssn.length !== 9){
            errorMessage.html('ssn type in error');
            return;
        }
        if(password.length < 6){
            errorMessage.html('password type in error');
            return;
        }
        if(password1.length < 6){
            errorMessage.html('password type in error');
            return;
        }
        if(password !== password1){
            errorMessage.html('password type in no match');
            return;
        }
        if(firstName.length <= 0){
            errorMessage.html('first name no type in');
            return;
        }
        if(lastName.length <= 0){
            errorMessage.html('last name no type in');
            return;
        }
        if(email.length <= 0){
            errorMessage.html('email no type in');
            return;
        }
        if(!validateEmail(email)){
            errorMessage.html('email format error');
            return;
        }
        if(phone.length <= 0){
            errorMessage.html('phone no type in');
            return;
        }
        errorMessage.html('');
        $.ajax({
            type: "POST",
            url: baseUrl + "/admin/dealer/create",
            data: {"username": username, "password": password, "firstName": firstName,
                'lastName': lastName, 'ssn': ssn, 'email': email, 'phone': phone},
            dataType: "json",
            beforeSend: function () {
                showLoading()
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    hideDetail();
                    showNotice(response.message);
                    insertNewSalesInTable(response.data);
                }else{
                    errorMessage.html(response.message);
                }
            },
            error: function () {
                hideLoading();
                errorMessage.html('communication fail, try again later');
            }
        });
    });

    /**
     * insert create successfully sales into table
     * @type {jQuery}
     */
    var trSales = $('#trSales').get(0);
    function insertNewSalesInTable(authSalesInfo) {
        console.log(authSalesInfo);
        var trObj = document.createElement('tr');
        var length = trSales.cells.length;
        for(var i = 0; i < length; i ++){
            var tdObj = document.createElement('td');
            switch (i){
                case 0:
                    var radioInput = document.createElement('input');
                    radioInput.setAttribute('type', 'radio');
                    radioInput.setAttribute('name', 'update');
                    radioInput.setAttribute('value', authSalesInfo.id);
                    radioInput.setAttribute('currentRow', parseInt(tbSales.rows[tbSales.rows.length - 1].cells[1].innerHTML).toString());
                    radioInput.onclick = function () {
                        currentSalesId = this.value;
                        currentRow = this.getAttribute('currentRow');
                    };
                    tdObj.append(radioInput);
                    break;
                case 1:
                    tdObj.innerHTML = parseInt(tbSales.rows[tbSales.rows.length - 1].cells[1].innerHTML) + 1;
                    break;
                case 2:
                    tdObj.innerHTML = authSalesInfo.username;
                    break;
                case 3:
                    var pwdInput = document.createElement('input');
                    pwdInput.setAttribute('type', 'text');
                    pwdInput.setAttribute('value', authSalesInfo.password);
                    pwdInput.setAttribute('title', 'pwd');
                    pwdInput.setAttribute('size', '12');
                    tdObj.append(pwdInput);
                    break;
                case 4:
                    tdObj.innerHTML = authSalesInfo.firstName + " " + authSalesInfo.lastName;
                    break;
                case 5:
                    var ssn = authSalesInfo.ssn.toString();
                    tdObj.innerHTML = ssn.substring(0, 3) + '-' + ssn.substring(3, 5) + '-'+ ssn.substring(5, 9);
                    break;
                case 6:
                    tdObj.innerHTML = authSalesInfo.email;
                    break;
                case 7:
                    tdObj.innerHTML = authSalesInfo.phone;
                    break;
                case 8:
                    tdObj.innerHTML = authSalesInfo.createTime.substring(0, 19);
                    tdObj.setAttribute('class', 'tdRows12');
                    break;
                case 9:
                    tdObj.innerHTML = '<a href="/panel/admin/users/'+ authSalesInfo.id+'">\n' +
                        '                 <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>\n' +
                        '              </a>';
                    break;
            }
            trObj.append(tdObj);
        }
        tbSales.append(trObj);
    }


    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
            return;
        }
        var rowsLength = tbSales.rows.length;
        for(var i = 0; i < rowsLength; i ++){
            var cellsLength = tbSales.rows[i].cells.length - 1;
            for(var j = 2; j < cellsLength; j ++){
                var content = tbSales.rows[i].cells[j].innerHTML.toLowerCase();
                if(content.search(key) >= 0){
                    tbSales.rows[i].style.display = "";
                    break
                }else{
                    tbSales.rows[i].style.display = "none";
                }
            }
        }
    });

    function showAllRows() {
        var rowsLength = tbSales.rows.length;
        for(var i =0 ; i < rowsLength; i ++){
            tbSales.rows[i].style.display = "";
        }
    }

});