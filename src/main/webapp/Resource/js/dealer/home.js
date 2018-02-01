$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;
    var currentDays = getDaysOfCurrentMonth();

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
        }
        setYearAndMont();
        getDealerVolumeEveryDayInMonth(currentYear, currentMonth);
        getDealerCommissionEveryDayInMonth(currentYear, currentMonth);
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMont();
        getDealerVolumeEveryDayInMonth(currentYear, currentMonth);
        getDealerCommissionEveryDayInMonth(currentYear, currentMonth);
    });


    /**
     * init commission table data
     */
    var tbCommission = $('#tbCommission').get(0).tBodies[0];
    var thCommission = $('#thCommission').get(0);
    var rowsLengthC = tbCommission.rows.length;
    initCommissionMonthData(currentDays);
    function initCommissionMonthData(days) {
        for (var j = 0; j < days; j++) {
            var tdObj = document.createElement("td");
            tdObj.innerHTML = (j + 1).toString();
            thCommission.append(tdObj);
            for(var k = 0; k < rowsLengthC; k ++){
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = "0";
                tbCommission.rows[k].append(tdObj1);
            }
        }
    }

    /**
     * clean commission table
     */
    function cleanMonthCommissionTable() {
        var length = tbCommission.rows[0].cells.length -2;
        for(var i = 0; i < length ; i ++){
            thCommission.removeChild(thCommission.lastChild);
            for(var k = 0; k < rowsLengthC; k ++){
                tbCommission.rows[k].removeChild(tbCommission.rows[k].lastChild);
            }
        }
    }

    /**
     * get dealer month commission
     */
    getDealerCommissionEveryDayInMonth(currentYear, currentMonth);
    function getDealerCommissionEveryDayInMonth(year, month) {
        var days = getDaysOfYearAndMonth(year, month);
        var url = baseUrl + "/dealer/chart/commission/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $('#homeLoading').css('display', 'block');
        $.get(url,{ },function(list, status){
            console.log(list);
            var length = list.length;
            var totalCommission = 0;
            cleanMonthCommissionTable();
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = (i+1).toString();
                thCommission.append(tdObj);
                var commission = 0;
                for(var j = 0; j < length; j ++){
                    var item = list[j];
                    if(item['day'] === i + 1){
                        commission = item['commission'];
                    }
                }
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = commission.toString();
                tbCommission.rows[0].append(tdObj1);
                totalCommission += commission;
            }
            tbCommission.rows[0].cells[1].innerHTML = totalCommission.toString();
            $('#currentMonthCommission').html(totalCommission);
            refreshCommissionData();
            chartCommission.setOption(chartCommissionOption);
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        })
    }

    /**
     * dealer month commission chart
     */
    var chartCommission = echarts.init(document.getElementById('chartCommission'));
    var xCommissionData = [];
    var commissionData = [];
    function refreshCommissionData() {
        xCommissionData.length = 0;
        commissionData.length = 0;
        var items = tbCommission.rows[0].cells.length - 2;
        for(var i = 0; i < items; i ++){
            xCommissionData.push(i + 1);
            var vd = tbCommission.rows[0].cells[i+2].innerHTML;
            commissionData.push(vd);
        }
    }

    var chartCommissionOption = {
        title: {
            text: '',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.0)'
            }
        },
        tooltip: {},
        legend: {
            data:['commission'],
            textStyle:{
                color: 'rgba(0, 0, 0, 0.8)'
            }
        },
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
        },
        xAxis: {
            data: xCommissionData
        },
        yAxis: {},
        series: [
            {
                name: 'commission',
                type: 'bar',
                data: commissionData,
                itemStyle: {
                    normal: {
                        color: '#f54be2',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    refreshCommissionData();
    chartCommission.setOption(chartCommissionOption);







    /**
     * init volume table data
     */
    var tbVolume = $('#tbVolume').get(0).tBodies[0];
    var thVolume = $('#thVolume').get(0);
    var rowsLength = tbVolume.rows.length;
    initMonthData(currentDays);
    function initMonthData(days) {
        for (var j = 0; j < days; j++) {
            var tdObj = document.createElement("td");
            tdObj.innerHTML = (j+1).toString();
            thVolume.append(tdObj);
            for(var k = 0; k < rowsLength; k ++){
                var tdObj1 = document.createElement("td");
                tdObj1.innerHTML = "0";
                tbVolume.rows[k].append(tdObj1);
            }
        }
    }

    /**
     * clean volume table
     */
    function cleanMonthTable() {
        var length = tbVolume.rows[0].cells.length -2;
        for(var i = 0; i < length ; i ++){
            thVolume.removeChild(thVolume.lastChild);
            for(var k = 0; k < rowsLength; k ++){
                tbVolume.rows[k].removeChild(tbVolume.rows[k].lastChild);
            }
        }
    }
    

    /**
     * get dealer month volume
     */
    getDealerVolumeEveryDayInMonth(currentYear, currentMonth);
    function getDealerVolumeEveryDayInMonth(year, month) {
        var days = getDaysOfYearAndMonth(year, month);
        var url = baseUrl + "/dealer/chart/volume/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $('#homeLoading').css('display', 'block');
        $.get(url,{ },function(list, status){
            var length = list.length;
            var rowsLength = tbVolume.rows.length;
            var totalVolume = 0;
            var totalB1 = 0;
            var totalP1 = 0;
            var totalP2 = 0;
            cleanMonthTable();
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = (i+1).toString();
                thVolume.append(tdObj);
                var b1 = 0;
                var p1 = 0;
                var p2 = 0;
                for(var j = 0; j < length; j ++){
                    var item = list[j];
                    if(item['day'] === i+1){
                        if(item['category'] === 'B1'){
                            b1 = item['count']
                        }
                        if(item['category'] === 'P1'){
                            p1 = item['count']
                        }
                        if(item['category'] === 'P2'){
                            p2 = item['count']
                        }
                    }
                }
                for(var k = 0; k < rowsLength; k ++){
                    var tdObj2 = document.createElement("td");
                    var n = 0;
                    switch (k){
                        case 0:
                            n = b1 + p1 + p2;
                            break;
                        case 1:
                            n = b1;
                            break;
                        case 2:
                            n = p1;
                            break;
                        case 3:
                            n = p2;
                            break;
                    }
                    tdObj2.innerHTML = n.toString();
                    tbVolume.rows[k].append(tdObj2);
                }
                totalB1 += b1;
                totalP1 += p1;
                totalP2 += p2;
                totalVolume += b1+p1+p2;
            }
            tbVolume.rows[0].cells[1].innerHTML = totalVolume.toString();
            tbVolume.rows[1].cells[1].innerHTML = totalB1.toString();
            tbVolume.rows[2].cells[1].innerHTML = totalP1.toString();
            tbVolume.rows[3].cells[1].innerHTML = totalP2.toString();
            $('#currentMonthVolume').html(totalVolume);
            refreshData();
            chartVolume.setOption(chartVolumeOption);
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        })
    }

    /**
     * dealer month volume chart
     */
    var chartVolume = echarts.init(document.getElementById('chartVolume'));
    var xData = [];
    var volumeData = [];
    var b1Data = [];
    var p1Data = [];
    var p2Data = [];
    function refreshData() {
        xData.length = 0;
        volumeData.length = 0;
        b1Data.length = 0;
        p1Data.length = 0;
        p2Data.length = 0;
        var items = tbVolume.rows[0].cells.length - 2;
        for(var i = 0; i < items; i ++){
            xData.push(i + 1);
            var vd = tbVolume.rows[0].cells[i+2].innerHTML;
            volumeData.push(vd);
            var b1 = tbVolume.rows[1].cells[i+2].innerHTML;
            b1Data.push(b1);
            var p1 = tbVolume.rows[2].cells[i+2].innerHTML;
            p1Data.push(p1);
            var p2 = tbVolume.rows[3].cells[i+2].innerHTML;
            p2Data.push(p2);
        }
    }

    var chartVolumeOption = {
        title: {
            text: '',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.0)'
            }
        },
        tooltip: {},
        legend: {
            data:['volume', 'B1', 'P1', 'P2'],
            textStyle:{
                color: 'rgba(0, 0, 0, 0.8)'
            }
        },
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
        },
        xAxis: {
            data: xData
        },
        yAxis: {},
        series: [
            {
                name: 'volume',
                type: 'bar',
                data: volumeData,
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color: '#F5C334',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'B1',
                type: 'line',
                data: b1Data,
                itemStyle: {
                    normal: {
                        color: '#f56107',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'P1',
                type: 'line',
                data: p1Data,
                itemStyle: {
                    normal: {
                        color: '#12f589',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#9efcaa',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            },
            {
                name: 'P2',
                type: 'line',
                data: p2Data,
                itemStyle: {
                    normal: {
                        color: '#320ef5',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#1baefc',
                        shadowBlur: 50,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    refreshData();
    chartVolume.setOption(chartVolumeOption);

});