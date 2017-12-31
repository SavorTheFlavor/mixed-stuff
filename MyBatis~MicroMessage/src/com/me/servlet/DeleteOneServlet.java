package com.me.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.me.service.MaintainService;

public class DeleteOneServlet extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ñ���
		request.setCharacterEncoding("utf-8");
		//����ҳ���ֵ
		String id = request.getParameter("id");
		//��ҳ�洫ֵ
		request.setAttribute("id", id);
		MaintainService maintainService = new MaintainService();
		maintainService.deleteOne(id);
		String succeedDeleteMsg = "������Ϣ��ɾ����";
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
