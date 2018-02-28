$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;
    var tbSales = $('#tbSales').get(0).tBodies[0];

    setYearAndMonth();
    function setYearAndMonth() {
        $('#aYear').html(currentYear);
        if(currentMonth < 10){
            $('#aMonth').html("0" + currentMonth);
        }else {
            $('#aMonth').html(currentMonth);
        }
    }


    /**
     * button create click event
     */
    $('#btCreate').click(function () {
        $('#modalCreate').modal('show');
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
        var bank = $('#ipBank').val();
        var cardNumber = $('#ipCreditCard').val();
        var expirationDate = $('#ipExpirationDate').val();
        var securityKey = $('#ipSecurityKey').val();
        var zipCode = $('#ipZipCode').val();
        var billingAddress = $('#ipBillingAddress').val();
        var activateCategory = $('#ipActivateCategory').val();
        if(username.length <= 0){
            showErrorMessage($('#errorCreate'), 'username type in error');
            return;
        }
        if(ssn.length !== 9){
            showErrorMessage($('#errorCreate'), 'ssn type in error');
            return;
        }
        if(firstName.length <= 0){
            showErrorMessage($('#errorCreate'), 'first name type in error');
            return;
        }
        if(lastName.length <= 0){
            showErrorMessage($('#errorCreate'), 'last name type in error');
            return;
        }
        if(email.length <= 0){
            showErrorMessage($('#errorCreate'), 'email type in error');
            return;
        }
        if(!validateEmail(email)){
            showErrorMessage($('#errorCreate'), 'email format error');
            return;
        }
        if(phone.length <= 0){
            showErrorMessage($('#errorCreate'), 'phone type in error');
            return;
        }
        if(bank.length <= 0){
            showErrorMessage($('#errorCreate'), 'debit card info type in error');
            return;
        }
        if(cardNumber.length < 16){
            showErrorMessage($('#errorCreate'), 'card number type in error');
            return;
        }
        if(expirationDate.length !== 4){
            showErrorMessage($('#errorCreate'), 'expiration date type in error');
            return;
        }
        if(securityKey.length !== 3){
            showErrorMessage($('#errorCreate'), 'security key type in error');
            return;
        }
        if(zipCode.length <= 0){
            showErrorMessage($('#errorCreate'), 'zip code type in error');
            return;
        }
        if(billingAddress.length <= 0){
            showErrorMessage($('#errorCreate'), 'billing address type in error');
            return;
        }
        if(password.length < 6){
            showErrorMessage($('#errorCreate'), 'password type in error');
            return;
        }
        if(password1.length < 6){
            showErrorMessage($('#errorCreate'), 'password type in error');
            return;
        }
        if(password !== password1){
            showErrorMessage($('#errorCreate'), 'password type in no match');
            return;
        }
        if(activateCategory.length <= 0){
            showErrorMessage($('#errorCreate'), 'have no choose activate category');
            return;
        }
        $('#errorCreate').hide();
        $.ajax({
            type: "POST",
            url: baseUrl + "/dealer/sale/create",
            data: {"username": username, "password": password, "firstName": firstName,
                'lastName': lastName, 'ssn': ssn, 'email': email, 'bankInfo': bank,
                'phone': phone, 'cardNumber': cardNumber, 'expirationDate': expirationDate,
                'securityKey': securityKey, 'zipCode': zipCode, 'billingAddress': billingAddress,
                'activateCategory': activateCategory},
            dataType: "json",
            beforeSend: function () {
                $('#modalCreate').modal('hide');
                showLoading();
            },
            success: function (response) {
                hideLoading();
                if(response.code === 200) {
                    $('#modalCreate').modal('hide');
                    insertNewSalesInTable(response.data);
                }else{
                    $('#modalCreate').modal('show');
                    showErrorMessage($('#errorCreate'), response.message);
                }
            },
            error: function () {
                hideLoading();
                $('#modalCreate').modal('show');
                showErrorMessage($('#errorCreate'), 'communication fail, try again later');
            }
        });
    });

    /**
     * insert create successfully sales into table
     * @type {jQuery}
     */
    var trSales = $('#trSales').get(0);
    function insertNewSalesInTable(authSalesInfo) {
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
                    tdObj.innerHTML = authSalesInfo.password;
                    break;
                case 4:
                    var ssn = authSalesInfo.ssn.toString();
                    tdObj.innerHTML = ssn.substring(0, 3) + '-' + ssn.substring(3, 5) + '-'+ ssn.substring(5, 9);
                    break;
                case 5:
                    tdObj.innerHTML = authSalesInfo.email;
                    break;
                case 6:
                    tdObj.innerHTML = authSalesInfo.bankInfo;
                    break;
                case 7:
                    tdObj.innerHTML = authSalesInfo.phone;
                    break;
                case 8:
                    tdObj.innerHTML = authSalesInfo.createTime;
                    break;
                case 9:
                    tdObj.innerHTML = '<span class="text-muted"><i class="fa fa-star-o"></i></span>';
                    break;
                case 10:
                    tdObj.innerHTML = '<a href="/panel/dealer/users/2/' + authSalesInfo.id + '">\n' +
                        '                 <i class="fa fa-eye"></i>\n' +
                        '              </a>';
                    break;
            }
            trObj.append(tdObj);
        }
        tbSales.append(trObj);
    }


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
            showNotice('have no choose sales');
            return;
        }
        $('#modalUpdate').modal('show');
    });

    $('#btSubmitUpdate').click(function () {
        var password = $('#ipPassword3').val();
        var password1 = $('#ipPassword4').val();
        if(password.length < 6){
            showErrorMessage($('#errorUpdate'), 'password type in error');
            return;
        }
        if(password1.length < 6){
            showErrorMessage($('#errorUpdate'), 'password type in error');
            return;
        }
        if(password !== password1){
            showErrorMessage($('#errorUpdate'), 'password type in no match');
            return;
        }
        $('#errorUpdate').hide();
        $('#btSubmitUpdate').attr('disabled');
        $.ajax({
            type: "PUT",
            url: baseUrl + "/dealer/sale/update",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"id": currentSalesId, "password": password}),
            dataType: "json",
            beforeSend: function () {
                $('#modalUpdate').modal('hide');
                showLoading();
            },
            success: function (response) {
                $('#btSubmitUpdate').removeAttr('disabled');
                hideLoading();
                if(response.code === 200) {
                    $('#modalUpdate').modal('hide');
                    tbSales.rows[parseInt(currentRow)].cells[3].innerHTML = password;
                }else{
                    $('#modalUpdate').modal('show');
                    showErrorMessage($('#errorUpdate'), response.message);
                }
            },
            error: function () {
                $('#btSubmitUpdate').removeAttr('disabled');
                $('#modalUpdate').modal('show');
                showErrorMessage($('#errorUpdate'), 'communication fail, try again later');
            }
        });
    });


    /**
     * result filter
     */
    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
            return;
        }
        var rowsLength = tbSales.rows.length;
        for(var i = 0; i < rowsLength; i ++){
            var cellsLength = tbSales.rows[i].cells.length - 2;
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