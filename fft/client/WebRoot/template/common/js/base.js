/**
 * FQ
 * 
 */
// 添加收藏夹
function addFavorite(url, title) {
	if (document.all) {
		window.external.addFavorite(url, title);
	} else if (window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	}
}



/**
 * lqp
 * 
 */

function   escapeHtml(str) { 	
	if(str.length == 0){
    	return   "";
    } 
	return str.replace(/%/g,'').replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/\"/g,'&quot;').replace(/ /g,'&nbsp;').replace(/\'/g,'&#39;').replace(/\n/g,'<br>');
}  

function unEscapeHtml(str){
	return str.replace(/&amp;/g,'&').replace(/&gt;/g,'>').replace(/&lt;/g,'<').replace(/&quot;/g,'"').replace(/&nbsp;/g,' ').replace(/&#39;/g,'\'').replace(/<br>/g,'\n');
}
