$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth() + 1;

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
        // currentMonth --;
        // if(currentMonth < 1){
        //     currentYear --;
        //     currentMonth = 12;
        // }
        currentYear --;
        setYearAndMonth();
        updateChart()
    });

    $('#btNextMonth').click(function () {
        // currentMonth ++;
        // if(currentMonth > 12){
        //     currentYear ++;
        //     currentMonth = 1;
        // }
        currentYear ++;
        setYearAndMonth();
        updateChart()
    });


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

    updateChart();
    function updateChart() {
        getLevelNumber(5, level5Data, chartLevel5, chartLevel5Option);
        getLevelNumber(4, level4Data, chartLevel4, chartLevel4Option);
        getLevelNumber(3, level3Data, chartLevel3, chartLevel3Option);
        getLevelNumber(2, level2Data, chartLevel2, chartLevel2Option);
    }

    function getLevelNumber(level, levelData, chart , chartOption) {
        var url = baseUrl + '/manager/chart/level/' + level + '/' + currentYear;
        $.get(url, {}, function (response, status) {
            if(response['code'] === 200){
                levelData.length = 0;
                var dataList = response['dataList'];
                var length = dataList.length;
            }
            for(var i = 0; i < 12; i ++){
                var count = 0;
                for(var j = 0; j < length; j ++){
                    var data = dataList[j];
                    if(data['month'] === i+1){
                        count = data['count'];
                    }
                }
                levelData.push(count);
            }
            chart.setOption(chartOption);
        });
    }

});