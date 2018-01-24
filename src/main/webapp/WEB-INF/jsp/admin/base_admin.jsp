<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/admin/">
                    <span data-feather="home"></span>
                    Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/dealer">
                    <span data-feather="briefcase"></span>
                    Dealer
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/sales">
                    <span data-feather="grid"></span>
                    Sales
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/users">
                    <span data-feather="users"></span>
                    Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/commission">
                    <span data-feather="bar-chart-2"></span>
                    Commission
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/devices">
                    <span data-feather="layers"></span>
                    Devices
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout">
                    <span data-feather="log-out"></span>
                    Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="../base.jsp"%>