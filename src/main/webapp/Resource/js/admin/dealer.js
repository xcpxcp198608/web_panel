$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;

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
     * get all sales commission by month
     */
    var tbDealerCommission = $('#tbDealerCommission').get(0).tBodies[0];
    getAllDealersCommissionByMonth(currentYear, currentMonth);
    function getAllDealersCommissionByMonth(year, month) {
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        clearTableCommissionByMonth();
        var url = baseUrl + '/admin/chart/dealer/commission/' + year + "/" + month;
        $.get(url, function (list, status) {
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
            $('#maxVolumeDealer').html('xxx');
            $('#maxVolume').html(0);
            var length = list.length;
            var totalCommission = 0;
            for(var i = 0; i < length; i ++){
                var commissionInfo = list[i];
                var trObj = document.createElement('tr');

                var tdObj1 = document.createElement('td');
                tdObj1.innerHTML = (i + 1).toString();
                trObj.append(tdObj1);

                var tdObj2 = document.createElement('td');
                tdObj2.innerHTML = commissionInfo['dealerUsername'];
                trObj.append(tdObj2);
                if(i === 0){
                    $('#maxVolumeDealer').html(commissionInfo['dealerUsername'])
                }

                var tdObj3 = document.createElement('td');
                tdObj3.innerHTML = commissionInfo['volume'];
                trObj.append(tdObj3);
                if(i === 0){
                    $('#maxVolume').html(commissionInfo['volume'])
                }

                var tdObj4 = document.createElement('td');
                tdObj4.innerHTML = commissionInfo['commission'];
                trObj.append(tdObj4);
                totalCommission += parseInt(commissionInfo['commission']);

                tbDealerCommission.append(trObj);
            }
            $('#totalCommission').html(totalCommission);
        })
    }

    function clearTableCommissionByMonth() {
        var length = tbDealerCommission.rows.length;
        for(var i = 0; i < length; i ++){
            tbDealerCommission.removeChild(tbDealerCommission.rows[0]);
        }
    }

    /**
     * set button event
     */
    $('#btPreviousMonth').click(function () {
        currentMonth --;
        if(currentMonth < 1){
            currentYear --;
            currentMonth = 12;
        }
        setYearAndMonth();
        getAllDealersCommissionByMonth(currentYear, currentMonth);
    });
    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMonth();
        getAllDealersCommissionByMonth(currentYear, currentMonth);
    });


    /**
     * filter by search input keyword
     */
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

    /**
     * button create click event
     */
    $('#btCreate').click(function () {
        $('#modalCreate').modal('show')
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
            showErrorMessage($('#errorCreate'), "username type in error");
            return;
        }
        if(ssn.length !== 9){
            showErrorMessage($('#errorCreate'), 'ssn type in error');
            return;
        }
        if(firstName.length <= 0){
            showErrorMessage($('#errorCreate'), 'first name no type in');
            return;
        }
        if(lastName.length <= 0){
            showErrorMessage($('#errorCreate'), 'last name no type in');
            return;
        }
        if(email.length <= 0){
            showErrorMessage($('#errorCreate'), 'email no type in');
            return;
        }
        if(!validateEmail(email)){
            showErrorMessage($('#errorCreate'), 'email format error');
            return;
        }
        if(phone.length <= 0){
            showErrorMessage($('#errorCreate'), 'phone no type in');
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
        $('#errorCreate').hide();
        $('#btSubmitCreate').attr('disabled');
        $('#modalCreate').modal('hide');
        $.ajax({
            type: "POST",
            url: baseUrl + "/admin/dealer/create",
            data: {"username": username, "password": password, "firstName": firstName,
                'lastName': lastName, 'ssn': ssn, 'email': email, 'phone': phone},
            dataType: "json",
            beforeSend: function () {
                $('#modalCreate').modal('hide');
                showLoading()
            },
            success: function (response) {
                $('#btSubmitCreate').removeAttr('disabled');
                hideLoading();
                if(response.code === 200) {
                    insertNewSalesInTable(response.data);
                    $('#modalCreate').modal('hide');
                }else{
                    $('#modalCreate').modal('show');
                    showErrorMessage($('#errorCreate'), response.message);
                }
            },
            error: function () {
                hideLoading();
                $('#btSubmitCreate').removeAttr('disabled');
                $('#modalCreate').modal('show');
                showErrorMessage($('#errorCreate'), 'communication fail, try again later');
            }
        });
    });

    /**
     * insert create successfully sales into table
     * @type {jQuery}
     */
    var tbDealers = $('#tbDealers').get(0).tBodies[0];
    function insertNewSalesInTable(authSalesInfo) {
        var trObj = document.createElement('tr');
        var length = tbDealers.rows[0].cells.length;
        for(var i = 0; i < length; i ++){
            var tdObj = document.createElement('td');
            switch (i){
                case 0:
                    var radioInput = document.createElement('input');
                    radioInput.setAttribute('type', 'radio');
                    radioInput.setAttribute('name', 'update');
                    radioInput.setAttribute('value', authSalesInfo.id);
                    radioInput.setAttribute('currentRow', parseInt(tbDealers.rows[tbDealers.rows.length - 1].cells[1].innerHTML).toString());
                    radioInput.onclick = function () {
                        currentSalesId = this.value;
                        currentRow = this.getAttribute('currentRow');
                    };
                    tdObj.append(radioInput);
                    break;
                case 1:
                    tdObj.innerHTML = parseInt(tbDealers.rows[tbDealers.rows.length - 1].cells[1].innerHTML) + 1;
                    break;
                case 2:
                    tdObj.innerHTML = authSalesInfo.username;
                    break;
                case 3:
                    tdObj.innerHTML = authSalesInfo.password;
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
                    tdObj.innerHTML = '<a href="/panel/admin/users/1/'+ authSalesInfo.id+'" target="_blank">\n' +
                        '                 <i class="fa fa-eye"></i>\n' +
                        '              </a>';
                    break;
            }
            trObj.append(tdObj);
        }
        tbDealers.append(trObj);
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
            showNotice('have no choose dealer');
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
        $('#modalUpdate').modal('hide');
        $.ajax({
            type: "PUT",
            url: baseUrl + "/admin/dealer/update",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"id": currentSalesId, "password": password}),
            dataType: "json",
            beforeSend: function () {
                $('#modalUpdate').modal('hide');
                showLoading()
            },
            success: function (response) {
                $('#btSubmitUpdate').removeAttr('disabled');
                hideLoading();
                if(response.code === 200) {
                    $('#modalUpdate').modal('hide');
                    tbDealers.rows[parseInt(currentRow)].cells[3].innerHTML = password;
                }else{
                    $('#modalUpdate').modal('show');
                    showErrorMessage($('#errorUpdate'), response.message);
                }
            },
            error: function () {
                hideLoading();
                $('#modalUpdate').modal('show');
                $('#btSubmitUpdate').removeAttr('disabled');
                showErrorMessage($('#errorUpdate'), 'communication fail, try again later');
            }
        });
    });


});

var dealer = {
    init: function () {

    }
};