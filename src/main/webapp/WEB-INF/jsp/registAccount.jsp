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
<div class="auth-container">
	<p class="p-text">会員登録</p>
		<form action="${pageContext.request.contextPath}/registAccount" method="post">
			<input type="text" 
	              	name="userName"
	                placeholder="ユーザー名"/>
	        <input type="password" 
	      		    name="password"
	              	placeholder="パスワード"/>
	        <input type="password" 
         			name="confirmPassword"
         			placeholder="パスワード（確認用）" />
	        <button type="submit">登録</button>
	     </form>

    <div class="links">
        <p>すでにアカウントをお持ちの方はこちら <a href="${pageContext.request.contextPath}/login">ログイン</a></p>
    </div>
    			
    <c:if test="${not empty errorMessage}">
	    <p class="message error-message">${errorMessage}</p>
    </c:if>
</div>
</body>
</html>