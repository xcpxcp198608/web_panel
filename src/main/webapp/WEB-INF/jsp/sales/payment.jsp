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
            <%--<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">--%>
                <%--<input type="hidden" name="cmd" value="_xclick">--%>
                <%--<input type="hidden" name="business" value="patrickxu@wiatec.com">--%>
                <%--<input type="hidden" name="lc" value="C2">--%>
                <%--<input type="hidden" name="item_name" value="pay now">--%>
                <%--<input type="hidden" name="amount" value="100.00">--%>
                <%--<input type="hidden" name="currency_code" value="USD">--%>
                <%--<input type="hidden" name="button_subtype" value="services">--%>
                <%--<input type="hidden" name="no_note" value="0">--%>
                <%--<input type="hidden" name="bn" value="PP-BuyNowBF:btn_paynowCC_LG.gif:NonHostedGuest">--%>
                <%--<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_paynowCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">--%>
                <%--<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">--%>
            <%--</form>--%>

            <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
                <input type="hidden" name="cmd" value="_s-xclick">
                <input type="hidden" name="hosted_button_id" value="EG3GVC2XHCPYJ">
                <input type="hidden" name="lc" value="C2">
                <input type="hidden" name="amount" value="100.00">
                <input type="hidden" name="currency_code" value="USD">
                <input type="hidden" name="button_subtype" value="services">
                <input type="hidden" name="no_note" value="0">
                <input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_paynowCC_LG.gif"
                       border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
                <img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif"
                     width="1" height="1">
            </form>

        </div>
    </div>

</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="../base.jsp"%>