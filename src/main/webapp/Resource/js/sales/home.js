$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;
    setYearAndMont();
    function setYearAndMont() {
        $('#aYear').html(currentYear);
        if(currentMonth < 10){
            $('#aMonth').html("0" + currentMonth);
        }else {
            $('#aMonth').html(currentMonth);
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
            getYearCommission(currentYear);
        }
        setYearAndMont();
        getMonthCommission(currentYear, currentMonth);
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
            getYearCommission(currentYear);
        }
        setYearAndMont();
        getMonthCommission(currentYear, currentMonth);
    });

    /**
     * init month table data
     */
    var tbMonth = $('#tbMonthCommission').get(0).tBodies[0];
    var thMonth = $('#trMonthCommission').get(0);
    var rowsLength = tbMonth.rows.length;
    var currentDays = getDaysOfCurrentMonth();
    initMonthData(currentDays);
    function initMonthData(days) {
        for (var j = 0; j < days; j++) {
            var tdObj = document.createElement("td");
            tdObj.innerHTML = j+1;
            thMonth.append(tdObj);
            for(var k = 0; k < rowsLength; k ++){
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = "0";
                tbMonth.rows[k].append(tdObj1);
            }
        }
    }

    /**
     * clean td of month table 
     */
    function cleanMonthTable() {
        var length = tbMonth.rows[0].cells.length -2;
        for(var i = 0; i < length ; i ++){
            thMonth.removeChild(thMonth.lastChild);
            for(var k = 0; k < rowsLength; k ++){
                tbMonth.rows[k].removeChild(tbMonth.rows[k].lastChild);
            }
        }
    }
    

    /**
     * get month commission
     */
    getMonthCommission(currentYear, currentMonth);
    function getMonthCommission(year, month) {
        var url = baseUrl + "/sales/commission/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $.get(url,function(response, status){
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
            var monthCommissionList = response.dataList;
            cleanMonthTable();
            var days = getDaysOfYearAndMonth(year, month);
            var totalCommission = 0;
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = i+1;
                thMonth.append(tdObj);
                var tdObj2 = document.createElement("td");
                var ll = monthCommissionList.length;
                if(ll <= 0){
                    tdObj2.innerHTML = 0;
                }else{
                    for (var j = 0; j < ll; j++) {
                        var commissionInfo = monthCommissionList[j];
                        if (commissionInfo['day'] === i + 1) {
                            totalCommission += commissionInfo['commission'];
                            tdObj2.innerHTML = commissionInfo['commission'];
                            break;
                        }
                        tdObj2.innerHTML = 0;
                    }
                }
                tbMonth.rows[0].append(tdObj2);
            }
            tbMonth.rows[0].cells[1].innerHTML = totalCommission;
            refreshData();
            monthChart.setOption(monthOption);
        })
    }

    /**
     * month commission chart
     */
    var monthChart = echarts.init(document.getElementById('chartMonthCommission'));
    var xData = [];
    var commissionData = [];
    function refreshData() {
        xData.length = 0;
        commissionData.length = 0;
        var items = tbMonth.rows[0].cells.length - 2;
        for(var i = 0; i < items; i ++){
            xData.push(i + 1);
            var cd = tbMonth.rows[0].cells[i+2].innerHTML;
            commissionData.push(cd);
        }
    }

    var monthOption = {
        title: {
            text: 'Month',
            textStyle:{
                color: '#7c9f97'
            }
        },
        tooltip: {},
        legend: {
            data:['month'],
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
        yAxis: {},
        series: [
            {
                name: 'volume',
                type: 'bar',
                data: commissionData,
                itemStyle: {
                    normal: {
                        color: '#f535eb',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    refreshData();
    monthChart.setOption(monthOption);

    /**
     * year commission chart
     */
    var tbYearCommission = $('#tbYearCommission').get(0).tBodies[0];
    var yearChart = echarts.init(document.getElementById('chartYearCommission'));
    var yearCommissionData = [];
    var yearOption = {
        title: {
            text: 'Year',
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
                name: 'commission',
                type: 'line',
                data: yearCommissionData,
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
    yearChart.setOption(yearOption);
    getYearCommission(currentYear);

    function getYearCommission(year) {
        var url = baseUrl + "/sales/commission/" + year;
        $.get(url,function(response, status){
            var commissionList = response.dataList;
            var totalCommission = 0;
            yearCommissionData.length = 0;
            for(var i = 0; i < 12; i ++){
                var ll = commissionList.length;
                if(ll <= 0){
                    yearCommissionData.push(0);
                }else {
                    for (var j = 0; j < ll; j++) {
                        var commissionInfo = commissionList[j];
                        if (commissionInfo['month'] === i + 1) {
                            yearCommissionData.push(commissionInfo['commission']);
                            totalCommission += commissionInfo['commission'];
                            break;
                        } else {
                            yearCommissionData.push(0);
                        }
                    }
                }
            }
            tbYearCommission.rows[0].cells[1].innerHTML = totalCommission;
            yearChart.setOption(yearOption);
        })
    }




    var tbOrders = $('#tbOrders').get(0).tBodies[0];
    var orderRowsLength = tbOrders.rows.length;
    var trTotal = $('#trTotal').get(0);
    /**
     * search of table orders & search input key up event listener
     */
    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRowsOfOrdersTable();
            return;
        }
        for(var i = 0; i < orderRowsLength; i ++){
            for(var j = 1; j < 3; j ++){
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
        selectChangeListener(key, 6);
    });

    /**
     * select of plan change event
     */
    $('#sePlan').change(function () {
        selectChangeListener($(this).val(), 7);
    });

    /**
     * select of type change event
     */
    $('#seType').change(function () {
        selectChangeListener($(this).val(), 8);
    });

    /**
     * select change event listener
     * @param key
     * @param cellIndex
     */
    function selectChangeListener(key, cellIndex) {
        if(key.length >0){
            for(var i =0 ; i < orderRowsLength; i ++){
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
        for(var i =0 ; i < orderRowsLength; i ++){
            tbOrders.rows[i].style.display = "";
        }
    }

    /**
     * show current visible count of table orders
     */
    showCurrentTotalInfoOfTableOrders();
    function showCurrentTotalInfoOfTableOrders() {
        var count = 0;
        var totalPrice = 0;
        var totalDeposit = 0;
        var totalSalesCommission = 0;
        for(var i =0 ; i < orderRowsLength; i ++){
            var status = tbOrders.rows[i].style.display;
            if(status !== 'none'){
                count ++;
                totalPrice += parseFloat(tbOrders.rows[i].cells[3].innerHTML);
                totalDeposit += parseFloat(tbOrders.rows[i].cells[4].innerHTML);
                totalSalesCommission += parseFloat(tbOrders.rows[i].cells[5].innerHTML);
            }
        }
        trTotal.cells[0].innerHTML = count;
        trTotal.cells[1].innerHTML = totalPrice.toFixed(2);
        trTotal.cells[2].innerHTML = totalDeposit.toFixed(2);
        trTotal.cells[3].innerHTML = totalSalesCommission.toFixed(2);
    }

});