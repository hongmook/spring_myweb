package com.khm.controller.user;



import java.io.IOException;
import java.util.List;

import javax.management.modelmbean.RequiredModelMBean;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khm.dto.Board;
import com.khm.dto.Criteria;
import com.khm.dto.Page;
import com.khm.service.BoardServiceImp;


@WebServlet(urlPatterns = {"*.bo"})
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public BoardController() {

    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req, resp);
	}

	private void doAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
//		System.out.println("uri = " + uri + "\t uri.lastIndexOf=" + uri.lastIndexOf("/"));
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		
//		System.out.println("cmd="+cmd);
		
		BoardServiceImp boardService = new BoardServiceImp();

		if(cmd.equals("boardList.bo")) {
			String searchfield = req.getParameter("search_field");
			String searchtext = req.getParameter("search_text");
			String currentPage = req.getParameter("currentPage");
			String rowPerPage = req.getParameter("rowPerPage");
			if(currentPage == null) currentPage = "1";
			if(rowPerPage == null) rowPerPage = "3";
			Criteria cri = new Criteria(Integer.parseInt(currentPage), Integer.parseInt(rowPerPage));
			
			cri.setSearchField(searchfield);
			cri.setSearchText(searchtext);
			List<Board> board = boardService.list(cri);
			req.setAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri)); //레코드 갯수
			req.setAttribute("board", board); 
			goView(req, resp, "/board/board_list.jsp");
			
		} else if(cmd.equals("boardDetail.bo")) {
			
			//게시판 세부내용
			
			String seqno = req.getParameter("seqno");
			if(seqno == null) {
				seqno = (String)req.getAttribute("seqno");
			}
			
			Board boardDetail = boardService.searchBoard(seqno);
			req.setAttribute("boardDetail", boardDetail);//(키,값)
			
			String page = req.getParameter("page");
			
			if(page != null) {
			
				goView(req, resp, "/board/modify.jsp");

			} else {
				
				goView(req, resp, "/board/Detail.jsp");

			}
			
		} else if(cmd.equals("boardRegForm.bo")) {
			
			goView(req, resp, "/board/boardCon.jsp");
			
		} else if(cmd.equals("boardCon.bo")) {
			
			req.setAttribute("seqno", boardService.insertBoard(req, resp));
			goView(req, resp, "boardDetail.bo");
			
		} else if(cmd.equals("modify.bo")) {
			
			req.setAttribute("seqno", boardService.update(req, resp));
			goView(req, resp, "boardDetail.bo");
			
		} else if(cmd.equals("boardDelete.bo")) {
			boardService.delete(req.getParameter("seqno"));
			goView(req, resp, "boardList.bo" );
		} else if(cmd.equals("replayProc.bo")) {
			String seqno = req.getParameter("seqno");
			boardService.reply(seqno);
			
		}
		
	}
	
	void goView (HttpServletRequest req, HttpServletResponse resp, String viewPage) throws ServletException, IOException {
	
		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
		rd.forward(req, resp);
		
	}

}
