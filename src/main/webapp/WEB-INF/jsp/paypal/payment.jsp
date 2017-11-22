<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Users
</rapid:override>

<rapid:override name="css_js">
    <script>
        $(function () {
           $('#pay').click(function () {
               setTimeout(showLoading, 20000);
           }); 
        });
    </script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/sales/">My</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/sales/users">Users</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">
    <div>
        <div>
            <h3>Click the following below button to complete payment and activate</h3>
        </div>
        <br/>
        <div style="width: 200px; margin: auto">

            <!-- sandbox -->
            <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_self">
                <input type='hidden' name='cmd' value='_xclick'>
                <input type='hidden' name='business' value='jw_seller@wiatec.com'>
                <input type='hidden' name='invoice' value='${payInfo.invoice}'>
                <input type='hidden' name='item_name' value='${payInfo.itemName}'>
                <input type='hidden' name='item_number' value='${payInfo.itemNumber}'>
                <input type='hidden' name='amount' value='${payInfo.amount}'>
                <input type='hidden' name='tax' value='${payInfo.tax}'>
                <input type='hidden' name='currency_code' value='${payInfo.currency}'>
                <input type='hidden' name='return' value='http://panel.protv.company:8080/panel/pay/return'>
                <input type='hidden' name='cancel_return' value='http://panel.protv.company:8080/panel/pay/cancel'>
                <input type='hidden' name='notify_url' value='http://panel.protv.company:8080/panel/pay/notify'>
                <input type='hidden' name='charset' value='utf-8'>
                <input type='hidden' name='no_shipping' value='1'>
                <input type='hidden' name='no_note' value='0'>
                <input type='hidden' name='rm' value='2'>
                <input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_paynowCC_LG.gif"
                       border="0" name="submit" alt="PayPal - The safer, easier way to pay online!" id="pay">
                <img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
            </form>

            <!-- live -->
            <%--<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">--%>
                <%--<input type='hidden' name='cmd' value='_xclick'>--%>
                <%--<input type='hidden' name='business' value='paotwo@gmail.com'>--%>
                <%--<input type='hidden' name='invoice' value='${payInfo.invoice}'>--%>
                <%--<input type='hidden' name='item_name' value='${payInfo.itemName}'>--%>
                <%--<input type='hidden' name='item_number' value='${payInfo.itemNumber}'>--%>
                <%--<input type='hidden' name='amount' value='${payInfo.amount}'>--%>
                <%--<input type='hidden' name='tax' value='${payInfo.tax}'>--%>
                <%--<input type='hidden' name='currency_code' value='${payInfo.currency}'>--%>
                <%--<input type='hidden' name='return' value='http://panel.protv.company:8080/panel/pay/return'>--%>
                <%--<input type='hidden' name='cancel_return' value='http://panel.protv.company:8080/panel/pay/cancel'>--%>
                <%--<input type='hidden' name='notify_url' value='http://panel.protv.company:8080/panel/pay/notify'>--%>
                <%--<input type='hidden' name='charset' value='utf-8'>--%>
                <%--<input type='hidden' name='no_shipping' value='1'>--%>
                <%--<input type='hidden' name='no_note' value='0'>--%>
                <%--<input type='hidden' name='rm' value='2'>--%>
                <%--<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_paynowCC_LG.gif"--%>
                       <%--border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">--%>
                <%--<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif"--%>
                     <%--width="1" height="1">--%>
            <%--</form>--%>
        </div>
    </div>

    <%--<div style="margin-top: 150px">--%>
        <%--<div>--%>
            <%--<h3>2.no activate after payment, type in your Invoice id to verify payment and activate</h3>--%>
        <%--</div>--%>
        <%--<br/>--%>
        <%--<div style="width: 400px; margin: auto">--%>
            <%--<form class="form-inline">--%>
                <%--<div class="form-group">--%>
                    <%--<div class="input-group">--%>
                        <%--<div class="input-group-addon">ID</div>--%>
                        <%--<input type="text" class="form-control" id="exampleInputAmount" placeholder="Invoice ID">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<button type="submit" class="btn btn-primary">VERIFY</button>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>

</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="../base.jsp"%>