<%@ page language = "java" contentType = "text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>

    <style>
        .error {
            color: red;
            font-style: italic;
            position: fixed;
        }
    </style>
</head>
<body>
    <div align = "center">
    <h1 align = "center">Student Registration Form</h1>
    <br>

    <form:form method="post" modelAttribute="student">
        <form:label path="name">Student's Name:</form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/><br>

        <form:label path="email">Email:</form:label>
        <form:input path="email" label="Email:"/>
        <form:errors path="email" cssClass="error"/><br>

        <form:label path="password">Password:</form:label>
        <form:password path="password"/>
        <form:errors path="password" cssClass="error"/><br>

        <form:label path="birthDate">BirthDate (yyyy-mm-dd):</form:label>
        <form:input path="birthDate"/>
        <form:errors path="birthDate" cssClass="error"/><br>

        <form:label path="branch">Branch:</form:label>
        <form:select path="branch" items = "${branchList}"/>
        <form:errors path="branch"/><br>

        <form:label path="gender">Gender:</form:label>
        Male<form:radiobutton path="gender" value="Male"/>
        Female<form:radiobutton path="gender" value="Female"/>
        <form:errors path="gender" cssClass="error"/><br>

        <form:label path="hobbies">Hobbies:</form:label>
        <form:checkbox path="hobbies" value="Programming" label="Programming"/>
        <form:checkbox path="hobbies" value="Gaming" label="Gaming"/>
        <form:checkbox path="hobbies" value="Reading" label="Reading"/>
        <form:checkbox path="hobbies" value="Singing" label="Singing"/>
        <form:checkbox path="hobbies" value="Dancing" label="Dancing"/>
        <form:errors path="hobbies" cssClass="error"/><br>

        <form:button>Submit</form:button>
    </form:form>
    </div>

</body>
</html>