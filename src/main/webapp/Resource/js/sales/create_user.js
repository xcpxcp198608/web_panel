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
       $.post(url,{},function (response, status) {
           tbCategory.rows[1].cells[0].innerHTML = response['price'];
           tbCategory.rows[1].cells[1].innerHTML = response['deposit'];
           tbCategory.rows[1].cells[2].innerHTML = response['monthPay'] * response['expires'];
           tbCategory.rows[1].cells[3].innerHTML = response['monthPay'];
           tbCategory.rows[1].cells[4].innerHTML = response['expires'];
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
       var url = baseUrl + '/sales/create';
       $.ajax({
           type: 'PUT',
           url: url,
           contentType: "application/json; charset=utf-8",
           data: JSON.stringify({'category':category, 'mac':mac, 'firstName': firstName,
               'lastName': lastName, 'email': email, 'phone': phone}),
           dataType: 'json',
           beforeSend:function(){
                showLoading()
           },
           success:function(response){
               hideLoading();
               console.log(response);
               if(response.code === 200) {
                   showNotice(response.message);
                   window.open(baseUrl + "/sales/activate/" + response['data'], "_self")
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