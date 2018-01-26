<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/dealer/">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/dealer/sales">
                    <i class="fa fa-bullhorn fa-lg"></i>&nbsp;Sales
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/dealer/users">
                    <i class="fa fa-users fa-lg"></i>&nbsp;Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout">
                    <i class="fa fa-sign-out fa-lg"></i>&nbsp;Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="../base.jsp"%>