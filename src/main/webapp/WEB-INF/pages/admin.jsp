<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin Page</title>
</head>
<body>
    ${title}<br/><br/>
    Welcome: ${user}, 
    you are successfully logged in as ${role}.
    <br/>
    <a href="billing">Billing tab</a><br>
    <a href="data">Data tab</a><br>
     <a href="usermgmt">User Management Tab</a><br>
    <a href="<c:url value="/logout" />">Logout</a>
</body>
</html>