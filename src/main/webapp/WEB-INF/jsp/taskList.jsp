<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="stylesheet" href="./css/common.css">
<link rel="stylesheet" href="./css/task.css">
</head>
<body>
    <div class="task-page-container">
        <p class="p-text">${loginUser.username}さんのToDoリスト</p>
       
        <%-- タスク入力フォーム --%>
        <form action="${pageContext.request.contextPath}/tasks" method="post"> 
            <%-- タスク名入力グループ --%>
            <div class="task-form-group"> 
                <label for="taskName">タスク名:</label>
                <input type="text" id="taskName" name="taskName" required placeholder="例: 報告書を作成する">
            </div>
            
            <%-- ステータス選択グループ --%>
            <div class="task-form-group">
                <label for="status">ステータス:</label>
                <select id="status" name="status">
                    <option value="未着手">未着手</option>
                    <option value="進行中">進行中</option>
                    <option value="完了">完了</option>
                </select>
            </div>

            <%-- 優先度選択グループ --%>
            <div class="task-form-group">
                <label for="priority">優先度:</label>
                <select id="priority" name="priority">
                    <option value="低">低</option>
                    <option value="中">中</option>
                    <option value="高">高</option>
                </select>
            </div>

            <%-- 期限入力グループ --%>
            <div class="task-form-group">
                <label for="dueDate">期限:</label>
                <input type="date" id="dueDate" name="dueDate">
            </div>
             
            <%-- タスク追加ボタン --%>
            <div> 
                <button type="submit" class="add-task-button">タスクを追加</button> 
            </div>
        </form>
    </div>
    	
    <div class="task-page-container">
        <%-- メッセージ表示（成功/エラー） --%>
        <c:if test="${not empty successMessage}">
            <p class="message success-message">${successMessage}</p>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="message error-message">${errorMessage}</p>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <%-- タスクリストの表示 --%>
        <c:choose>
            <c:when test="${not empty requestScope.taskList}">
                <%-- レスポンシブ対応テーブルコンテナ --%>
                <div class="table-responsive"> 
                    <%-- タスク表示テーブル --%>
                    <table class="task-table"> 
                        <thead>
                            <tr> 
                                <th>タスク名</th>
                                <th>状況</th>
                                <th>優先度</th>
                                <th>期限</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="task" items="${requestScope.taskList}">
                                <tr> 
                                    <td>${task.taskName}</td> 
                                    <td class="center-align">${task.status}</td>
                                    <%-- 優先度によってスタイルを適用 --%>
                                    <td class="priority ${task.priority eq '高' ? 'high' : (task.priority eq '中' ? 'medium' : 'low')}">${task.priority}</td> 
                                    <td class="center-align">${task.formattedDueDate}</td> 
                                   
                                    <%-- タスク操作ボタン群 --%>
                                     <td class="action-buttons">
                                        <%-- 編集ボタン --%>
                                        <form action="${pageContext.request.contextPath}/editTask" method="get" style="display: inline;">
                                            <input type="hidden" name="taskId" value="${task.taskId}">
                                            <button type="submit" class="edit-button">編集</button>
                                        </form>
                                        
                                        <%-- 削除ボタン --%>
                                        <form action="${pageContext.request.contextPath}/deleteTask" method="post" style="display: inline;">
                                            <input type="hidden" name="taskId" value="${task.taskId}"> 
                                            <input type="hidden" name="taskName" value="${task.taskName}"> 
                                            <button type="submit" class="delete-button">削除</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <%-- タスクがない場合のメッセージ --%>
                <p class="message">まだタスクがありません。新しいタスクを追加しましょう！</p>
            </c:otherwise>
        </c:choose>
        
        <%-- ログアウトリンク --%>
        <div class="links">
   			 <p><a href="${pageContext.request.contextPath}/logout">ログアウト</a></p>
		</div>
    </div>
</body>
</html>