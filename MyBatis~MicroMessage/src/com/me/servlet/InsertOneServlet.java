package com.me.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.me.bean.Message;
import com.me.service.MaintainService;

/**
 * Servlet implementation class InsertOneServlet
 */
@WebServlet("/InsertOneServlet")
public class InsertOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//接受页面的值
		String message = request.getParameter("newMessage");
		String currentPage = request.getParameter("currentPage"); 
		//通过href传参时防止中文乱码！
		message= new String(message.getBytes("ISO8859-1"),"UTF-8");
		
		MaintainService maintainService = new MaintainService();
		maintainService.insertOne(message);
		response.sendRedirect("index.jsp?currentPage="+currentPage);
//		request.getRequestDispatcher("/List.action").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
