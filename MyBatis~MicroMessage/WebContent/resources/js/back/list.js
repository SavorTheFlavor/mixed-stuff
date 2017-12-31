/**
 * 调用后台删除一条信息的方法
 */
function deleteOne(id){
	window.location.href=$("#basePath").val()+"DeleteOneServlet.action?id="+id;
}

/**
 * 调用后台批量删除方法
 */
function deleteBatch(){
	$("#mainForm").attr("action",$("#basePath").val()+"DeleteBatchServlet.action");
	$("#mainForm").submit();
}
/**
* 提示输入一条消息
*/
function addOneMessage(){
	var message = prompt("请输入消息的指令名称、描述、及内容。以一个空格作为分割....","");
	window.location.href=$("#basePath").val()+"InsertOneServlet.action?newMessage="+message;
}

/**
 * 改版新增
 */
function forAdd(){
	$(".add").toggle(777);
}
function addOrDelete(){
	var flag = $("#flag").html();
	if(flag == "+"){
		$("#content").append("<input name='newContent' value='haha'/>");
		$("#flag").html("-");
		$("#content").append("	<tr>"+
								"<td>2</td>"+
								"<td id='content2'></td>"+
								"<td><a href='javascript:addOrDelete()' id='flag2'>"+"+"+"</a></td>"+
							"</tr>");
	}else{
		$("#content").empty();
		$("#flag").html("+");
	}
}

/**
 * 改变当前页数
 */
function changeCurrentPage(currentPage){
	$("#currentPage").val(currentPage);
	$("#mainForm").submit();
}