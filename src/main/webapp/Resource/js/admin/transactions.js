$(function () {

    var tbOrders = $('#tbOrders').get(0).tBodies[0];
    var rowsLength = tbOrders.rows.length;

    var currentSearchIndex = 0;
    $('input[name=searchRadio]').each(function(){
        $(this).click(function(){
            currentSearchIndex = parseInt($(this).val());
        });
    });

    /**
     * search of table orders & search input key up event listener
     */
    $('#ipSearch').keyup(function () {
        var key = $(this).val().toLowerCase();
        if(key.length <= 0){
            showAllRowsOfOrdersTable();
            return;
        }
        var count = 0;
        if(currentSearchIndex > 0) {
            for (var m = 0; m < rowsLength; m ++) {
                var content1 = tbOrders.rows[m].cells[currentSearchIndex].innerHTML.toLowerCase();
                if (content1.search(key) >= 0) {
                    tbOrders.rows[m].style.display = "";
                    count++;
                } else {
                    tbOrders.rows[m].style.display = "none";
                }
            }
        }else{
            for (var i = 0; i < rowsLength; i++) {
                for (var j = 1; j < 8; j++) {
                    var content = tbOrders.rows[i].cells[j].innerHTML.toLowerCase();
                    if (content.search(key) >= 0) {
                        tbOrders.rows[i].style.display = '';
                        count++;
                        break;
                    } else {
                        tbOrders.rows[i].style.display = 'none';
                    }
                }
            }
        }
        $('#totalRecorders').html(count.toString());
        showCurrentTotalInfoOfTableOrders();
    });

    /**
     * show all rows of table orders
     */
    function showAllRowsOfOrdersTable(){
        for(var i =0 ; i < rowsLength; i ++){
            tbOrders.rows[i].style.display = "";
        }
    }

    showCurrentTotalInfoOfTableOrders();

    /**
     * show current visible count of table orders
     */
    function showCurrentTotalInfoOfTableOrders() {
        var count = 0;
        var totalAmount = 0;
        var totalDeposit = 0;
        var totalTxFee = 0;
        var totalLdCommission = 0;
        var totalLdeCommission = 0;
        var totalDealerCommission = 0;
        var totalSalesCommission = 0;
        for(var i =0 ; i < rowsLength; i ++){
            var status = tbOrders.rows[i].style.display;
            if(status !== 'none'){
                count ++;
                totalAmount += parseFloat(tbOrders.rows[i].cells[8].innerHTML);
                totalTxFee += parseFloat(tbOrders.rows[i].cells[9].innerHTML);
                totalDeposit += parseFloat(tbOrders.rows[i].cells[10].innerHTML);
                totalLdCommission += parseFloat(tbOrders.rows[i].cells[11].innerHTML);
                totalLdeCommission += parseFloat(tbOrders.rows[i].cells[12].innerHTML);
                totalDealerCommission += parseFloat(tbOrders.rows[i].cells[13].innerHTML);
                totalSalesCommission += parseFloat(tbOrders.rows[i].cells[14].innerHTML);
            }
        }
        $('#totalAmount').html(totalAmount.toFixed(2));
        $('#totalLdeCommission').html(totalLdeCommission.toFixed(2));
        $('#totalDealersCommission').html(totalDealerCommission.toFixed(2));
        $('#totalSalesCommission').html(totalSalesCommission.toFixed(2));
    }


    var currentRow = 0;
    var currentClientKey = '';
    $('input[name=rdTransactions]').each(function(){
        $(this).click(function(){
            currentClientKey = $(this).val();
            currentRow = $(this).attr('currentRow');
        });
    });

    $('#btResendInvoice').click(function () {
        if(currentRow <= 0){
            return;
        }
        console.log(currentClientKey);
    });

});