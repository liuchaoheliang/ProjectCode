(function ($) {
    $.zxyTimerDown = function (optionVal) {
        optionVal = jQuery.extend({
            showId: ""
        }, optionVal || {});

        var timmerDiv = $('#' + optionVal.showId);
        if (timmerDiv == undefined) {
            return;
        }
        if (timmerDiv.attr('endtime') == undefined) {
            return;
        }
        var downTimeStr = timmerDiv.attr('endtime');
        var dateArray = downTimeStr.split(':');
        var day = parseInt(dateArray[0], 10);
        var hour = parseInt(dateArray[1], 10);
        var minute = parseInt(dateArray[2], 10);
        var secound = parseInt(dateArray[3], 10);
        var strDay, strHour, strMinute, StrSecound;
        var interval;
        interval = setInterval(function () {
            strDay = day < 10 ? '0' + day : day;
            strHour = hour < 10 ? '0' + hour : hour;
            strMinute = minute < 10 ? '0' + minute : minute;
            strSecound = secound < 10 ? '0' + secound : secound;
            timmerDiv.html("<span>" + strDay + "</span><em>天</em><span>" + strHour + "</span><em>时</em><span>" + strMinute + "</span><em>分</em><span>" + strSecound + "</span><em>秒</em>");
            secound -= 1;
            if (secound < 0) {
                secound = 59;
                minute -= 1;
                if (minute < 0) {
                    minute = 59;
                    hour -= 1;
                    if (hour < 0) {
                        hour = 23;
                        day -= 1;
                        if (day < 0) {
                            clearInterval(interval);
                            var changeDIV = timmerDiv.parent().parent();
                            changeDIV.empty();
                            changeDIV.html('<div class="leftDiscount redFont f20">预售已结束，正在提货中</div>');
                            return;
                        }
                    }
                }
            }
        }, 1000);
    };
})(jQuery);