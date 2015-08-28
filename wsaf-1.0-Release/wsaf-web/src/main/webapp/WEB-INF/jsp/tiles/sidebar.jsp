<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	$(document).ready(function(){
		sidebar_page_init();
	});
</script>

<div id="sidebar">
	<div id="sidebar-wrapper">
		<h1 id="sidebar-title">
			<a href="#"></a>
		</h1>
		<a href="#" target="_blank">
			<img id="logo" src="images/logo.png" />
		</a>
		<div id="profile-links">
			你好 &nbsp; ${currentUser.name}
			<br />
			<br />
			<a href="common/logout" title="退出系统">退出系统</a>
		</div>
		<ul id="main-nav">
		<li><a id="aa" href="relationRule/toRelationRuleEdit/type20141216" class="nav-top-item no-submenu">配置权限 </a></li>
		<li><a id="bb" href="user/list?type=type20141216" class="nav-top-item no-submenu">用户列表 </a></li>
		<li><a id="user-information" href="user/list" class="nav-top-item no-submenu">用户 </a></li>
<!-- 		<li><a id="role-information" href="role/list" class="nav-top-item no-submenu">角色 </a></li> -->
<!-- 		<li><a id="group-information" href="group/list" class="nav-top-item no-submenu">分组 </a></li> -->
<!-- 		<li><a id="organ-information" href="organ/list" class="nav-top-item no-submenu">组织结构 </a></li> -->
<!-- 		<li><a id="privilege-information" href="privilege/list" class="nav-top-item no-submenu">权限 </a></li> -->
<!-- 		<li><a id="resource-information" href="resource/list" class="nav-top-item no-submenu">资源 </a></li>		 -->
<!-- 		<li><a id="privilege-information" href="relation/list" class="nav-top-item no-submenu">关系 </a></li> -->
<!-- 		<li><a id="resource-information" href="objInfo/list" class="nav-top-item no-submenu">对象</a></li>						 -->
<!--			<li><a id="my-information" href="user/my-information" class="nav-top-item no-submenu">我的信息 </a></li>-->
<!--			<li><a id="contact" href="#" class="nav-top-item">联系人 </a>-->
<!--				<ul>-->
<!--					<li>-->
<!--						<a id="contact-group" href="contact/list-groups">分组</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">联系人</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">权限</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">资源</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">角色</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">用户</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a id="contact-contact" href="contact/list-contacts/1">组织</a>-->
<!--					</li>-->
<!--				</ul>-->
<!--			</li>-->
			<security:authorize access="hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')">
			<li><a id="system-setting" href="#" class="nav-top-item">系统管理</a>
				<ul>
					<li>
						<a id="list-users" href="user/list-users/1">禁用账户</a>
					</li>
				</ul>
			</li>
			</security:authorize>
			<li><a id="setting" href="#" class="nav-top-item">设置 </a>
				<ul>
					<li>
						<a id="change-passwod" href="user/change-password">更改密码</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>
</div>