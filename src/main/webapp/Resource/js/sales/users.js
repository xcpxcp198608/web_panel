$(function () {

    var tBody = $('#tbUsers').get(0).tBodies[0];
    var tDetailsBody = $('#tbUserDetails').get(0).tBodies[0];
    var rowsLength = tBody.rows.length;

    /**
     * show current total count in table
     */
    showTotalCount();
    function showTotalCount() {
        var count = 0;
        for(var x =0 ; x < tBody.rows.length; x ++){
            var status = tBody.rows[x].style.display;
            if(status !== 'none'){
                count ++;
            }
        }
        $('#spTotalCount').html(''+count);
    }

    /**
     * show user details
     * @param key
     */
    for(var i = 0; i < rowsLength; i ++){
        tBody.rows[i].cells[8].onclick = function(){
            var key = this.parentNode.cells[1].innerHTML;
            getUserDetailInfoByKey(key)
        }
    }

    function getUserDetailInfoByKey(key) {
        var url = baseUrl + "/admin/user/" + key;
        loading.css('display', 'block');
        $.post(url,{}, function (response, status) {
            loading.css('display', 'none');
            if(status === "success") {
                tDetailsBody.rows[0].cells[1].innerHTML = response['clientKey'];
                tDetailsBody.rows[1].cells[1].innerHTML = response['mac'];
                tDetailsBody.rows[2].cells[1].innerHTML = response['category'];
                tDetailsBody.rows[3].cells[1].innerHTML = response['firstName'];
                tDetailsBody.rows[4].cells[1].innerHTML = response['lastName'];
                tDetailsBody.rows[5].cells[1].innerHTML = response['email'];
                tDetailsBody.rows[6].cells[1].innerHTML = response['phone'];
                tDetailsBody.rows[7].cells[1].innerHTML = response['time'];
                tDetailsBody.rows[8].cells[1].innerHTML = response['status'];
                tDetailsBody.rows[9].cells[1].innerHTML = response['country'];
                tDetailsBody.rows[10].cells[1].innerHTML = response['region'];
                tDetailsBody.rows[11].cells[1].innerHTML = response['city'];
                tDetailsBody.rows[12].cells[1].innerHTML = response['timeZone'];
                tDetailsBody.rows[13].cells[1].innerHTML = response['lastOnLineTime'];
                dDetails.css('display', 'block')
            }else{
                showNotice('communication error')
            }
        })
    }

    /**
     * show all rows of table
     */
    function showAllRows() {
        for(var i =0 ; i < rowsLength; i ++){
            tBody.rows[i].style.display = "";
        }
    }


    $('#btCreate').click(function () {
        window.open(baseUrl + "/sales/go", "_self")
    });

    /**
     * search event
     */
    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRows();
        }else{
            for(var k = 0; k < rowsLength; k ++){
                for(var j = 1; j < 6; j ++){
                    var content = tBody.rows[k].cells[j].innerHTML.toLowerCase();
                    if(content.search(key) >= 0){
                        tBody.rows[k].style.display = "";
                        break
                    }else{
                        tBody.rows[k].style.display = "none";
                    }
                }
            }
        }
        showTotalCount()
    });

    /**
     * select event
     */
    $('#seCategory').change(function () {
        selectChangeListener($(this).val(), 6);
    });

    $('#seStatus').change(function () {
        selectChangeListener($(this).val(), 7);
    });

    function selectChangeListener(key, cellIndex) {
        if(key.length >0){
            for(var i =0 ; i < rowsLength; i ++){
                if(tBody.rows[i].cells[cellIndex].innerHTML === key){
                    tBody.rows[i].style.display = "";
                }else{
                    tBody.rows[i].style.display = "none";
                }
            }
        }else{
            showAllRows();
        }
        showTotalCount()
    }


});