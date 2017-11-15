$(function () {

    var tbOrders = $('#tbOrders').get(0).tBodies[0];
    var trTotal = $('#trTotal').get(0);
    var rowsLength = tbOrders.rows.length;

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;

    setYearAndMonth();
    showCurrentTotalInfoOfTableOrders();

    function setYearAndMonth() {
        $('#aYear').html(currentYear);
        if(currentMonth < 10){
            $('#aMonth').html("0" + currentMonth);
        }else {
            $('#aMonth').html(currentMonth);
        }
    }

    /**
     * init category chart
     */
    var tbCategory = $('#tbCategory').get(0).tBodies[0];
    var B1Chart = echarts.init(document.getElementById('B1Chart'));
    var P1Chart = echarts.init(document.getElementById('P1Chart'));
    var P2Chart = echarts.init(document.getElementById('P2Chart'));
    B1Chart.setOption({
        title:{
            text: 'B1'
        },
        series : [
            {
                name: 'B1',
                type: 'pie',
                radius: '60%',
                data:[
                    {value:tbCategory.rows[0].cells[3].innerHTML, name:'LD'},
                    {value:tbCategory.rows[0].cells[4].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[0].cells[5].innerHTML, name:'Sales'}
                ]
            }
        ]
    });
    P1Chart.setOption({
        title:{
            text: 'P1'
        },
        series : [
            {
                name: 'P1',
                type: 'pie',
                radius: '60%',
                data:[
                    {value:tbCategory.rows[1].cells[3].innerHTML, name:'LD'},
                    {value:tbCategory.rows[1].cells[4].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[1].cells[5].innerHTML, name:'Sales'}
                ]
            }
        ]
    });
    P2Chart.setOption({
        title:{
            text: 'P2'
        },
        series : [
            {
                name: 'P2',
                type: 'pie',
                radius: '60%',
                data:[
                    {value:tbCategory.rows[2].cells[3].innerHTML, name:'LD'},
                    {value:tbCategory.rows[2].cells[4].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[2].cells[5].innerHTML, name:'Sales'}
                ]
            }
        ]
    });

    /**
     * set button event
     */
    $('#btPreviousMonth').click(function () {
        currentMonth --;
        if(currentMonth < 1){
            currentYear --;
            currentMonth = 12;
            getOrdersByYear(currentYear);
        }
        setYearAndMonth();
        getOrdersByMonth(currentYear, currentMonth);
    });
    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
            getOrdersByYear(currentYear);
        }
        setYearAndMonth();
        getOrdersByMonth(currentYear, currentMonth);
    });


    /**
     * init year chart
     */
    var tbYearIncome = $('#tbYearIncome').get(0).tBodies[0];
    var yearIncomeChart = echarts.init(document.getElementById('yearIncomeChart'));
    var yearIncomeData = [];
    var yearIncomeChartOption = {
        title: {
            text: 'Year income',
            textStyle:{
                color: '#7c9f97'
            }
        },
        tooltip: {},
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        xAxis: {
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        },
        yAxis: {},
        series: [
            {
                name: 'income',
                type: 'line',
                data: yearIncomeData,
                itemStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#14f5a1',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    yearIncomeChart.setOption(yearIncomeChartOption);

    /**
     * get orders by year
     * @param year
     */
    getOrdersByYear(currentYear);
    function getOrdersByYear(year){
        var url = baseUrl + "/admin/orders/"+ year;
        $.post(url, {}, function (response, status) {
            var yearOrderList = response.dataList;
            yearIncomeData.length = 0;
            var yearData = transformYearOrderLists(yearOrderList);
            var yearIncomeDataList = yearData['incomeData'];
            var yearDepositDataList = yearData['depositData'];
            var yearLdDataList = yearData['ldData'];
            var yearDealerDataList = yearData['dealerData'];
            var yearSalesDataList = yearData['salesData'];
            var yearTotalIncome = 0;
            var yearTotalDeposit = 0;
            var yearTotalLdCommission = 0;
            var yearTotalDealerCommission = 0;
            var yearTotalSalesCommission = 0;
            for(var i = 0; i < 12; i ++){
                var data = yearIncomeDataList[i];
                yearIncomeData.push(data);
                yearTotalIncome += parseFloat(yearIncomeDataList[i]);
                yearTotalDeposit += parseFloat(yearDepositDataList[i]);
                yearTotalLdCommission += parseFloat(yearLdDataList[i]);
                yearTotalDealerCommission += parseFloat(yearDealerDataList[i]);
                yearTotalSalesCommission += parseFloat(yearSalesDataList[i]);
            }
            tbYearIncome.rows[0].cells[1].innerHTML = yearTotalIncome.toFixed(2);
            tbYearIncome.rows[1].cells[1].innerHTML = yearTotalDeposit.toFixed(2);
            tbYearIncome.rows[2].cells[1].innerHTML = yearTotalLdCommission.toFixed(2);
            tbYearIncome.rows[3].cells[1].innerHTML = yearTotalDealerCommission.toFixed(2);
            tbYearIncome.rows[4].cells[1].innerHTML = yearTotalSalesCommission.toFixed(2);
            yearIncomeChart.setOption(yearIncomeChartOption);
        })
    }

    /**
     * transform year orders
     * @param yearOrderList
     * @returns {{}}
     */
    function transformYearOrderLists(yearOrderList) {
        var yearData = {};
        var yearIncomeData = [];
        var yearDepositData = [];
        var yearLdCommissionData = [];
        var yearDealerCommissionData = [];
        var yearSalesCommissionData = [];
        var length = yearOrderList.length;
        for(var i = 1; i < 13; i++){
            var month = i < 10 ? '0' + i : i;
            var key = currentYear + '-' + month;
            var income = 0;
            var deposit = 0;
            var ld = 0;
            var dealer = 0;
            var sales = 0;
            for(var j = 0; j < length; j ++){
                var order = yearOrderList[j];
                if(order['tradingTime'].search(key) >= 0){
                    income += order['price'];
                    deposit += order['deposit'];
                    ld += order['ldCommission'];
                    dealer += order['dealerCommission'];
                    sales += order['salesCommission'];
                }
            }
            yearIncomeData.push(income.toFixed(2));
            yearDepositData.push(deposit.toFixed(2));
            yearLdCommissionData.push(ld.toFixed(2));
            yearDealerCommissionData.push(dealer.toFixed(2));
            yearSalesCommissionData.push(sales.toFixed(2));
        }
        yearData['incomeData'] = yearIncomeData;
        yearData['depositData'] = yearDepositData;
        yearData['ldData'] = yearLdCommissionData;
        yearData['dealerData'] = yearDealerCommissionData;
        yearData['salesData'] = yearSalesCommissionData;
        return yearData;
    }

    /**
     * init month income table data
     */
    var tbIncome = $('#tbMonthIncome').get(0).tBodies[0];
    var trIncome = $('#trIncome').get(0);
    var incomeRowsLength = tbIncome.rows.length;
    var currentDays = getDaysOfCurrentMonth();
    initIncomeData(currentDays);
    function initIncomeData(days) {
        for (var j = 0; j < days; j++) {
            var tdObj = document.createElement("td");
            tdObj.innerHTML = j+1;
            trIncome.append(tdObj);
            for(var k = 0; k < incomeRowsLength; k ++){
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = '0';
                tdObj1.setAttribute("class","tdRows10");
                tbIncome.rows[k].append(tdObj1);
            }
        }
    }


    /**
     * refresh income chart
     */
    var monthIncomeChart = echarts.init(document.getElementById('monthIncomeChart'));
    var xData = [];
    var incomeData = [];
    var depositData = [];
    var ldData = [];
    var dealerData = [];
    var salesData = [];
    function refreshData() {
        xData.length = 0;
        incomeData.length = 0;
        depositData.length = 0;
        ldData.length = 0;
        dealerData.length = 0;
        salesData.length = 0;
        var items = tbIncome.rows[0].cells.length - 2;
        for(var i = 0; i < items; i ++){
            xData.push(i + 1);
            var income = tbIncome.rows[0].cells[i+2].innerHTML;
            incomeData.push(income);
            var deposit = tbIncome.rows[1].cells[i+2].innerHTML;
            depositData.push(deposit);
            var ld = tbIncome.rows[2].cells[i+2].innerHTML;
            ldData.push(ld);
            var dealer = tbIncome.rows[3].cells[i+2].innerHTML;
            dealerData.push(dealer);
            var sales = tbIncome.rows[4].cells[i+2].innerHTML;
            salesData.push(sales);
        }
    }

    var monthIncomeChartOption = {
        title: {
            text: 'Month details',
            textStyle:{
                color: '#7c9f97'
            }
        },
        tooltip: {},
        legend: {
            data:['income', 'deposit', 'ld', 'dealer', 'sales'],
            textStyle:{
                color: 'rgba(255, 255, 255, 0.8)'
            }
        },
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        xAxis: {
            data: xData
        },
        yAxis:{},
        series: [
            {
                name: 'income',
                type: 'bar',
                data: incomeData,
                barWidth: 5,
                itemStyle: {
                    normal: {
                        color: '#f90c0c',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'deposit',
                type: 'bar',
                data: depositData,
                barWidth: 5,
                itemStyle: {
                    normal: {
                        color: '#f614da',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'ld',
                type: 'bar',
                data: ldData,
                barWidth: 5,
                itemStyle: {
                    normal: {
                        color: '#f6f30d',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'dealer',
                type: 'bar',
                data: dealerData,
                barWidth: 5,
                itemStyle: {
                    normal: {
                        color: '#8fffe7',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'sales',
                type: 'bar',
                data: salesData,
                barWidth: 5,
                itemStyle: {
                    normal: {
                        color: '#00ff00',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    refreshData();
    monthIncomeChart.setOption(monthIncomeChartOption);


    /**
     * get chart data by month
     */
    getOrdersByMonth(currentYear, currentMonth);
    function getOrdersByMonth(year, month) {
        var url = baseUrl + "/admin/orders/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $.post(url,{ },function(response, status){
            if(status !== 'success'){
                showNotice('communication error');
                return;
            }
            if(response.code !== 200){
                showNotice('response.message');
                return;
            }
            var orderLists = response.dataList;
            var daysIncomeData = transformMonthOrderLists(orderLists);
            cleanMonthIncomeTable();
            var incomeData = daysIncomeData['incomeData'];
            var depositData = daysIncomeData['depositData'];
            var ldData = daysIncomeData['ldData'];
            var dealerData = daysIncomeData['dealerData'];
            var salesData = daysIncomeData['salesData'];
            var dataLength = incomeData.length;
            var totalMonthIncome = 0;
            var totalMonthDeposit = 0;
            var totalMonthLdCommission = 0;
            var totalMonthDealerCommission = 0;
            var totalMonthSalesCommission = 0;
            for(var i = 0 ; i < dataLength ; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = i+1;
                trIncome.append(tdObj);
                var ll = tbIncome.rows.length;
                for(var j = 0; j < ll; j ++){
                    var tdObj2 = document.createElement("td");
                    var n = 0;
                    switch (j){
                        case 0:
                            n = incomeData[i];
                            totalMonthIncome += n;
                            break;
                        case 1:
                            n = depositData[i];
                            totalMonthDeposit += n;
                            break;
                        case 2:
                            n = ldData[i];
                            totalMonthLdCommission += n;
                            break;
                        case 3:
                            n = dealerData[i];
                            totalMonthDealerCommission += n;
                            break;
                        case 4:
                            n = salesData[i];
                            totalMonthSalesCommission += n;
                            break;
                    }
                    tdObj2.innerHTML = n.toFixed(2);
                    tdObj2.setAttribute('class', 'tdRows12');
                    tbIncome.rows[j].append(tdObj2);
                }
            }
            tbIncome.rows[0].cells[1].innerHTML = totalMonthIncome.toFixed(2);
            tbIncome.rows[1].cells[1].innerHTML = totalMonthDeposit.toFixed(2);
            tbIncome.rows[2].cells[1].innerHTML = totalMonthLdCommission.toFixed(2);
            tbIncome.rows[3].cells[1].innerHTML = totalMonthDealerCommission.toFixed(2);
            tbIncome.rows[4].cells[1].innerHTML = totalMonthSalesCommission.toFixed(2);
            refreshData();
            monthIncomeChart.setOption(monthIncomeChartOption);
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        })
    }

    /**
     * transform month order data
     * @param orderLists
     * @returns {{}}
     */
    function transformMonthOrderLists(orderLists){
        var daysIncomeData = {};
        var incomeData = [];
        var depositData = [];
        var ldData = [];
        var dealerData = [];
        var salesData = [];
        var days = getDaysOfYearAndMonth(currentYear, currentMonth);
        var length = orderLists.length;
        for(var i = 1; i <= days; i ++){
            var day = i < 10 ? '0' + i : i;
            var month = currentMonth < 10 ? "0" + currentMonth : currentMonth;
            var key = currentYear + '-' + month + '-' + day;
            var price = 0;
            var deposit  = 0;
            var ldCommission = 0;
            var dealerCommission = 0;
            var salesCommission = 0;
            for(var j = 0; j < length; j ++){
                var order = orderLists[j];
                if(order['tradingTime'].search(key) >= 0){
                    price += order['price'];
                    deposit += order['deposit'];
                    ldCommission += order['ldCommission'];
                    dealerCommission += order['dealerCommission'];
                    salesCommission += order['salesCommission'];
                }
            }
            incomeData.push(price);
            depositData.push(deposit);
            ldData.push(ldCommission);
            dealerData.push(dealerCommission);
            salesData.push(salesCommission);
        }
        daysIncomeData['incomeData'] = incomeData;
        daysIncomeData['depositData'] = depositData;
        daysIncomeData['ldData'] = ldData;
        daysIncomeData['dealerData'] = dealerData;
        daysIncomeData['salesData'] = salesData;
        return daysIncomeData;
    }

    /**
     * clean td of month table
     */
    function cleanMonthIncomeTable() {
        var length = tbIncome.rows[0].cells.length -2;
        for(var i = 0; i < length ; i ++){
            trIncome.removeChild(trIncome.lastChild);
            for(var k = 0; k < incomeRowsLength; k ++){
                tbIncome.rows[k].removeChild(tbIncome.rows[k].lastChild);
            }
        }
    }

    /**
     * search of table orders & search input key up event listener
     */
    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRowsOfOrdersTable();
            return;
        }
        for(var i = 0; i < rowsLength; i ++){
            for(var j = 1; j < 4; j ++){
                var content = tbOrders.rows[i].cells[j].innerHTML.toLowerCase();
                if(content.search(key) >= 0){
                    tbOrders.rows[i].style.display = '';
                    break;
                }else{
                    tbOrders.rows[i].style.display = 'none';
                }
            }
        }
        showCurrentTotalInfoOfTableOrders();
    });

    /**
     * date picker change event
     */
    $('#ipDate').change(function () {
        var key = $(this).val().length >0 ? $(this).val().substring(0, 7) : '';
        selectChangeListener(key, 10);
    });

    /**
     * select of plan change event
     */
    $('#sePlan').change(function () {
        selectChangeListener($(this).val(), 11);
    });

    /**
     * select of type change event
     */
    $('#seType').change(function () {
        selectChangeListener($(this).val(), 12);
    });

    /**
     * select change event listener
     * @param key
     * @param cellIndex
     */
    function selectChangeListener(key, cellIndex) {
        if(key.length >0){
            for(var i =0 ; i < rowsLength; i ++){
                var currentStatus = tbOrders.rows[i].style.display;
                if (tbOrders.rows[i].cells[cellIndex].innerHTML.search(key) >= 0) {
                    tbOrders.rows[i].style.display = "";
                } else {
                    tbOrders.rows[i].style.display = "none";
                }
            }
        }else{
            showAllRowsOfOrdersTable();
        }
        showCurrentTotalInfoOfTableOrders();
    }

    /**
     * show all rows of table orders
     */
    function showAllRowsOfOrdersTable(){
        for(var i =0 ; i < rowsLength; i ++){
            tbOrders.rows[i].style.display = "";
        }
    }

    /**
     * show current visible count of table orders
     */
    function showCurrentTotalInfoOfTableOrders() {
        var count = 0;
        var totalPrice = 0;
        var totalDeposit = 0;
        var totalLdCommission = 0;
        var totalDealerCommission = 0;
        var totalSalesCommission = 0;
        for(var i =0 ; i < rowsLength; i ++){
            var status = tbOrders.rows[i].style.display;
            if(status !== 'none'){
                count ++;
                totalPrice += parseFloat(tbOrders.rows[i].cells[4].innerHTML);
                totalDeposit += parseFloat(tbOrders.rows[i].cells[6].innerHTML);
                totalLdCommission += parseFloat(tbOrders.rows[i].cells[7].innerHTML);
                totalDealerCommission += parseFloat(tbOrders.rows[i].cells[8].innerHTML);
                totalSalesCommission += parseFloat(tbOrders.rows[i].cells[9].innerHTML);
            }
        }
        trTotal.cells[0].innerHTML = count;
        trTotal.cells[1].innerHTML = totalPrice.toFixed(2);
        trTotal.cells[2].innerHTML = totalDeposit.toFixed(2);
        trTotal.cells[3].innerHTML = totalLdCommission.toFixed(2);
        trTotal.cells[4].innerHTML = totalDealerCommission.toFixed(2);
        trTotal.cells[5].innerHTML = totalSalesCommission.toFixed(2);
    }

});