<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/sales/">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/sales/users">
                    <i class="fa fa-users fa-lg"></i>&nbsp;Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/sales/create">
                    <i class="fa fa-plus-square fa-lg"></i>&nbsp;Create
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