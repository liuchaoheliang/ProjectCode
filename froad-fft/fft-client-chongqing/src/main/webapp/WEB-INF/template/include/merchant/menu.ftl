<script src="${base}/resources/common/js/menu.js"></script>
<#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"]>
<!--左侧导航开始-->
<div class="left" id="leftHeight">
	<!--<h5>
		<@shiro.principal />
		,欢迎您
	</h5>-->
	<ul id="admin_menu">
		 <li>
			<a class="first">
				<u>提货管理</u>
			</a>
			<ul class="mainfirst">
				<li>
					<a href="${base}/merchant/presell/verify.jhtml">
						<u>提货认证</u>
					</a>
				</li>
				<li>
					<a href="${base}/merchant/presell/list.jhtml">
						<u>提货列表</u>
					</a>
				</li>
			</ul>
		</li>
	
	
		<@shiro.hasPermission name="merchant:stock_pile">
		<li>
			<a <#-- href="${base}/merchant/index.jhtml"--> class="first">
				<u>库存管理</u>
			</a>
			<ul class="mainfirst">
				<li class="">
					<a href="${base}/merchant/stock_pile/list.jhtml">
						<u>库存列表</u>
					</a>
				</li>
			</ul>
		</li>
		</@shiro.hasPermission>

		<li>
			<a  class="first">
				<u>退换货管理</u>
			</a>
		</li>
		<ul class="mainfirst">
			<li>
				<a href="${base}/merchant/return_sale/verify.jhtml">
					<u>退换货认证</u>
				</a>
			</li>
			<li>
				<a href="${base}/merchant/return_sale/list.jhtml">
					<u>退换货列表</u>
				</a>
			</li>
		</ul>
		<li>
			<a class="first">
				<u>销售列表</u>
			</a>
			<ul class="mainfirst">

				<li>
					<a href="${base}/merchant/sale_rank/list.jhtml">
						<u class="redFont" style="font-weight:bold;">提货排行榜</u>
					</a>
				</li>
			</ul>
			
		</li>
		<li>
			<a class="first">
				<u>安全中心</u>
			</a>
			<ul class="mainfirst">
				<#--
				<li>
					<a href="${base}/merchant/user/list.jhtml">
						<u>用户管理</u>
					</a>
				</li>
				-->
				<li>
					<a href="${base}/merchant/profile/edit.jhtml">
						<u>修改密码</u>
					</a>
				</li>
			</ul>
		</li>
	</ul>
</div>
<!--左侧导航结束-->