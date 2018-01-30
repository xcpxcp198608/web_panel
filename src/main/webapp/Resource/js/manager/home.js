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


    var tbMonthVolume = $('#tbMonthVolume').get(0).tBodies[0];
    var thMonthVolume = $('#tbMonthVolume').get(0).tHead;
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
            text: '',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
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
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.7)'
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#68d4fc',
                        shadowBlur: 10,
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
     * set button event
     */
    $('#btPreviousMonth').click(function () {
        currentMonth --;
        if(currentMonth < 1){
            currentYear --;
            currentMonth = 12;
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
            getYearVolume(currentYear);
        }
        getMonthVolume(currentYear, currentMonth);
        setYearAndMonth();
    });


    var tbYearVolume = $('#tbYearVolume').get(0).tBodies[0];
    /**
     * year volume chart
     */
    var chartYearVolume = echarts.init(document.getElementById('chartYearVolume'));
    var yearXData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    var yearVolume = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var chartYearVolumeOption = {
        title: {
            text: '',
            textStyle:{
                color: 'rgba(255, 255, 255, 0.9)'
            }
        },
        tooltip: {},
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgba(0, 0, 0, 0.8)'
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
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color: '#fc3ba8',
                        shadowBlur: 50,
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


});