<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Users
</rapid:override>
<rapid:override name="css_js">
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
            <h3 style="width: 100%; text-align: center">${result}</h3>
        </div>
    </div>

</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="../base.jsp"%>