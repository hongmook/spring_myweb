package com.khm.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khm.common.LoginImpl;
import com.khm.dto.Board;
import com.khm.dto.Criteria;
import com.khm.dto.Page;
import com.khm.service.BoardServiceImp;

@Controller
@RequestMapping(value="/board/")
public class BoardController  {

	@Autowired
	BoardServiceImp boardService;
	
//	@GetMapping("list")
//	@PostMapping("list")
	@RequestMapping(value="list", method= {RequestMethod.POST, RequestMethod.GET})
	public String list(Criteria cri, Model model) {
		
		if(cri.getCurrentPage() == 0) cri.setCurrentPage(1);
		if(cri.getRowPerPage() == 0) cri.setRowPerPage(3);
		
		List<Board> board = boardService.list(cri);
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri)); //레코드 갯수
		model.addAttribute("board", board); 
		
		return "/board/board_list";
		
	}	
	
	@GetMapping("detail")
	public String detail(@ModelAttribute("seqno") String seqno,
						Model model,
						RedirectAttributes rttr) {
		
		model.addAttribute("boardDetail", boardService.searchBoard(seqno));
		
		return "/board/Detail";
	}
	
	@GetMapping("regForm")
	public void regForm() {
		
	}
	
	@PostMapping("register")
	public String register(Board board, 
						   MultipartFile filename, 
						   HttpSession sess,
						   RedirectAttributes rttr) {
		
		board.setId(((LoginImpl)sess.getAttribute("loginUser")).getId());
		String seqno = boardService.insertBoard(board, filename);

		rttr.addFlashAttribute("seqno", seqno);
		
		return "redirect:/board/detail";
	}
	
/*	
			
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
 */		
	
	void goView (HttpServletRequest req, HttpServletResponse resp, String viewPage) throws ServletException, IOException {
	
		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
		rd.forward(req, resp);
		
	}

}
