<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Users
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/sales/users.js"></script>
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
            <h3 style="width: 100%; text-align: center">user create successfully, activate after payment</h3>
        </div>
        <br/>
        <div style="margin: auto; width: 200px">

            <%--<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_blank">--%>
                <%--<input type='hidden' name='cmd' value='_xclick'>--%>
                <%--<input type='hidden' name='business' value='jw_seller@wiatec.com'>--%>
                <%--<input type='hidden' name='invoice' value='s20171114140009'>--%>
                <%--<input type='hidden' name='item_name' value='B1'>--%>
                <%--<input type='hidden' name='item_number' value='b1'>--%>
                <%--<input type='hidden' name='amount' value='0.01'>--%>
                <%--<input type='hidden' name='tax' value='0'>--%>
                <%--<input type='hidden' name='currency_code' value='USD'>--%>
                <%--<input type='hidden' name='return' value='http://localhost:8080/panel/pay/return'>--%>
                <%--<input type='hidden' name='cancel_return' value='http://localhost:8080/panel/pay/cancel'>--%>
                <%--<input type='hidden' name='notify_url' value='http://localhost:8080/panel/pay/notify'>--%>
                <%--<input type='hidden' name='charset' value='utf-8'>--%>
                <%--<input type='hidden' name='no_shipping' value='1'>--%>
                <%--<input type='hidden' name='no_note' value='0'>--%>
                <%--<input type='hidden' name='rm' value='2'>--%>
                <%--<input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_paynowCC_LG.gif"--%>
                       <%--border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">--%>
                <%--<img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">--%>
            <%--</form>--%>

            <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
                <input type='hidden' name='cmd' value='_xclick'>
                <input type='hidden' name='business' value='paotwo@gmail.com'>
                <input type='hidden' name='invoice' value='s20171114140009'>
                <input type='hidden' name='item_name' value='B1'>
                <input type='hidden' name='item_number' value='b1'>
                <input type='hidden' name='amount' value='0.01'>
                <input type='hidden' name='tax' value='0'>
                <input type='hidden' name='currency_code' value='USD'>
                <input type='hidden' name='return' value='http://panel.protv.company:8080/panel/pay/return'>
                <input type='hidden' name='cancel_return' value='http://panel.protv.company:8080/panel/pay/cancel'>
                <input type='hidden' name='notify_url' value='http://panel.protv.company:8080/panel/pay/notify'>
                <input type='hidden' name='charset' value='utf-8'>
                <input type='hidden' name='no_shipping' value='1'>
                <input type='hidden' name='no_note' value='0'>
                <input type='hidden' name='rm' value='2'>
                <input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_paynowCC_LG.gif"
                       border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
                <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif"
                     width="1" height="1">
            </form>

        </div>
    </div>

</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="../base.jsp"%>