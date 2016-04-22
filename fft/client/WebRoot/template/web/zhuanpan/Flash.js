function loadFlash(stopNum){
	flashStr = "<object classid=clsid:d27cdb6e-ae6d-11cf-96b8-444553540000 codebase=http:\/\/fpdownload.macromedia.com\/pub\/shockwave\/cabs\/flash\/swflash.cab#version=8,0,0,0 width=510 height=510 id=flashvars>"+
			   "  <param name=movie value=\"template\/web\/zhuanpan\/flash.swf\" >"+
			   "  <param name=FlashVars value=\"stopNum="+ stopNum +"&autoPlay="+ autoPlay +"&setPointer="+ setPointer +"&url_bg="+ url_bg +"&url_prize="+ url_prize +"&url_pointer="+ url_pointer +"&url_btnStart="+ url_btnStart +"&url_btnOver="+ url_btnOver +"&url_btnWait="+ url_btnWait +"&url_btnSuc="+ url_btnSuc +"&W_bg="+ W_bg +"&W_btn="+ W_btn +"&W_pointer="+ W_pointer +"&H_pointer="+ H_pointer +"&totleNum="+ totleNum +"&turns="+ turns +"\" \/>"+
			   "  <param name=quality value=high \/>"+
			   "  <param name=wmode value=transparent \/>"+
			   "  <embed src=\"template\/web\/zhuanpan\/flash.swf\" FlashVars=\"stopNum="+ stopNum +"&autoPlay="+ autoPlay +"&setPointer="+ setPointer +"&url_bg="+ url_bg +"&url_prize="+ url_prize +"&url_pointer="+ url_pointer +"&url_btnStart="+ url_btnStart +"&url_btnOver="+ url_btnOver +"&url_btnWait="+ url_btnWait +"&url_btnSuc="+ url_btnSuc +"&W_bg="+ W_bg +"&W_btn="+ W_btn +"&W_pointer="+ W_pointer +"&H_pointer="+ H_pointer +"&totleNum="+ totleNum +"&turns="+ turns +"\" quality=high wmode=transparent width=510 height=510 name=flashvars type=application\/x-shockwave-flash pluginspage=http:\/\/www.macromedia.com\/go\/getflashplayer \/>"+
			   "<\/object>"+
			   "<style type=\"text\/css\">:focus {outline:0;}<\/style>"
	document.getElementById(flashHoler).innerHTML = flashStr;
};loadFlash(stopNum);