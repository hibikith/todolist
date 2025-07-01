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
<div class="auth-container">
     <p class="p-text">ログイン画面</p> 

     <%-- ログインフォーム --%>
     <form action="${pageContext.request.contextPath}/login" method="post"> 
	      <%-- ユーザー名入力フィールド --%>
	      <input type="text" 
	              name="userName"
	              placeholder="ユーザー名"/> 
	      <%-- パスワード入力フィールド --%>
	      <input type="password" 
	      		  name="password"
	              placeholder="パスワード"/> 
	      <%-- ログインボタン --%>
	      <button type="submit">ログイン</button>
      </form>

    <%-- 新規登録リンク --%>
    <div class="links">
        <p>アカウントをお持ちでない方は <a href="${pageContext.request.contextPath}/registAccount">新規登録</a></p>
    </div>

    <%-- エラーメッセージ表示 --%>
    <c:if test="${not empty errorMessage}">
	    <p class="message error-message">${errorMessage}</p>
    </c:if>

</div> 

</body>
</html>