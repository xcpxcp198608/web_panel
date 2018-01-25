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
    var now1 = [now.getHours(), now.getMinutes(), now.getSeconds()+1].join(':');
    var date = [now1];
    var data = [0];
    function addData() {
        var url = baseUrl + "/admin/chart/online";
        $.get(url,function (number, status) {
            $('#sOnline').html(number);
            var now = new Date();
            var now1 = [now.getHours(), now.getMinutes(), now.getSeconds()+1].join(':');
            date.push(now1);
            data.push(number);
            if (date.length > 720) {
                date.shift();
                data.shift();
            }
        });

    }

    var onlineOption = {
        title:{
            textStyle:{
                color: 'rgba(0, 0, 0, 0.8)'
            }
        },
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
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
            borderColor: '#333',
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
        getSaleVolumeEveryDayInMonth(currentYear, currentMonth);
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMont();
        getSaleVolumeEveryDayInMonth(currentYear, currentMonth);
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
            tdObj.innerHTML = (j + 1).toString();
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
     * get month sales volume
     */
    getSaleVolumeEveryDayInMonth(currentYear, currentMonth);
    function getSaleVolumeEveryDayInMonth(year, month) {
        var days = getDaysOfYearAndMonth(year, month);
        var url = baseUrl + "/admin/chart/volume/" + year + "/" + month;
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        $.get(url,{ },function(dayVolumeList, status){
            var length = dayVolumeList.length;
            var rowsLength = tbMonth.rows.length;
            var totalVolume = 0;
            var totalB1 = 0;
            var totalP1 = 0;
            var totalP2 = 0;
            cleanMonthTable();
            for(var i = 0; i < days; i ++){
                var tdObj = document.createElement("td");
                tdObj.innerHTML = i+1;
                thMonth.append(tdObj);
                var b1 = 0;
                var p1 = 0;
                var p2 = 0;
                for(var j = 0; j < length; j ++){
                    var item = dayVolumeList[j];
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
                    tdObj2.innerHTML = n;
                    tdObj2.setAttribute('class', 'tdRows12');
                    tbMonth.rows[k].append(tdObj2);
                }
                totalB1 += b1;
                totalP1 += p1;
                totalP2 += p2;
                totalVolume += b1+p1+p2;
            }
            tbMonth.rows[0].cells[1].innerHTML = totalVolume.toString();
            tbMonth.rows[1].cells[1].innerHTML = totalB1.toString();
            tbMonth.rows[2].cells[1].innerHTML = totalP1.toString();
            tbMonth.rows[3].cells[1].innerHTML = totalP2.toString();
            refreshData();
            monthChart.setOption(monthOption);
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        })
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
            text: '',
            textStyle:{
                color: 'rgba(0, 0, 0, 0.8)'
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
                itemStyle: {
                    normal: {
                        color: '#F5C334',
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
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
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
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
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#9efcaa',
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
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
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#1baefc',
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    refreshData();
    monthChart.setOption(monthOption);

});