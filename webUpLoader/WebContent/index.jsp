<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file upload</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/webuploader.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/webuploader.js"></script>

<style type="text/css">
	body{
		text-align: center;
	}
</style>

</head>
<body>
		<div class="container">
			<div id="uploader">
				<div id="fileList"></div>
				<div id="filePicker">pick a file</div><br/>
				<button id="fupload" type="button" onclick="fileUpload()" style="width: 100px; height: 80px;" >upload</button>
			</div>
		</div>
		<script type="text/javascript">
			var uploader = WebUploader.create(
			{	swf:"${pageContext.request.contextPath}/js/Uploader.swf",
				server:"${pageContext.request.contextPath}/UploadServlet",
				pick:"#filePicker",
				auto:false
				}
			);//json对象
		
			//实现选择文件，提示文件
			//file:所选文件
			uploader.on("fileQueued",function(file){
				$("#fileList").append("<div id="+file.id+"><img/><span>"+file.name+"</span>&nbsp;<span class='percentage'><span/></div>");
				
				//制作缩略图
				uploader.makeThumb(file,function(error,src){
					if(error){//不是图片，没有缩略图
						$("#"+file.id).find("img").replaceWith("@.@");
					}else{
						$("#"+file.id).find("img").attr("src",src);
					}
				});
				
			});
			
			
			//进度监控
			uploader.on("uploadProgress",function(file,percentage){
				$("#"+file.id).find("span.percentage").text(Math.round(percentage*100)+"%");
			});
			
			function fileUpload(){
				uploader.upload();
			}
			
			
		</script>
</body>
</html>