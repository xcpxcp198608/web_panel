$(function () {

    var chartDistribution = echarts.init(document.getElementById('chart_distribution'));
    var legendData=['volume'];
    var legendColor=['#05637a'];
    var visColor=["#ff0000", "#e8273b", "#d64830", "#b9621e", "#f09252", "#d3cf22"];
    var distributionData = [];
    var seriesData=[
        {
            name: 'volume',
            type: 'map',
            mapType: 'world',
            roam: true,
            textStyle:{
                color: '#7c9f97'
            },
            itemStyle:{
                normal: {
                    color: '#42eff5',
                    shadowBlur: 200,
                    shadowColor: 'rgba(0, 0, 0, 0.7)'
                },
                emphasis:{
                    label:{show:true}
                }
            },
            data: distributionData
        }
    ];


    var distributionOption = {
        backgroundColor: '#2c343c',
        textStyle: {
            color: '#dcdcdc'
        },
        title: {
            text: 'sales volume distribution',
            left: 'left',
            textStyle:{
                color: '#7c9f97'
            }
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            textStyle:{
                color: '#7c9f97'
            },
            right: '3%',
            bottom:'3%',
            data:legendData
        },
        color:legendColor,
        visualMap: {
            min: 0,
            max: 100000,
            left: 'left',
            bottom: '3%',
            text: ['high','low'],
            calculable: true,
            color:visColor,
            textStyle:{
                color: '#7c9f97'
            }
        },
        toolbox: {
            show: true,
            right: '3%',
            feature: {
                // dataView: {readOnly: false},
                restore: {
                    title: 'restore'
                },
                saveAsImage: {
                    title: 'save as image'
                }
            }
        },
        series: seriesData
    };

    chartDistribution.setOption(distributionOption);

    getDistributionData();
    function getDistributionData() {
        var url = baseUrl + '/admin/chart/distribution';
        $.get(url, function (data) {
            console.log(data);
            distributionData.length = 0;
            var l = data.length;
            for(var i = 0; i < l; i ++){
                distributionData.push(data[i])
            }
            chartDistribution.setOption(distributionOption);
        })
    }

});