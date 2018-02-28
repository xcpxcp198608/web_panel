$(function () {

    var currentCategory = 'M2';
    $('input[name=categoryRadio]').each(function(){
        $(this).click(function(){
            currentCategory = $(this).val();
        });
    });


    var currentPaymentMethod = '1';
    $('input[name=payMethod]').each(function(){
        $(this).click(function(){
            currentPaymentMethod = $(this).val();
            console.log(currentPaymentMethod);
            if(currentPaymentMethod === '0'){
                $('#dCardInfo').css('display', 'none')
            }else if(currentPaymentMethod === '1'){
                $('#dCardInfo').css('display', 'block')
            }else if(currentPaymentMethod === '2'){
                $('#dCardInfo').css('display', 'none')
            }else{
                showNotice("payment method error")
            }
        });
    });

    var express = false;
    $('input[name=express]').each(function(){
        $(this).click(function(){
            express = $(this).val();
        });
    });
   
   $('#btSubmitCreate').click(function () {
       var mac = $('#ipMac').val();
       var firstName = $('#ipFirstName').val();
       var lastName = $('#ipLastName').val();
       var email = $('#ipEmail').val();
       var phone = $('#ipPhone').val();
       var postCode = $('#ipPostCode').val();
       var postAddress = $('#ipPostAddress').val();
       var cardNumber = $('#ipCardNumber').val();
       var expirationDate = $('#ipExpirationDate').val();
       var securityKey = $('#ipSecurityKey').val();
       var zipCode = $('#ipZipCode').val();
       var billingAddress = $('#ipBillingAddress').val();
       if(currentCategory.length <= 0){
           showErrorMessage($('#errorCreate'), 'No Plan chosen');
           return;
       }
       if(mac.length !== 17){
           showErrorMessage($('#errorCreate'), 'mac input error');
           return;
       }
       if(firstName.length <= 0){
           showErrorMessage($('#errorCreate'), 'first name input error');
           return;
       }
       if(lastName.length <= 0){
           showErrorMessage($('#errorCreate'), 'last name input error');
           return;
       }
       if(email.length <= 0){
           showErrorMessage($('#errorCreate'), 'email input error');
           return;
       }
       if(!validateEmail(email)){
           showErrorMessage($('#errorCreate'), 'email format error');
           return;
       }
       if(phone.length <= 0){
           showErrorMessage($('#errorCreate'), 'phone input error');
           return;
       }
       if(postCode.length <= 0){
           showErrorMessage($('#errorCreate'), 'post code input error');
           return;
       }
       if(postAddress.length <= 0){
           showErrorMessage($('#errorCreate'), 'post address input error');
           return;
       }
       if(currentPaymentMethod === '1') {
           if (cardNumber.length !== 16) {
               showErrorMessage($('#errorCreate'), 'card number input error');
               return;
           }
           if (expirationDate.length !== 4) {
               showErrorMessage($('#errorCreate'), 'expiration date input error');
               return;
           }
           if (securityKey.length !== 3) {
               showErrorMessage($('#errorCreate'), 'security key input error');
               return;
           }
           if (zipCode.length <= 0) {
               showErrorMessage($('#errorCreate'), 'zip code input error');
               return;
           }
           if (billingAddress.length <= 0) {
               showErrorMessage($('#errorCreate'), 'billing address input error');
               return;
           }
       }
       $('#errorCreate').hide();
       var url = baseUrl + '/mexico/customer/create/' + currentPaymentMethod;
       $.ajax({
           type: 'POST',
           url: url,
           data: {'category': currentCategory, 'mac': mac, 'firstName': firstName,
               'lastName': lastName, 'email': email, 'phone': phone, 'postCode': postCode,
               'postAddress': postAddress, 'express': express, 'cardNumber': cardNumber,
               'expirationDate': expirationDate, 'securityKey': securityKey,
               'zipCode': zipCode, 'billingAddress': billingAddress},
           dataType: 'json',
           beforeSend:function(){
                showLoading()
           },
           success:function(response){
               hideLoading();
               if(response.code === 200) {
                   window.open(baseUrl + "/mexico/customers", "_self")
               }else{
                   showErrorMessage($('#errorCreate'), response.message);
               }
           },
           error:function(){
                hideLoading();
               showErrorMessage($('#errorCreate'), 'communication error');
           }
       });
   });

});