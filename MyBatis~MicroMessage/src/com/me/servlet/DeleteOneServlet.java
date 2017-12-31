package com.me.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.me.service.MaintainService;

public class DeleteOneServlet extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置编码
		request.setCharacterEncoding("utf-8");
		//接受页面的值
		String id = request.getParameter("id");
		//向页面传值
		request.setAttribute("id", id);
		MaintainService maintainService = new MaintainService();
		maintainService.deleteOne(id);
		String succeedDeleteMsg = "此条消息已删除！";
		request.setAttribute("succeedDeleteMsg", succeedDeleteMsg);
		request.getRequestDispatcher("/List.action").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
