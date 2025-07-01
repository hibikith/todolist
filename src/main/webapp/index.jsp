<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Todolistへようこそ</title>
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/index.css">
</head>

<body>
  <div class="welcome-container"> 
        <%-- ウェルカムタイトル --%>
        <h1 class="welcome-title">Welcome!</h1> 
        <%-- ウェルカムメッセージ --%>
        <p class="welcome-message">あなたのタスクをシンプルに管理できるToDoリストへようこそ！</p>
        
        <%-- アクションボタンのグループ --%>
        <div class="action-buttons-group">
        	<%-- ログインボタン --%>
        	<a href="${pageContext.request.contextPath}/login" class="action-button login">ログイン</a> 
            <%-- アカウント新規作成ボタン --%>
            <a href="${pageContext.request.contextPath}/registAccount" class="action-button regist">アカウント新規作成</a> 
        </div>
   </div>
</body>