<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Digital Library - Sign In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="./script.js"></script>
</head>
<body>
    <div class="login-main-container">
        <h1>Digital Library</h1>
    <div class="login-container">
        <h2>Sign In</h2>
        <form class="login-form-container" action="submitLogin" method="post" modelAttribute="loginuser">
            <div class="form-group">
                <label path="role" for="role">Role:</label>
                <select path="role" id="role" name="role" required>
                    <option path="role" value="student">Student</option>
                    <option path="role" value="teacher">Teacher</option>
                    <option path="role" value="librarian">Librarian</option>
                </select>
            </div>
            <div class="form-group">
                <label path="id" for="id">ID Number:</label>
                <input path="id" type="text" id="id" name="id" required>
            </div>
            <div class="form-group">
                <label path="password" for="password">Password:</label>
                <input path="password" type="password" id="password" name="password" required>
            </div>
            <div >
                <button type="submit">Sign In</button>
            </div>
            <div>
                <p>New User? <a href="/signup">Sign up</a></p>
            </div>
        </form>
    </div>
</div>
</body>
</html>