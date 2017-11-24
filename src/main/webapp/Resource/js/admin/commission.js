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
                    {value:tbCategory.rows[0].cells[4].innerHTML, name:'LD'},
                    {value:tbCategory.rows[0].cells[5].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[0].cells[6].innerHTML, name:'Sales'}
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
                    {value:tbCategory.rows[1].cells[4].innerHTML, name:'LD'},
                    {value:tbCategory.rows[1].cells[5].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[1].cells[6].innerHTML, name:'Sales'}
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
                    {value:tbCategory.rows[2].cells[4].innerHTML, name:'LD'},
                    {value:tbCategory.rows[2].cells[5].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[2].cells[6].innerHTML, name:'Sales'}
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
            getSalesAmountEveryMonthInYear(currentYear);
        }
        setYearAndMonth();
        getSalesAmountEveryDayInMonth(currentYear, currentMonth);
    });
    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
            getSalesAmountEveryMonthInYear(currentYear);
        }
        setYearAndMonth();
        getSalesAmountEveryDayInMonth(currentYear, currentMonth);
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
    getSalesAmountEveryMonthInYear(currentYear);
    function getSalesAmountEveryMonthInYear(year){
        var url = baseUrl + "/admin/chart/amount/"+ year;
        $.get(url, {}, function (dataList, status) {
            yearIncomeData.length = 0;
            var yearTotalIncome = 0;
            var yearTotalTxFee = 0;
            var yearTotalDeposit = 0;
            var yearTotalLdCommission = 0;
            var yearTotalDealerCommission = 0;
            var yearTotalSalesCommission = 0;
            var length = dataList.length;
            for(var i = 0; i < 12; i ++){
                var monthAmount = 0;
                var monthTxFee = 0;
                var monthDeposit = 0;
                var monthLdCommission = 0;
                var monthDealerCommission = 0;
                var monthSalesCommission = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['date'] === i+1){
                        monthAmount = data['amount'];
                        monthTxFee = data['txFee'];
                        monthDeposit = data['deposit'];
                        monthLdCommission = data['ldCommission'];
                        monthDealerCommission = data['dealerCommission'];
                        monthSalesCommission = data['salesCommission'];
                    }
                }
                yearIncomeData.push(monthAmount);
                yearTotalIncome += monthAmount;
                yearTotalTxFee += monthTxFee;
                yearTotalDeposit += monthDeposit;
                yearTotalLdCommission += monthLdCommission;
                yearTotalDealerCommission += monthDealerCommission;
                yearTotalSalesCommission += monthSalesCommission;
            }
            tbYearIncome.rows[0].cells[1].innerHTML = yearTotalIncome.toFixed(2);
            tbYearIncome.rows[1].cells[1].innerHTML = yearTotalTxFee.toFixed(2);
            tbYearIncome.rows[2].cells[1].innerHTML = yearTotalDeposit.toFixed(2);
            tbYearIncome.rows[3].cells[1].innerHTML = yearTotalLdCommission.toFixed(2);
            tbYearIncome.rows[4].cells[1].innerHTML = yearTotalDealerCommission.toFixed(2);
            tbYearIncome.rows[5].cells[1].innerHTML = yearTotalSalesCommission.toFixed(2);
            yearIncomeChart.setOption(yearIncomeChartOption);
        })
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
    getSalesAmountEveryDayInMonth(currentYear, currentMonth);
    function getSalesAmountEveryDayInMonth(year, month) {
        var url = baseUrl + "/admin/chart/amount/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $.get(url,{ },function(dataList, status){
            cleanMonthIncomeTable();
            var length = dataList.length;
            var rowLength = tbIncome.rows.length;
            var days = getDaysOfYearAndMonth(currentYear, currentMonth);
            var totalMonthIncome = 0;
            var totalMonthDeposit = 0;
            var totalMonthLdCommission = 0;
            var totalMonthDealerCommission = 0;
            var totalMonthSalesCommission = 0;
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = i+1;
                trIncome.append(tdObj);
                var income = 0;
                var deposit = 0;
                var ldCommission = 0 ;
                var dealerCommission = 0;
                var salesCommission = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['date'] === i + 1){
                        income = data['amount'];
                        deposit = data['deposit'];
                        ldCommission = data['ldCommission'];
                        dealerCommission = data['dealerCommission'];
                        salesCommission = data['salesCommission'];
                    }
                }
                for(var k = 0; k < rowLength; k ++){
                    var tdObj2 = document.createElement("td");
                    var n = 0;
                    switch (k){
                        case 0:
                            n = income;
                            break;
                        case 1:
                            n = deposit;
                            break;
                        case 2:
                            n = ldCommission;
                            break;
                        case 3:
                            n = dealerCommission;
                            break;
                        case 4:
                            n = salesCommission;
                            break;
                        default:
                            break;
                    }
                    tdObj2.innerHTML = n;
                    tdObj2.setAttribute('class', 'tdRows12');
                    tbIncome.rows[k].append(tdObj2);
                }
                totalMonthIncome += income;
                totalMonthDeposit += deposit;
                totalMonthLdCommission += ldCommission;
                totalMonthDealerCommission += dealerCommission;
                totalMonthSalesCommission += salesCommission;
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
        var totalTxFee = 0;
        var totalLdCommission = 0;
        var totalDealerCommission = 0;
        var totalSalesCommission = 0;
        for(var i =0 ; i < rowsLength; i ++){
            var status = tbOrders.rows[i].style.display;
            if(status !== 'none'){
                count ++;
                totalPrice += parseFloat(tbOrders.rows[i].cells[4].innerHTML);
                totalTxFee += parseFloat(tbOrders.rows[i].cells[5].innerHTML);
                totalDeposit += parseFloat(tbOrders.rows[i].cells[6].innerHTML);
                totalLdCommission += parseFloat(tbOrders.rows[i].cells[7].innerHTML);
                totalDealerCommission += parseFloat(tbOrders.rows[i].cells[8].innerHTML);
                totalSalesCommission += parseFloat(tbOrders.rows[i].cells[9].innerHTML);
            }
        }
        trTotal.cells[0].innerHTML = count;
        trTotal.cells[1].innerHTML = totalPrice.toFixed(2);
        trTotal.cells[2].innerHTML = totalTxFee.toFixed(2);
        trTotal.cells[3].innerHTML = totalDeposit.toFixed(2);
        trTotal.cells[4].innerHTML = totalLdCommission.toFixed(2);
        trTotal.cells[5].innerHTML = totalDealerCommission.toFixed(2);
        trTotal.cells[6].innerHTML = totalSalesCommission.toFixed(2);
    }

});