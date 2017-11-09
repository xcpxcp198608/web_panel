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
     * init online chart
     */
    var chartOnline = echarts.init(document.getElementById('chart_online'));
    var date = [];
    var data = [Math.random() * 150];
    function addData() {
        var now = new Date();
        var now1 = [now.getHours(), now.getMinutes(), now.getSeconds()+1].join(':');
        date.push(now1);
        data.push((Math.random() - 0.4) * 10 + data[data.length - 1]);
        if (date.length > 720) {
            date.shift();
            data.shift();
        }
    }

    var onlineOption = {
        title:{
            text: 'The number of real time online',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.8)'
            }
        },
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        itemStyle: {
            normal: {
                color: '#0ff'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            backgroundColor: 'rgba(245, 245, 245, 0.8)',
            borderWidth: 1,
            borderColor: '#ccc',
            padding: 10,
            textStyle: {
                color: '#000'
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: date
        },
        yAxis: {
            boundaryGap: [0, '10%'],
            type: 'value'
        },
        series: [
            {
                name: 'online',
                type: 'line',
                smooth: true,
                symbol: 'none',
                stack: 'a',
                areaStyle: {
                    normal: {}
                },
                data: data
            }
        ]
    };
    addData();
    chartOnline.setOption(onlineOption);
    setInterval(function () {
        addData();
        chartOnline.setOption({
            xAxis: {
                data: date
            },
            series: [{
                name:'online',
                data: data
            }]
        });
    }, 5000);


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
        getMonthOrders(currentYear, currentMonth);
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMont();
        getMonthOrders(currentYear, currentMonth);
    });

    /**
     * init month table data
     */
    var tbMonth = $('#tb_month').get(0).tBodies[0];
    var thMonth = $('#trHead').get(0);
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
     * get month orders
     */
    getMonthOrders(currentYear, currentMonth);
    function getMonthOrders(year, month) {
        var days = getDaysOfYearAndMonth(year, month);
        var url = baseUrl + "/admin/commission/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $('#homeLoading').css('display', 'block');
        $.post(url,{ },function(response, status){
            var monthOrderList = response.dataList;
            var monthData = transformMonthOrders(monthOrderList);
            var volumeData = monthData['volumeData'];
            var b1Data = monthData['b1Data'];
            var p1Data = monthData['p1Data'];
            var p2Data = monthData['p2Data'];
            cleanMonthTable();
            var totalVolume = 0;
            var totalB1 = 0;
            var totalP1 = 0;
            var totalP2 = 0;
            var length = volumeData.length;
            for(var i = 0 ; i < length ; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = i+1;
                thMonth.append(tdObj);
                var ll = tbMonth.rows.length;
                for(var j = 0; j < ll; j ++){
                    var tdObj2 = document.createElement("td");
                    var n = 0;
                    switch (j){
                        case 0:
                            n = volumeData[i];
                            totalVolume += n;
                            break;
                        case 1:
                            n = b1Data[i];
                            totalB1 += n;
                            break;
                        case 2:
                            n = p1Data[i];
                            totalP1 += n;
                            break;
                        case 3:
                            n = p2Data[i];
                            totalP2 += n;
                            break;
                    }
                    tdObj2.innerHTML = n;
                    tdObj2.setAttribute('class', 'tdRows12');
                    tbMonth.rows[j].append(tdObj2);
                }
            }
            tbMonth.rows[0].cells[1].innerHTML = totalVolume;
            tbMonth.rows[1].cells[1].innerHTML = totalB1;
            tbMonth.rows[2].cells[1].innerHTML = totalP1;
            tbMonth.rows[3].cells[1].innerHTML = totalP2;
            refreshData();
            monthChart.setOption(monthOption);
            $('#homeLoading').css('display', 'none');
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        })
    }

    /**
     * transform month orders
     * @param monthOrderList
     * @returns {{}}
     */
    function transformMonthOrders(monthOrderList) {
        var monthData = {};
        var volumeData = [];
        var b1Data = [];
        var p1Data = [];
        var p2Data = [];
        var days = getDaysOfYearAndMonth(currentYear, currentMonth) + 1;
        for(var i = 1; i < days; i ++){
            var b1 = 0 ;
            var p1 = 0 ;
            var p2 = 0 ;
            var length = monthOrderList.length;
            var month = currentMonth < 10 ? '0' + currentMonth : currentMonth;
            var day = i < 10 ? '0' + i : i;
            var key = currentYear + '-' + month + "-" + day;
            for(var j = 0; j < length; j ++){
                var order = monthOrderList[j];
                if(order['tradingTime'].search(key) >= 0) {
                    if ('B1' === order['category']) {
                        b1++;
                    } else if ('P1' === order['category']) {
                        p1++;
                    } else if ('P2' === order['category']) {
                        p2++;
                    }
                }
            }
            volumeData.push(b1 + p1 + p2);
            b1Data.push(b1);
            p1Data.push(p1);
            p2Data.push(p2);
        }
        monthData['volumeData'] = volumeData;
        monthData['b1Data'] = b1Data;
        monthData['p1Data'] = p1Data;
        monthData['p2Data'] = p2Data;
        console.log(monthData);
        return monthData;
    }

    /**
     * month chart
     */
    var monthChart = echarts.init(document.getElementById('chart_month'));
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
        var items = tbMonth.rows[0].cells.length - 2;
        for(var i = 0; i < items; i ++){
            xData.push(i + 1);
            var vd = tbMonth.rows[0].cells[i+2].innerHTML;
            volumeData.push(vd);
            var b1 = tbMonth.rows[1].cells[i+2].innerHTML;
            b1Data.push(b1);
            var p1 = tbMonth.rows[2].cells[i+2].innerHTML;
            p1Data.push(p1);
            var p2 = tbMonth.rows[3].cells[i+2].innerHTML;
            p2Data.push(p2);
        }
    }

    var monthOption = {
        title: {
            text: 'sales',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.0)'
            }
        },
        tooltip: {},
        legend: {
            data:['volume', 'B1', 'P1', 'P2'],
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
                data: volumeData,
                itemStyle: {
                    normal: {
                        color: '#F5C334',
                        shadowBlur: 200,
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
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
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
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#9efcaa',
                        shadowBlur: 200,
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
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#1baefc',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    refreshData();
    monthChart.setOption(monthOption);

});