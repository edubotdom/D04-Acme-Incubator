<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<acme:form-textbox code="authenticated.forum.list.label.titleRound" path="nameRound" readonly="true" />
	<acme:form-textbox code="authenticated.forum.list.label.tickerRound" path="tickerRound" readonly="true" />

	<jstl:if test="${command != 'create' }">
	<acme:form-return code="authenticated.forum.form.button.messageList" action= "${direccion}"/>
	<acme:form-return code="authenticated.forum.form.button.message.create" action="${forumCreateMessage}" />
	</jstl:if>
	
	<acme:form-return code="authenticated.forum.form.button.return" />
</acme:form>
