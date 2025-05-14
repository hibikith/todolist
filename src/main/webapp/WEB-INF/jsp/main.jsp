<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>メイン画面</title>
</head>
<body>

	<h2>タスク一覧</h2>
		<form>
			<c:forEach var="task" items="${taskList}">
				<c:out value="${taskList.taskId}">
				<c:out value="${taskList.taskName}">
				<c:out value="${taskList.escription}">
				<a>タスクの編集</a>
		</form>
</body>
</html>