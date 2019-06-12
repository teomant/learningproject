<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create admin</title>
</head>
<body>

<h1>Creating new admin</h1>

<form:form method="post" action="/admin/create" modelAttribute="registrationForm">
    <spring:bind path="username">
        <label>Login</label>
        <form:input path="username" placeholder="User name"/>
        <form:errors path="username"/>
    </spring:bind>
    <br/>
    <spring:bind path="password">
        <label>Password</label>
        <form:password path="password" placeholder="Password"/>
        <form:errors path="password"/>
    </spring:bind>
    <br/>
    <button type="submit" > Submit</button>
</form:form>
<br/>
</body>
</html>
