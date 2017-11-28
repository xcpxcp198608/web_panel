$(function () {

    var tbCategory = $('#tbCategory').get(0).tBodies[0];

   $('#seCategory').change(function () {
       var value = $(this).val();
       if(value.length <= 0){
           var length = tbCategory.rows[1].cells.length;
           for(var i = 0 ; i < length; i ++){
               tbCategory.rows[1].cells[i].innerHTML = 0;
           }
           return;
       }
       var url = baseUrl + "/category/" + value;
       $.get(url,function (response, status) {
           tbCategory.rows[1].cells[0].innerHTML = response['firstPay'];
           tbCategory.rows[1].cells[1].innerHTML = response['monthPay'];
           tbCategory.rows[1].cells[2].innerHTML = response['deposit'];
           tbCategory.rows[1].cells[3].innerHTML = response['expires'];
           tbCategory.rows[1].cells[4].innerHTML = response['bonus'];
           tbCategory.rows[1].cells[5].innerHTML = response['price'];
       });
   });

   $('#ipMac').keyup(function () {
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
   
   $('#btSubmitCreate').click(function () {
       $('#errorMessage').html('');
       var category = $('#seCategory').val();
       var mac = $('#ipMac').val();
       var firstName = $('#ipFirstName').val();
       var lastName = $('#ipLastName').val();
       var email = $('#ipEmail').val();
       var phone = $('#ipPhone').val();
       var cardNumber = $('#ipCardNumber').val();
       var expirationDate = $('#ipExpirationDate').val();
       var securityKey = $('#ipSecurityKey').val();
       if(category.length <= 0){
           $('#errorMessage').html('have no choose plan');
           return;
       }
       if(mac.length !== 17){
           $('#errorMessage').html('mac input error');
           return;
       }
       if(firstName.length <= 0){
           $('#errorMessage').html('first name input error');
           return;
       }
       if(lastName.length <= 0){
           $('#errorMessage').html('last name input error');
           return;
       }
       if(email.length <= 0){
           $('#errorMessage').html('email input error');
           return;
       }
       if(!validateEmail(email)){
           $('#errorMessage').html('email format error');
           return;
       }
       if(phone.length <= 0){
           $('#errorMessage').html('phone input error');
           return;
       }
       if(cardNumber.length !== 16){
           $('#errorMessage').html('card number input error');
           return;
       }
       if(expirationDate.length !== 4){
           $('#errorMessage').html('expiration date input error');
           return;
       }
       if(securityKey.length !== 3){
           $('#errorMessage').html('security key input error');
           return;
       }
       var url = baseUrl + '/sales/create';
       $.ajax({
           type: 'POST',
           url: url,
           data: {'category':category, 'mac':mac, 'firstName': firstName, 'lastName': lastName,
               'email': email, 'phone': phone, 'cardNumber': cardNumber,
               'expirationDate': expirationDate, 'securityKey': securityKey},
           dataType: 'json',
           beforeSend:function(){
                showLoading()
           },
           success:function(response){
               hideLoading();
               if(response.code === 200) {
                   showNotice(response.message);
                   window.open(baseUrl + "/sales/users/", "_self")
               }else{
                   $('#errorMessage').html(response.message);
               }
           },
           error:function(){
                hideLoading()
           }
       });
   });


});