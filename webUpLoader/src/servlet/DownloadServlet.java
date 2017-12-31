package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = "ooxx.jpg";
		//处理中文乱码问题
		//filename = new String(filename.getBytes(),"ISO8859-1");
		try (InputStream iStream  = this.getServletContext().getResourceAsStream("/Cj7M.jpg");
			OutputStream oStream = response.getOutputStream();){
		
			response.setHeader("content-disposition", "attachment;filename="+filename);
			
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = iStream.read(buf))!=-1){
				oStream.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
