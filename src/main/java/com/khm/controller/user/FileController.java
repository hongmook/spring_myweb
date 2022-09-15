package com.khm.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khm.service.FileService;
import com.khm.service.FileServiceImpl;


@WebServlet("/file/*")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	FileService fileservice = new FileServiceImpl();
	
    public FileController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) {

		
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		
		switch(cmd) {
			case "fileDown" :
				fileservice.fileDown(request, response);
				
				break;
			case "filedel" :
				String no = request.getParameter("no");
				String savefilename = request.getParameter("savefilename");
				String filepath = request.getParameter("filepath");
				String thumb_filename = request.getParameter("thumb_filename");
				
				int rs = fileservice.delete(no, savefilename, filepath, thumb_filename);
				
				System.out.println("파일삭제결과 : "+rs);

			PrintWriter out;
			try {
				
				out = response.getWriter();
				out.print(rs);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
				
			default :
		}
	}

}
