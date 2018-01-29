$(function () {

    var currentCategory = '';
    $('input[name=categoryRadio]').each(function(){
        $(this).click(function(){
            currentCategory = $(this).val();
            console.log(currentCategory);
        });
    });

   $('#ipMac').keyup(function (e) {
       var code = e.keyCode;
       if(code === 8){
           return true;
       }
       if(code === 32){
           return;
       }
       var mac = $(this).val();
       if(mac.length === 2){
           $(this).val(mac + ':');
           return;
       }
       if(mac.length === 5){
           $(this).val(mac + ':');
           return;
       }
       if(mac.length === 8){
           $(this).val(mac + ':');
           return;
       }
       if(mac.length === 11){
           $(this).val(mac + ':');
           return;
       }
       if(mac.length === 14){
           $(this).val(mac + ':');
       }
   });

    var currentPaymentMethod = 0;
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
   
   $('#btSubmitCreate').click(function () {
       var mac = $('#ipMac').val();
       var firstName = $('#ipFirstName').val();
       var lastName = $('#ipLastName').val();
       var email = $('#ipEmail').val();
       var phone = $('#ipPhone').val();
       var cardNumber = $('#ipCardNumber').val();
       var expirationDate = $('#ipExpirationDate').val();
       var securityKey = $('#ipSecurityKey').val();
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
       }
       $('#errorCreate').hide();
       var url = baseUrl + '/sales/create/' + currentPaymentMethod;
       $.ajax({
           type: 'POST',
           url: url,
           data: {'category': currentCategory, 'mac': mac, 'firstName': firstName,
               'lastName': lastName, 'email': email, 'phone': phone, 'cardNumber': cardNumber,
               'expirationDate': expirationDate, 'securityKey': securityKey},
           dataType: 'json',
           beforeSend:function(){
                showLoading()
           },
           success:function(response){
               hideLoading();
               if(response.code === 200) {
                   window.open(baseUrl + "/sales/users/", "_self")
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