<div class="menu100">
    <div class="menu">
        <div class="leftLiMenu" id="menuAClick">
            <li><a href="index.action">首页</a></li>
            <li><a href="presell_index.action">精品预售</a></li>
            <li><a href="new_exchange_index.action">积分兑换</a></li>
            <li><a href="queryBeforeExch.action">积分提现</a></li>
            <li><a href="group_index.action">团购</a></li>
            <li><a href="rebate_index.action">积分返利</a></li>
            <li><a href="preferential_index.action">直接优惠</a></li>
        </div>
        <div class="leftLiMenu linkWhite" style="margin-top:8px">
        <span>
			<a style="display:none;" id="jhjfbtn" href="bankPoint_activate.action"><img src="${base}/template/web/images/jhjfbtn.png"></a></span>
		<span><a style="display:none;" href="toResgiter.action" id="registerBut"><img src="${base}/template/web/images/regeditindex.png"></a></span>
        </div>
        <div class="searchall">
            <div class="rightSearch">
            <input type="text" id="searchWord" class="headSearch" value="请输入商家名称" maxlength="20" style="color:#999"></div>
            <div class="rightBtn"><a href="javascript:search();"><img
                    src="${base}/template/common/images/sousuoBtn.png"></a></div>

            <!--	 <div class="hiddensearch" style="display:none" id="hiddensearch">
                </div> -->

        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/menu.js"></script>
<script type="text/javascript" src="${base}/template/common/js/headsearch.js"></script>
<script type="text/javascript">
    // $(window).load(function(){
    //getHotSearchesList();
    // });
    function getHotSearchesList() {
        $.getJSON("${base}/findHotSearchesList.action", function (data) {
            var $hiddensearch = $("#hiddensearch");
            var html = '';
            var districtHtml = '';
            var classifyHtml = '';
            if (data.reno == "0") {
                $.each(data.hotSearchesList, function (key, value) {
                    if (key == 0) {
                        districtHtml += '<li class="font01"><a href="searchIndex.action?pager.tagDistrictBId=' + value.tagDistrictBId + '">' + value.tagDistrictBValue + '</a></li>';
                        classifyHtml += '<li class="font02"><a href="searchIndex.action?pager.tagClassifyAId=' + value.tagClassifyAId + '">' + value.tagClassifyAValue + '</a></li>';
                    }
                    if (key == 1) {
                        districtHtml += '<li class="font03"><a href="searchIndex.action?pager.tagDistrictBId=' + value.tagDistrictBId + '">' + value.tagDistrictBValue + '</a></li>';
                        classifyHtml += '<li class="font04"><a href="searchIndex.action?pager.tagClassifyAId=' + value.tagClassifyAId + '">' + value.tagClassifyAValue + '</a></li>';
                    }
                    if (key == 2) {
                        districtHtml += '<li class="font05"><a href="searchIndex.action?pager.tagDistrictBId=' + value.tagDistrictBId + '">' + value.tagDistrictBValue + '</a></li>';
                        classifyHtml += '<li class="font06"><a href="searchIndex.action?pager.tagClassifyAId=' + value.tagClassifyAId + '">' + value.tagClassifyAValue + '</a></li>';
                    }
                    if (key == 3) {
                        districtHtml += '<li class="font07"><a href="searchIndex.action?pager.tagDistrictBId=' + value.tagDistrictBId + '">' + value.tagDistrictBValue + '</a></li>';
                        classifyHtml += '<li class="font08"><a href="searchIndex.action?pager.tagClassifyAId=' + value.tagClassifyAId + '">' + value.tagClassifyAValue + '</a></li>';
                    }
                    if (key == 4) {
                        districtHtml += '<li class="font09"><a href="searchIndex.action?pager.tagDistrictBId=' + value.tagDistrictBId + '">' + value.tagDistrictBValue + '</a></li>';
                        classifyHtml += '<li class="font10"><a href="searchIndex.action?pager.tagClassifyAId=' + value.tagClassifyAId + '">' + value.tagClassifyAValue + '</a></li>';
                    }
                });
                html = districtHtml + classifyHtml;
                $hiddensearch.html(html);
                $('#hiddensearch').hide();
            } else {
                $hiddensearch.html('');
                $('#hiddensearch').hide();
            }
        });
    }
</script>


<script type="text/javascript">

    $(document).ready(function () {
        $("#searchWord").keydown(function (e) {
            e = window.event || e;
            if (e.keyCode == 13) {
                search();
            }
        })
    })
</script>

<script type="text/javascript" src="${base}/template/common/js/selectMenu.js"></script>
