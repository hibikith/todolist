<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>新規登録</title>
	<link rel="stylesheet" href="./css/common.css">
</head>

<body>
<div class="container">
	<p class="p-text">会員登録</p>
		<!-- フォームの表示 -->
		<form ation="/regist-account" method="post">
			<input type="text" 
	              	name="userName"
	                placeholder="Username"/>
	        <input type="password" 
	      		    name="password"
	              	placeholder="Password"/>
	        <input type="password" 
         			name="confirmPassword"
         			placeholder="Confirm Password" />
         			
	        <button type="submit">登録</button>
	     </form>
</div>
<!-- ログイン画面へのリンクの表示 -->	
<div class="links">
    <p>ログインの方はこちら <a href="${pageContext.request.contextPath}/login">新規登録</a></p>
</div>
			
<!-- エラーメッセージの表示 -->
<c:if test="${not empty errorMessage}">
	<p class="message error-message">${errorMessage}</p>
</c:if>
</body>
</html>