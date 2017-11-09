$(function () {

    var chartSalesVolume = echarts.init(document.getElementById('chart_sales_volume'));
    var salesVolumeOption = {
        title: {
            text: 'Top5 of sales volume',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.8)'
            }
        },
        legend: {
            data:['sales amount'],
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
                color: function(params) {
                    var colorList = [
                        '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B'
                    ];
                    return colorList[params.dataIndex]
                },
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.7)'
            }
        },
        tooltip: {},
        xAxis: {
            data: ["s1","s2","s3","s4","s5"]
        },
        yAxis: {},
        series: [{
            name: 'sales volume',
            type: 'bar',
            barWidth: 30,
            data: [15, 20, 36, 10, 10]
        }]
    };
    chartSalesVolume.setOption(salesVolumeOption);


    var chartSalesAmount = echarts.init(document.getElementById('chart_sales_amount'));
    var salesAmountOption = {
        title: {
            text: 'Top5 of sales amount',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.8)'
            }
        },
        legend: {
            data:['sales amount']
        },
        backgroundColor: '#2c343c',
        textStyle: {
            color: 'rgba(255, 255, 255, 0.8)'
        },
        itemStyle: {
            normal: {
                color: function(params) {
                    var colorList = [
                        '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B'
                    ];
                    return colorList[params.dataIndex]
                },
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        },
        tooltip: {},
        xAxis: {
            data: ["s1","s2","s3","s4","s5"]
        },
        yAxis: {},
        series: [{
            name: 'sales volume',
            type: 'bar',
            barWidth: 30,
            data: [15, 20, 36, 10, 10]
        }]
    };
    chartSalesAmount.setOption(salesAmountOption);

});