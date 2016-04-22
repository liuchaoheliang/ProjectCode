 function AutoScroll(obj) {

            $(obj).find("ul:first").animate({
                marginTop: "-25px"
            }, 1200, function() {
                $(this).css({ marginTop: "0px" }).find("li:first").appendTo(this);
            });
        }
        $(document).ready(function() {
            var myar = setInterval('AutoScroll("#scrollDiv")', 1500)
            $("#scrollDiv").hover(function() { clearInterval(myar); }, function() { myar = setInterval('AutoScroll("#scrollDiv")', 1500) }); //当鼠标放上去的时候，滚动停止，鼠标离开的时候滚动开始
        });