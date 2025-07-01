<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスクの編集</title>
<link rel="stylesheet" href="./css/common.css">
</head>
<body>
    <div class="auth-container">
        <p class="p-text">タスクの編集</p>

        <%-- エラーメッセージ表示 --%>
        <c:if test="${not empty errorMessage}">
            <p class="message error-message">${errorMessage}</p>
            <c:remove var="errorMessage" scope="request"/> 
        </c:if>

        <c:if test="${not empty task}">
            <form action="${pageContext.request.contextPath}/editTask" method="post">
                <input type="hidden" name="taskId" value="${task.taskId}"> 
                
                <%-- タスク名フィールド --%>
                <div class="task-form-group">
                    <label for="taskName">タスク名:</label>
                    <input type="text" id="taskName" name="taskName" value="${task.taskName}" required>
                </div>
                
                <%-- ステータスフィールド --%>
                <div class="task-form-group">
                    <label for="status">ステータス:</label>
                    <select id="status" name="status">
                        <option value="未着手" <c:if test="${task.status eq '未着手'}">selected</c:if>>未着手</option>
                        <option value="進行中" <c:if test="${task.status eq '進行中'}">selected</c:if>>進行中</option>
                        <option value="完了" <c:if test="${task.status eq '完了'}">selected</c:if>>完了</option>
                    </select>
                </div>
                
                <%-- 優先度フィールド --%>
                <div class="task-form-group">
                    <label for="priority">優先度:</label>
                    <select id="priority" name="priority">
                        <option value="高" <c:if test="${task.priority eq '高'}">selected</c:if>>高</option>
                        <option value="中" <c:if test="${task.priority eq '中'}">selected</c:if>>中</option>
                        <option value="低" <c:if test="${task.priority eq '低'}">selected</c:if>>低</option>
                    </select>
                </div>
                
                <%-- 期限フィールド --%>
                <div class="task-form-group">
                    <label for="dueDate">期限:</label>
                    <input type="date" id="dueDate" name="dueDate" 
							value="<c:if test="${not empty task.dueDate}">${task.formattedDueDate}
							</c:if>">                
				</div>
                
                <%-- ボタンをグループ化 --%>
                <div class="action-buttons-group">
                    <%-- 更新ボタン --%>
                    <button type="submit" class="add-task-button">更新</button>
                    
                    <%-- キャンセルボタン（タスクリストへ戻る） --%>
                    <a href="${pageContext.request.contextPath}/tasks" class="action-button secondary">キャンセル</a>
                </div>
            </form>
        </c:if>
        <c:if test="${empty task}">
            <p class="message error-message">編集対象のタスクが見つかりませんでした。</p>
            <p class="links"><a href="${pageContext.request.contextPath}/tasks">タスクリストに戻る</a></p>
        </c:if>
    </div>
    
    <%-- ログアウトリンク --%>
    <div class="links">
    	<p><a href="${pageContext.request.contextPath}/logout">ログアウト</a></p>
	</div>
</body>
</html>