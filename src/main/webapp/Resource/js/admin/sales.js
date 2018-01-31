$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;
    var tbSales = $('#tbSales').get(0).tBodies[0];
    var errorMessage = $('#errorMessage');
    var topLimit = 5;

    setYearAndMonth();
    function setYearAndMonth() {
        $('#aYear').html(currentYear);
        if(currentMonth < 10){
            $('#aMonth').html("0" + currentMonth);
        }else {
            $('#aMonth').html(currentMonth);
        }
    }

    // /**
    //  * init top5 volume chart
    //  */
    // var chartSalesVolume = echarts.init(document.getElementById('chart_sales_volume'));
    // var volumeXData = ['s1', 's2', 's3', 's4', 's5'];
    // var volumeData = [0, 0, 0, 0, 0];
    // var salesVolumeOption = {
    //     title: {
    //         text: 'Top '+topLimit+' of sales volume',
    //         textStyle:{
    //             color: 'rgba(255, 255, 255, 0.8)'
    //         }
    //     },
    //     backgroundColor: '#2c343c',
    //     textStyle: {
    //         color: 'rgba(255, 255, 255, 0.8)'
    //     },
    //     itemStyle: {
    //         normal: {
    //             color: function(params) {
    //                 var colorList = [
    //                     '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B'
    //                 ];
    //                 return colorList[params.dataIndex]
    //             },
    //             shadowBlur: 200,
    //             shadowColor: 'rgba(0, 0, 0, 0.7)'
    //         }
    //     },
    //     tooltip: {},
    //     xAxis: {
    //         data: volumeXData
    //     },
    //     yAxis: {},
    //     series: [{
    //         name: 'sales volume',
    //         type: 'bar',
    //         barWidth: 30,
    //         data: volumeData
    //     }]
    // };
    // chartSalesVolume.setOption(salesVolumeOption);
    //
    // /**
    //  * init top5 amount chart
    //  */
    // var chartSalesAmount = echarts.init(document.getElementById('chart_sales_amount'));
    // var amountXData = ['s1', 's2', 's3', 's4', 's5'];
    // var amountData = [0, 0, 0, 0, 0];
    // var salesAmountOption = {
    //     title: {
    //         text: 'Top '+topLimit+' of sales amount',
    //         textStyle:{
    //             color: 'rgba(255, 255, 255, 0.8)'
    //         }
    //     },
    //     backgroundColor: '#2c343c',
    //     textStyle: {
    //         color: 'rgba(255, 255, 255, 0.8)'
    //     },
    //     itemStyle: {
    //         normal: {
    //             color: function(params) {
    //                 var colorList = [
    //                     '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B'
    //                 ];
    //                 return colorList[params.dataIndex]
    //             },
    //             shadowBlur: 200,
    //             shadowColor: 'rgba(0, 0, 0, 0.5)'
    //         }
    //     },
    //     tooltip: {},
    //     xAxis: {
    //         data: amountXData
    //     },
    //     yAxis: {},
    //     series: [{
    //         name: 'sales amount',
    //         type: 'bar',
    //         barWidth: 30,
    //         data: amountData
    //     }]
    // };
    // chartSalesAmount.setOption(salesAmountOption);
    //
    // /**
    //  * get volume data
    //  */
    // getTopVolume(topLimit);
    // function getTopVolume(limit) {
    //     var url = baseUrl + '/admin/chart/sales/volume/' + limit;
    //     $.get(url, function (topVolumeList, status) {
    //         volumeXData.length = 0;
    //         volumeData.length = 0;
    //         var length = topVolumeList.length;
    //         for(var i = 0; i < topLimit; i ++){
    //             if(i >= length){
    //                 volumeXData.push(' ');
    //                 volumeData.push(0);
    //             }else{
    //                 volumeXData.push(topVolumeList[i]['salesName']);
    //                 volumeData.push(topVolumeList[i]['volume']);
    //             }
    //         }
    //         chartSalesVolume.setOption(salesVolumeOption);
    //     })
    // }
    //
    // /**
    //  * get amount data
    //  */
    // getTopAmount(topLimit);
    // function getTopAmount(limit) {
    //     var url = baseUrl + '/admin/chart/sales/amount/' + limit;
    //     $.get(url, function (topAmountList, status) {
    //         amountXData.length = 0;
    //         amountData.length = 0;
    //         var length = topAmountList.length;
    //         for(var i = 0; i < topLimit; i ++){
    //             if(i >= length){
    //                 amountXData.push(' ');
    //                 amountData.push(0);
    //             }else{
    //                 amountXData.push(topAmountList[i]['salesName']);
    //                 amountData.push(topAmountList[i]['amount']);
    //             }
    //         }
    //         chartSalesAmount.setOption(salesAmountOption);
    //     })
    // }


    /**
     * get all sales commission by month
     */
    var tbCommissionByMonth = $('#tbCommissionByMonth').get(0).tBodies[0];
    getAllSalesCommissionByMonth(currentYear, currentMonth);
    function getAllSalesCommissionByMonth(year, month) {
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        clearTableCommissionByMonth();
        var url = baseUrl + '/admin/chart/sales/commission/' + year + "/" + month;
        $.get(url, function (list, status) {
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
            $('#maxVolumeSales').html('xxx');
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
                tdObj2.innerHTML = commissionInfo['salesUsername'];
                trObj.append(tdObj2);
                if(i === 0){
                    $('#maxVolumeSales').html(commissionInfo['salesUsername'])
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

                tbCommissionByMonth.append(trObj);
            }
            $('#totalCommission').html(totalCommission);
        })
    }
    
    function clearTableCommissionByMonth() {
        var length = tbCommissionByMonth.rows.length;
        for(var i = 0; i < length; i ++){
            tbCommissionByMonth.removeChild(tbCommissionByMonth.rows[i]);
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
        getAllSalesCommissionByMonth(currentYear, currentMonth);
    });
    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMonth();
        getAllSalesCommissionByMonth(currentYear, currentMonth);
    });

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
        var dealerId = $('#ipDealerId').val();
        var activateCategory = $('#ipActivateCategory').val();
        var goldCategory = $('#ipGoldCategory').val();
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
        if(dealerId <= 0){
            showErrorMessage($('#errorCreate'), 'have no choose dealer');
            return;
        }
        if(activateCategory.length <= 0){
            showErrorMessage($('#errorCreate'), 'have no choose activate category');
            return;
        }
        $('#errorCreate').hide();
        $.ajax({
            type: "POST",
            url: baseUrl + "/admin/sale/create",
            data: {"username": username, "password": password, "firstName": firstName,
                'lastName': lastName, 'ssn': ssn, 'email': email, 'bankInfo': bank,
                'phone': phone, 'cardNumber': cardNumber, 'expirationDate': expirationDate,
                'securityKey': securityKey, 'dealerId': dealerId, 'activateCategory': activateCategory,
                'goldCategory': goldCategory},
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
    function insertNewSalesInTable(authSalesInfo) {
        var trObj = document.createElement('tr');
        var length = tbSales.rows[0].cells.length;
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
                    tdObj.innerHTML = authSalesInfo.dealerName;
                    break;
                case 5:
                    var ssn = authSalesInfo.ssn.toString();
                    tdObj.innerHTML = ssn.substring(0, 3) + '-' + ssn.substring(3, 5) + '-'+ ssn.substring(5, 9);
                    break;
                case 6:
                    tdObj.innerHTML = authSalesInfo.email;
                    break;
                case 7:
                    tdObj.innerHTML = authSalesInfo.bankInfo;
                    break;
                case 8:
                    tdObj.innerHTML = authSalesInfo.phone;
                    break;
                case 9:
                    tdObj.innerHTML = authSalesInfo.createTime;
                    break;
                case 10:
                    tdObj.innerHTML = '<span class="text-muted"><i class="fa fa-star"></i></span>';
                    break;
                case 11:
                    tdObj.innerHTML = '<span class="text-muted"><i class="fa fa-fire"></i></span>';
                    break;
                case 12:
                    tdObj.innerHTML = '<a href="/panel/admin/users/2/'+ authSalesInfo.id+'" target="_blank">\n' +
                        '                 <i class="fa fa-eye"></i>\n' +
                        '              </a>';
                    break;
                case 13:
                    tdObj.innerHTML = '<a href="/panel/admin/sales/detail/' + authSalesInfo.id + '" title="show sales detail information" target="_blank">\n' +
                        '                                    <i class="fa fa-share"></i>\n' +
                        '                                </a>';
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
            url: baseUrl + "/admin/sale/update",
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