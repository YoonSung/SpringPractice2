<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringPractice2 :: 회원가입</title>

<%@ include file="../commons/_header.jspf"%>

</head>
<body>
	<%@ include file="../commons/_top.jspf"%>

	<div class="container">
		<div class="row">
			<div class="span12">
				<section id="typography">
				<div class="page-header">
					<h1>로그인</h1>
				</div>
			
				
				<springform:form modelAttribute="authentication" cssClass="form-horizontal" action="/users/login" method="post">
					<c:if test="${not empty errorMessage}">
						<div style="border: 1px solid red;color: red; font-size: 18px; padding: 20px; display: inline-block;">
							${errorMessage}
						</div>
					</c:if>
					<div class="control-group">
						<label class="control-label" for="userId">사용자 아이디</label>
						<div class="controls">
							<springform:input path="userId" />
							<springform:errors path="userId" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">비밀번호</label>
						<div class="controls">
							<springform:password id="password" path="password"/>
							<springform:errors path="password" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary">로그인</button>
						</div>
					</div>
				</springform:form>
			</div>
		</div>
	</div>
</body>
</html>