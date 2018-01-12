$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;

    var tbYearVolume = $('#tbYearVolume').get(0).tBodies[0];
    var tbMonthVolume = $('#tbMonthVolume').get(0).tBodies[0];
    var thMonthVolume = $('#tbMonthVolume').get(0).tHead;

    /**
     * init data and chart
     */
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
     * set button event
     */
    $('#btPreviousMonth').click(function () {
        currentMonth --;
        if(currentMonth < 1){
            currentYear --;
            currentMonth = 12;
            updateLevelChart();
            getYearVolume(currentYear);
        }
        getMonthVolume(currentYear, currentMonth);
        setYearAndMonth();
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
            updateLevelChart();
            getYearVolume(currentYear);
        }
        getMonthVolume(currentYear, currentMonth);
        setYearAndMonth();
    });

    /**
     * year volume chart
     */
    var chartYearVolume = echarts.init(document.getElementById('chartYearVolume'));
    var yearXData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    var yearVolume = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var chartYearVolumeOption = {
        title: {
            text: 'Volume of month',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        xAxis: {
            data: yearXData
        },
        yAxis: {},
        series: [
            {
                name: 'volume',
                type: 'bar',
                data: yearVolume,
                barWidth: 30,
                itemStyle: {
                    normal: {
                        color: '#fc3ba8',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartYearVolume.setOption(chartYearVolumeOption);
    getYearVolume(currentYear);
    function getYearVolume(year) {
        var url = baseUrl + '/manager/chart/volume/' + year + '/0';
        $.get(url, {}, function (response) {
            if(response['code'] === 200){
                yearVolume.length = 0;
                var dataList = response['dataList'];
                var length = dataList.length;
            }
            var totalVolume = 0;
            for(var i = 0; i < 12; i ++){
                var volume = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['month'] === i+1){
                        volume = data['volume'];
                    }
                }
                totalVolume += volume;
                yearVolume.push(volume);
                tbYearVolume.rows[0].cells[i+1].innerHTML = volume.toString();
            }
            tbYearVolume.rows[0].cells[0].innerHTML = totalVolume.toString();
            chartYearVolume.setOption(chartYearVolumeOption);
        });
    }


    /**
     * month volume data
     */
    var chartMonthVolume = echarts.init(document.getElementById('chartMonthVolume'));
    var monthXData = [];
    var monthVolume = [];
    initMonthData();
    function initMonthData() {
        var l = getDaysOfYearAndMonth(currentYear, currentMonth);
        monthXData.length = 0;
        for(var i = 0; i < l; i ++){
            monthXData.push(i + 1);
            monthVolume.push(0);
        }
    }
    var chartMonthVolumeOption = {
        title: {
            text: 'Volume of day',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        xAxis: {
            data: monthXData
        },
        yAxis: {},
        series: [
            {
                name: 'volume',
                type: 'line',
                data: monthVolume,
                itemStyle: {
                    normal: {
                        color: '#83fc1a',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#68d4fc',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartMonthVolume.setOption(chartMonthVolumeOption);
    getMonthVolume(currentYear, currentMonth);
    function getMonthVolume(year, month) {
        initMonthData();
        $('#btPreviousMonth').attr('disabled', 'disabled');
        $('#btNextMonth').attr('disabled', 'disabled');
        var url = baseUrl + '/manager/chart/volume/' + year + '/' + month;
        $.get(url, {}, function (response) {
            if(response['code'] === 200) {
                monthVolume.length = 0;
                var dataList = response['dataList'];
                var length = dataList.length;

                cleanMonthVolumeTable();
                var l = getDaysOfYearAndMonth(currentYear, currentMonth);
                var totalVolume = 0;
                for (var i = 0; i < l; i++) {
                    var volume = 0;
                    for (var j = 0; j < length; j++) {
                        var data = dataList[j];
                        if (data['day'] === i + 1) {
                            volume = data['volume'];
                        }
                    }
                    totalVolume += volume;
                    monthVolume.push(volume);

                    var th = document.createElement("th");
                    th.innerHTML = (i + 1).toString();
                    thMonthVolume.rows[0].appendChild(th);

                    var td = document.createElement("td");
                    td.innerHTML = volume.toString();
                    tbMonthVolume.rows[0].appendChild(td);
                }
                tbMonthVolume.rows[0].cells[0].innerHTML = totalVolume.toString();
                chartMonthVolume.setOption(chartMonthVolumeOption);
            }
            $('#btPreviousMonth').removeAttr('disabled');
            $('#btNextMonth').removeAttr('disabled');
        });
    }
    
    function cleanMonthVolumeTable() {
        var cells = tbMonthVolume.rows[0].cells.length -1;
        for(var i = 0; i < cells; i ++){
            tbMonthVolume.rows[0].removeChild(tbMonthVolume.rows[0].lastChild);
            thMonthVolume.rows[0].removeChild(thMonthVolume.rows[0].lastChild);
        }
    }



    /**
     * level chart
     */
    var chartLevel5 = echarts.init(document.getElementById('chartLevel5'));
    var chartLevel4 = echarts.init(document.getElementById('chartLevel4'));
    var chartLevel3 = echarts.init(document.getElementById('chartLevel3'));
    var chartLevel2 = echarts.init(document.getElementById('chartLevel2'));
    var xData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    var level5Data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var level4Data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var level3Data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var level2Data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

    var chartLevel5Option = {
        title: {
            text: 'Level5',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
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
                name: 'number',
                type: 'line',
                data: level5Data,
                itemStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#f0d455',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartLevel5.setOption(chartLevel5Option);

    var chartLevel4Option = {
        title: {
            text: 'Level4',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
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
                name: 'number',
                type: 'line',
                data: level4Data,
                itemStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#f0d455',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartLevel4.setOption(chartLevel4Option);

    var chartLevel3Option = {
        title: {
            text: 'Level3',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
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
                name: 'number',
                type: 'line',
                data: level3Data,
                itemStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#f0d455',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartLevel3.setOption(chartLevel3Option);

    var chartLevel2Option = {
        title: {
            text: 'Level2',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
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
                name: 'number',
                type: 'line',
                data: level2Data,
                itemStyle: {
                    normal: {
                        color: '#fc0a5b',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#f0d455',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                }
            }
        ]
    };
    chartLevel2.setOption(chartLevel2Option);

    function getLevelNumber(level, levelData, chart , chartOption) {
        var url = baseUrl + '/manager/chart/level/' + level + '/' + currentYear;
        $.get(url, {}, function (response) {
            if(response['code'] === 200){
                levelData.length = 0;
                var dataList = response['dataList'];
                var length = dataList.length;
            }
            var tempLevelData = [];
            for(var i = 0; i < 12; i ++){
                var count = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['month'] === i+1){
                        count = data['count'];
                    }
                }
                tempLevelData.push(count);
            }
            console.log(tempLevelData);
            for(var j = 0; j < 12; j ++){
                var temp = 0;
                for(var k = 11; k >= j; k --){
                    temp += tempLevelData[k]
                }
                levelData.push(temp)
                switch (level){
                    case 5:
                        var tbLevel5 = $('#tbLevel5').get(0).tBodies[0];
                        tbLevel5.rows[0].cells[j].innerHTML = temp.toString();
                        break;
                    case 4:
                        var tbLevel4 = $('#tbLevel4').get(0).tBodies[0];
                        tbLevel4.rows[0].cells[j].innerHTML = temp.toString();
                        break;
                    case 3:
                        var tbLevel3 = $('#tbLevel3').get(0).tBodies[0];
                        tbLevel3.rows[0].cells[j].innerHTML = temp.toString();
                        break;
                    case 2:
                        var tbLevel2 = $('#tbLevel2').get(0).tBodies[0];
                        tbLevel2.rows[0].cells[j].innerHTML = temp.toString();
                        break;
                    default:
                        break;
                }
            }
            chart.setOption(chartOption);
        });
    }

    updateLevelChart();
    function updateLevelChart() {
        getLevelNumber(5, level5Data, chartLevel5, chartLevel5Option);
        getLevelNumber(4, level4Data, chartLevel4, chartLevel4Option);
        getLevelNumber(3, level3Data, chartLevel3, chartLevel3Option);
        getLevelNumber(2, level2Data, chartLevel2, chartLevel2Option);
    }



});