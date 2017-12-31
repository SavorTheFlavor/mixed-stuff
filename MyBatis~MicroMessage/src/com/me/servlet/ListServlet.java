package com.me.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.me.bean.Message;
import com.me.entity.Page;
import com.me.service.QueryService;

/**
 * �б�ҳ��ĳ�ʼ���Ͳ�ѯ����
 */
@WebServlet(description = "asf", urlPatterns = { "/ListServlet" })
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//���ñ���
		request.setCharacterEncoding("utf-8");
		//����ҳ���ֵ
		String command = request.getParameter("command");
		String description = request.getParameter("description");
		String currentPage = request.getParameter("currentPage");
		//������ҳ����
		Page page = new Page();
		Pattern pattern = Pattern.compile("[0-9]{1,9}");//У��currentPage�Ƿ����
		if(currentPage == null || !pattern.matcher(currentPage).matches()){
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(Integer.valueOf(currentPage));
		}
		
		
		//��ѯ��Ϣ�б�����ҳ��
		QueryService queryService = new QueryService();
		request.setAttribute("messageList", queryService.queryMessageListByPage(command, description,page));
		//��ҳ�洫ֵ
		request.setAttribute("command", command);
		request.setAttribute("description", description);
		request.setAttribute("page", page);
		//��ҳ����ת
		request.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
