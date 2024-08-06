<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>Digital Library - Sign In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/signup.js"></script>
</head>
<body class="signing">
    <div class="login-main-container">
        <h1>Digital Library</h1>
        <div class="login-container">
            <h2>Sign In</h2>
            <form:form class="login-form-container" action="${pageContext.request.contextPath}/submitLogin" method="post" modelAttribute="loginuser">
                <div class="form-group">
                    <form:errors path="*" cssClass="error" element="div"/>
                </div>
                <div class="form-group">
                    <form:label path="role" for="role">Role:</form:label>
                    <form:select path="role" id="role">
                        <form:option value="student">Student</form:option>
                        <form:option value="teacher">Teacher</form:option>
                        <form:option value="librarian">Librarian</form:option>
                    </form:select>
                </div>
                <div class="form-group">
                    <form:label path="id" for="id">ID Number:</form:label>
                    <form:input path="id" id="id" required="True"/>
                </div>
                <div class="form-group">
                    <form:label path="password" for="password">Password:</form:label>
                    <form:password path="password" id="password" required="True"/>
                </div>
                <div>
                    <button type="submit">Sign In</button>
                </div>
                <div>
                    <p>New User? <a href="${pageContext.request.contextPath}/signup">Sign up</a></p>
                </div>
            </form:form>
        </div>
        <!-- <div>
            <c:if test="${not empty error}">
                <script>
                    showMessage("${error}", 'error');
                </script>
            </c:if>
            <c:if test="${not empty message}">
                <script>
                    showMessage("${message}", 'success');
                </script>
            </c:if>
    </div> -->
</body>
</html>
