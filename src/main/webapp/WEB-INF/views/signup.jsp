<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>Digital Library - Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class = "signup-main-container">
        <h1>Digital Library</h1>
    <div class="signup-container">
        <h2>Sign Up</h2>
        <form class="signup-form-container" action="submitRegistration" method="post" modelAttribute="user">
            <div class="form-group">
                <label path="name">Your name:</label>
                <input path="name" type="text" id="name" name="name" required>
                <errors path="name" cssClass="error" />
            </div>
            <div class="form-group">
                <label path="role" for="role">Role:</label>
                <select path="role" id="role" name="role" required>
                    <option path="role" value="student">Student</option>
                    <option path="role" value="teacher">Teacher</option>
                    <option path="role" value="librarian">Librarian</option>
                </select>
                <errors path="name" cssClass="error" />
            </div>
            <div class="form-group">
                <label path="id" for="id">ID Number:</label>
                <input path="id" type="text" id="id" name="id" required>
                <errors path="name" cssClass="error" />
            </div>
            <div class="form-group">
                <label path="password" for="password">Password:</label>
                <input path="password" type="password" id="password" name="password" required>
                <errors path="name" cssClass="error" />
            </div>
            <div class="form-group">
                <label path="confirm_password" for="confirm_password">Confirm Password:</label>
                <input path="confirm_password" type="password" id="confirm_password" name="confirm_password" required>
                <errors path="name" cssClass="error" />
            </div>
            <div>
                <button type="submit">Sign Up</button>
            </div>
            <div>
                <p>Already have an account? <a href="/login">Sign in</a></p>
            </div>
            <!-- <c:if test="${not empty errors}">
            <div style="color: red;">
            <c:forEach items="${errors.allErrors}" var="error">
                ${error.defaultMessage}<br>
            </c:forEach> -->
        </div>
    </c:if>
        </form>
    </div>
</div>
</body>
</html>
