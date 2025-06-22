<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ログイン</title>
	<link rel="stylesheet" href="./css/common.css">
</head>
<body>
<!-- フォームの表示 -->
<div class="container">
     <form  ation="/login" method="post">
	     <p class="p-text">ログイン画面</p>
	      <input type="text" 
	              name="userName"
	              placeholder="Username"/>
	      <input type="password" 
	      		  name="password"
	              placeholder="Password"/>
	      <button type="submit">ログイン</button>
      </form>
</div>
<!-- 新規登録画面へのリンクの表示 -->
 <div class="links">
     <p>アカウントをお持ちでない方は <a href="${pageContext.request.contextPath}/registAccount">新規登録</a></p>
 </div>

<!-- エラーメッセージの表示 -->
<c:if test="${not empty errorMessage}">
	<p class="message error-message">${errorMessage}</p>
</c:if>

</body>
</html>