/*
* Author:
* pengling@f-road.com.cn 
*/
//头部搜索

//得到焦点时触发事件
function OnFocusFun(element,elementvalue)
{
//document.getElementById("hiddensearch").style.display = "block"	

document.getElementById("searchWord").className = "headSearch02"
if(element.value==elementvalue)
{
element.value="";
element.style.color="#999";
}
}
//离开输入框时触发事件
function OnBlurFun(element,elementvalue)
{
//document.getElementById("hiddensearch").style.display = "block"	

document.getElementById("searchWord").className = "headSearch"

if(element.value==""||element.value.replace(/\s/g,"")=="")
{
element.value=elementvalue;
element.style.color="#999";
}
}

function  Checkbtn()
{  

  if  (document.getElementById("searchWord").value  ==  "请输入商家名称")  {  
    return false;
  }
  return  true;
  } 
