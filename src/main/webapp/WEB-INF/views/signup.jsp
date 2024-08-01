<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>Digital Library - Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="signing">
    <div class="signup-main-container">
        <h1>Digital Library</h1>
        <div class="signup-container">
            <h2>Sign Up</h2>
            <form:form class="signup-form-container" action="${pageContext.request.contextPath}/submitRegistration" method="post" modelAttribute="user">
                <div class="form-group">
                    <form:label path="name" for="name">Your name:</form:label>
                    <form:input path="name" id="name" />
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:label path="role" for="role">Role:</form:label>
                    <form:select path="role" id="role">
                        <form:option value="student">Student</form:option>
                        <form:option value="teacher">Teacher</form:option>
                        <form:option value="librarian">Librarian</form:option>
                    </form:select>
                    <form:errors path="role" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:label path="id" for="id">ID Number:</form:label>
                    <form:input path="id" id="id" />
                    <form:errors path="id" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:label path="password" for="password">Password:</form:label>
                    <form:password path="password" id="password" />
                    <form:errors path="password" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:label path="confirmPassword" for="confirmPassword">Confirm Password:</form:label>
                    <form:password path="confirmPassword" id="confirmPassword" />
                    <form:errors path="confirmPassword" cssClass="error"/>
                </div>
                <div>
                    <button type="submit">Sign Up</button>
                </div>
                <div>
                    <p>Already have an account? <a href="/login">Sign in</a></p>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
