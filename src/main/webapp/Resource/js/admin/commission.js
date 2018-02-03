$(function () {

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
                    {value:tbCategory.rows[0].cells[6].innerHTML, name:'LD'},
                    {value:tbCategory.rows[0].cells[7].innerHTML, name:'LDE'},
                    {value:tbCategory.rows[0].cells[8].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[0].cells[9].innerHTML, name:'Sales'}
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
                    {value:tbCategory.rows[1].cells[6].innerHTML, name:'LD'},
                    {value:tbCategory.rows[1].cells[7].innerHTML, name:'LDE'},
                    {value:tbCategory.rows[1].cells[8].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[1].cells[9].innerHTML, name:'Sales'}
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
                    {value:tbCategory.rows[2].cells[6].innerHTML, name:'LD'},
                    {value:tbCategory.rows[2].cells[7].innerHTML, name:'LDE'},
                    {value:tbCategory.rows[2].cells[8].innerHTML, name:'Dealer'},
                    {value:tbCategory.rows[2].cells[9].innerHTML, name:'Sales'}
                ]
            }
        ]
    });


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
     * init year chart
     */
    var tbYearIncome = $('#tbYearIncome').get(0).tBodies[0];
    var yearIncomeChart = echarts.init(document.getElementById('yearIncomeChart'));
    var yearIncomeData = [];
    var yearIncomeChartOption = {
        title: {
            text: 'income',
            textStyle:{
                color: '#454545'
            }
        },
        tooltip: {},
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
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
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#f5d482',
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
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
            var yearTotalDeposit = 0;
            var yearTotalLdCommission = 0;
            var yearTotalLdeCommission = 0;
            var yearTotalDealerCommission = 0;
            var yearTotalSalesCommission = 0;
            var yearTotalSvcCharge = 0;
            var yearTotalTxFee = 0;
            var yearTotalActivation = 0;
            var yearTotalLdActivationComm = 0;
            var yearTotalLdeActivationComm = 0;
            var yearTotalDealerActivationComm = 0;
            var yearTotalSalesActivationComm = 0;
            var length = dataList.length;
            for(var i = 0; i < 12; i ++){
                var monthAmount = 0;
                var monthDeposit = 0;
                var monthLdCommission = 0;
                var monthLdeCommission = 0;
                var monthDealerCommission = 0;
                var monthSalesCommission = 0;
                var monthSvcCharge = 0;
                var monthTxFee = 0;
                var monthActivation = 0;
                var monthLdActivationComm = 0;
                var monthLdeActivationComm = 0;
                var monthDealerActivationComm = 0;
                var monthSalesActivationComm = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['date'] === i+1){
                        monthAmount = data['amount'];
                        monthDeposit = data['deposit'];
                        monthLdCommission = data['ldCommission'];
                        monthLdeCommission = data['ldeCommission'];
                        monthDealerCommission = data['dealerCommission'];
                        monthSalesCommission = data['salesCommission'];
                        monthSvcCharge = data['svcCharge'];
                        monthTxFee = data['txFee'];
                        monthActivation = data['activatePay'];
                        monthLdActivationComm = data['ldActivationComm'];
                        monthLdeActivationComm = data['ldeActivationComm'];
                        monthDealerActivationComm = data['dealerActivationComm'];
                        monthSalesActivationComm = data['salesActivationComm'];
                    }
                }
                yearIncomeData.push(monthAmount);
                yearTotalIncome += monthAmount;
                yearTotalDeposit += monthDeposit;
                yearTotalLdCommission += monthLdCommission;
                yearTotalLdeCommission += monthLdeCommission;
                yearTotalDealerCommission += monthDealerCommission;
                yearTotalSalesCommission += monthSalesCommission;
                yearTotalSvcCharge += monthSvcCharge;
                yearTotalTxFee += monthTxFee;
                yearTotalActivation += monthActivation;
                yearTotalLdActivationComm += monthLdActivationComm;
                yearTotalLdeActivationComm += monthLdeActivationComm;
                yearTotalDealerActivationComm += monthDealerActivationComm;
                yearTotalSalesActivationComm += monthSalesActivationComm;
            }
            tbYearIncome.rows[0].cells[1].innerHTML = yearTotalIncome.toFixed(2);
            tbYearIncome.rows[1].cells[1].innerHTML = yearTotalDeposit.toFixed(2);
            tbYearIncome.rows[2].cells[1].innerHTML = yearTotalLdCommission.toFixed(2);
            tbYearIncome.rows[3].cells[1].innerHTML = yearTotalLdeCommission.toFixed(2);
            tbYearIncome.rows[4].cells[1].innerHTML = yearTotalDealerCommission.toFixed(2);
            tbYearIncome.rows[5].cells[1].innerHTML = yearTotalSalesCommission.toFixed(2);
            tbYearIncome.rows[6].cells[1].innerHTML = yearTotalSvcCharge.toFixed(2);
            tbYearIncome.rows[7].cells[1].innerHTML = yearTotalTxFee.toFixed(2);
            tbYearIncome.rows[8].cells[1].innerHTML = yearTotalActivation.toFixed(2);
            tbYearIncome.rows[9].cells[1].innerHTML = yearTotalLdActivationComm.toFixed(2);
            tbYearIncome.rows[10].cells[1].innerHTML = yearTotalLdeActivationComm.toFixed(2);
            tbYearIncome.rows[11].cells[1].innerHTML = yearTotalDealerActivationComm.toFixed(2);
            tbYearIncome.rows[12].cells[1].innerHTML = yearTotalSalesActivationComm.toFixed(2);
            yearIncomeChart.setOption(yearIncomeChartOption);
        })
    }


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
     * init month income table data
     */
    var tbIncome = $('#tbMonthIncome').get(0).tBodies[0];
    var trIncome = $('#trMonthIncome').get(0);
    var incomeRowsLength = tbIncome.rows.length;
    var currentDays = getDaysOfCurrentMonth();
    initIncomeData(currentDays);
    function initIncomeData(days) {
        for (var j = 0; j < days; j++) {
            var tdObj = document.createElement("td");
            tdObj.innerHTML = (j + 1).toString();
            trIncome.append(tdObj);
            for(var k = 0; k < incomeRowsLength; k ++){
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = '0';
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
    var ldeData = [];
    var dealerData = [];
    var salesData = [];
    var svcData = [];
    var txData = [];
    var activationData = [];
    var ldAData = [];
    var ldeAData = [];
    var dealerAData = [];
    var salesAData = [];
    function refreshData() {
        xData.length = 0;
        incomeData.length = 0;
        depositData.length = 0;
        ldData.length = 0;
        ldeData.length = 0;
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
            var lde = tbIncome.rows[3].cells[i+2].innerHTML;
            ldeData.push(lde);
            var dealer = tbIncome.rows[4].cells[i+2].innerHTML;
            dealerData.push(dealer);
            var sales = tbIncome.rows[5].cells[i+2].innerHTML;
            salesData.push(sales);
            var svc = tbIncome.rows[6].cells[i+2].innerHTML;
            svcData.push(svc);
            var tx = tbIncome.rows[7].cells[i+2].innerHTML;
            txData.push(tx);
            var activation = tbIncome.rows[8].cells[i+2].innerHTML;
            activationData.push(activation);
            var ldA = tbIncome.rows[9].cells[i+2].innerHTML;
            ldAData.push(ldA);
            var ldeA = tbIncome.rows[10].cells[i+2].innerHTML;
            ldeAData.push(ldeA);
            var dealerA = tbIncome.rows[11].cells[i+2].innerHTML;
            dealerAData.push(dealerA);
            var salesA = tbIncome.rows[12].cells[i+2].innerHTML;
            salesAData.push(salesA);
        }
    }

    var monthIncomeChartOption = {
        title: {
            text: '',
            textStyle:{
                color: '#7c9f97'
            }
        },
        tooltip: {},
        legend: {
            data:['income', 'deposit', 'ld', 'dealer', 'sales'],
            textStyle:{
                color: 'rgba(0, 0, 0, 0.8)'
            }
        },
        backgroundColor: '#ffffff',
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
                barWidth: 3,
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
                barWidth: 3,
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
                barWidth: 3,
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
                barWidth: 3,
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
                barWidth: 3,
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
            var totalMonthLdeCommission = 0;
            var totalMonthDealerCommission = 0;
            var totalMonthSalesCommission = 0;
            var totalMonthSvcCharge = 0;
            var totalMonthTxFee = 0;
            var totalMonthActivatePay = 0;
            var totalMonthLdActivationComm = 0;
            var totalMonthLdeActivationComm = 0;
            var totalMonthDealerActivationComm = 0;
            var totalMonthSalesActivationComm  = 0;
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = (i+1).toString();
                trIncome.append(tdObj);
                var income = 0;
                var deposit = 0;
                var ldCommission = 0 ;
                var ldeCommission = 0 ;
                var dealerCommission = 0;
                var salesCommission = 0;
                var svcCharge = 0;
                var txFee = 0;
                var activatePay = 0;
                var ldActivationComm = 0;
                var ldeActivationComm = 0;
                var dealerActivationComm = 0;
                var salesActivationComm = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['date'] === i + 1){
                        income = data['amount'];
                        deposit = data['deposit'];
                        ldCommission = data['ldCommission'];
                        ldeCommission = data['ldeCommission'];
                        dealerCommission = data['dealerCommission'];
                        salesCommission = data['salesCommission'];
                        svcCharge = data['svcCharge'];
                        txFee = data['txFee'];
                        activatePay = data['activatePay'];
                        ldActivationComm = data['ldActivationComm'];
                        ldeActivationComm = data['ldeActivationComm'];
                        dealerActivationComm = data['dealerActivationComm'];
                        salesActivationComm = data['salesActivationComm'];
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
                            n = ldeCommission;
                            break;
                        case 4:
                            n = dealerCommission;
                            break;
                        case 5:
                            n = salesCommission;
                            break;
                        case 6:
                            n = svcCharge;
                            break;
                        case 7:
                            n = txFee;
                            break;
                        case 8:
                            n = activatePay;
                            break;
                        case 9:
                            n = ldActivationComm;
                            break;
                        case 10:
                            n = ldeActivationComm;
                            break;
                        case 11:
                            n = dealerActivationComm;
                            break;
                        case 12:
                            n = salesActivationComm;
                            break;
                        default:
                            break;
                    }
                    tdObj2.innerHTML = n.toString();
                    tbIncome.rows[k].append(tdObj2);
                }
                totalMonthIncome += income;
                totalMonthDeposit += deposit;
                totalMonthLdCommission += ldCommission;
                totalMonthLdeCommission += ldeCommission;
                totalMonthDealerCommission += dealerCommission;
                totalMonthSalesCommission += salesCommission;
                totalMonthSvcCharge += svcCharge;
                totalMonthTxFee += txFee;
                totalMonthActivatePay += activatePay;
                totalMonthLdActivationComm += ldActivationComm;
                totalMonthLdeActivationComm += ldeActivationComm;
                totalMonthDealerActivationComm += dealerActivationComm;
                totalMonthSalesActivationComm += salesActivationComm;
            }
            tbIncome.rows[0].cells[1].innerHTML = totalMonthIncome.toFixed(2);
            tbIncome.rows[1].cells[1].innerHTML = totalMonthDeposit.toFixed(2);
            tbIncome.rows[2].cells[1].innerHTML = totalMonthLdCommission.toFixed(2);
            tbIncome.rows[3].cells[1].innerHTML = totalMonthLdeCommission.toFixed(2);
            tbIncome.rows[4].cells[1].innerHTML = totalMonthDealerCommission.toFixed(2);
            tbIncome.rows[5].cells[1].innerHTML = totalMonthSalesCommission.toFixed(2);
            tbIncome.rows[6].cells[1].innerHTML = totalMonthSvcCharge.toFixed(2);
            tbIncome.rows[7].cells[1].innerHTML = totalMonthTxFee.toFixed(2);
            tbIncome.rows[8].cells[1].innerHTML = totalMonthActivatePay.toFixed(2);
            tbIncome.rows[9].cells[1].innerHTML = totalMonthLdActivationComm.toFixed(2);
            tbIncome.rows[10].cells[1].innerHTML = totalMonthLdeActivationComm.toFixed(2);
            tbIncome.rows[11].cells[1].innerHTML = totalMonthDealerActivationComm.toFixed(2);
            tbIncome.rows[12].cells[1].innerHTML = totalMonthSalesActivationComm.toFixed(2);
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

});