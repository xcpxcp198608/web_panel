$(function () {

    var now = new Date();
    var currentYear = now.getFullYear();
    var currentMonth = now.getMonth();
    if(currentMonth < 1){
        currentMonth = 12;
        currentYear --;
    }
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
        }
        setYearAndMonth();
        getCurrentMonthActivateUser();
    });

    $('#btNextMonth').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        setYearAndMonth();
        getCurrentMonthActivateUser();
    });


    var tbUsers = $('#tbUsers').get(0).tBodies[0];
    getCurrentMonthActivateUser();
    function getCurrentMonthActivateUser() {
        var url = baseUrl + '/mexico/customers/' + currentYear + '/' + currentMonth;
        $.post(url, {}, function (dataList) {
            cleanUserTable();
            var l = dataList.length;
            for(var i = 0; i < l; i ++){
                var data = dataList[i];
                var tr = document.createElement("tr");
                var td1 = document.createElement("td");
                td1.innerHTML = (i + 1).toString();
                var td2 = document.createElement("td");
                td2.innerHTML = data['clientKey'];
                var td3 = document.createElement("td");
                td3.innerHTML = data['mac'];
                var td4 = document.createElement("td");
                td4.innerHTML = data['firstName'] + " " + data['lastName'];
                var td5 = document.createElement("td");
                td5.innerHTML = data['activateTime'];
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tbUsers.appendChild(tr);
            }
            showTotalData();
        })
    }
    
    function cleanUserTable() {
        var length = tbUsers.rows.length;
        for(var i = 0; i < length; i ++){
            tbUsers.removeChild(tbUsers.lastChild);
        }
    }

    showTotalData();
    function showTotalData() {
        var totalCount = tbUsers.rows.length;
        var l = $('#ldC').val();
        var ldm = $('#ldmC').val();
        var dealer = $('#dealerC').val();
        var ldComm = l * totalCount;
        var ldmComm = ldm * totalCount;
        var dealerComm = dealer * totalCount;
        $('#totalCount').html(totalCount);
        $('#totalLdCommission').html(ldComm.toFixed(2));
        $('#totalLdmCommission').html(ldmComm.toFixed(2));
        $('#totalDealersCommission').html(dealerComm.toFixed(2));
    }

});