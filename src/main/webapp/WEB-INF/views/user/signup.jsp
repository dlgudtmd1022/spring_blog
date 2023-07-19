<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <form action="/signup" method="POST">
        <div class="row">
            <div class="col-3">
                <label for="loginId" >아이디</label>
                <input type="text"  name="loginId" id="loginId" placeholder="아이디를 적어주세요" required>
            </div>
            <div class="col-3">
                <label for="email" >이메일</label>
                <input type="email" name="email" id="email" placeholder="이메일을 적어주세요" required>
            </div>
            <div class="col-3">
                <label for="password" >비밀번호</label>
                <input type="password" name="password" id="password" placeholder="비밀번호를 적어주세요" required>
            </div>
        </div>
        <div class = "row">
            <div class="col-6">
                <input type="submit" class="btn btn-primary" value="회원가입하기">
            </div>
        </div>
<%--        아이디 : <input type="text" name="loginId" required/><br>--%>
<%--        이메일: <input type="email" name="email" required/><br>--%>
<%--        비밀번호 : <input type="password" name="password" required/><br>--%>
<%--        <input type="submit"value="가입">--%>
    </form>
</div>

</body>
</html>
