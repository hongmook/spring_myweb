package com.khm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.khm.common.LoginImpl;
import com.khm.dao.BoardDao;
import com.khm.dto.AttachFile;
import com.khm.dto.Board;
import com.khm.dto.Criteria;


public class BoardServiceImp implements BoardService {
	BoardDao boardDao = new BoardDao();

	private static final String CHARSET = "utf-8";

	@Override
	public List<Board> list(Criteria cri) {
		return boardDao.boardList(cri);
	}
	
	@Override
	public Board searchBoard(String seqno) {
		return boardDao.boardDetail(seqno);
	}

	@Override
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
	
		factory.setDefaultCharset(CHARSET);
		ServletFileUpload upload = new ServletFileUpload(factory);

		Board board = new Board();

		AttachFile attachFile = null;
		FileService fileService = new FileServiceImpl();
		
		try {
			List<FileItem> items = upload.parseRequest(req);
//			System.out.println("폼에서 넘어온 개수 : " + items.size());
			for(FileItem item : items) {
				if(item.isFormField()) {
					//<input> 태그값
					board = getFormParameter(item, board);
					
//					board.setId((String)req.getSession().getAttribute("sess_id"));
//					System.out.println(board.getId());
					
				} else {
					attachFile = fileService.fileupload(item);
				}
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		LoginImpl login = (LoginImpl)req.getSession().getAttribute("loginUser");
		board.setId(login.getId());
		
		return BoardDao.insert(board, attachFile);
		
	}
	
	@Override
	public String update(HttpServletRequest req, HttpServletResponse resp) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setDefaultCharset(CHARSET);
		ServletFileUpload upload = new ServletFileUpload(factory);

		Board board = new Board();

		AttachFile attachFile = null;
		FileService fileService = new FileServiceImpl();
		
		try {
			List<FileItem> items = upload.parseRequest(req);
//			System.out.println("폼에서 넘어온 개수 : " + items.size());
			for(FileItem item : items) {
				if(item.isFormField()) {
					//<input> 태그값
					board = getFormParameter(item, board);
					
//					board.setId((String)req.getSession().getAttribute("sess_id"));
//					System.out.println(board.getId());
					
				} else {
					attachFile = fileService.fileupload(item);
				}
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
//		board.setId((String)req.getSession().getAttribute("loginUser"));
	
		LoginImpl login = (LoginImpl)req.getSession().getAttribute("loginUser");
		board.setId(login.getId());
		
		boardDao.update(board, attachFile);

		return board.getSeqno();
		
	}
	
	Board getFormParameter(FileItem item, Board board) {
		
		System.out.printf("필드이름 : %s, 필드값 : %s\n", item.getFieldName(), item.getString());
		
		if(item.getFieldName().equals("title")) {
			board.setTitle(item.getString());
		}
		if(item.getFieldName().equals("open")) {
			board.setOpen(item.getString());
		}
		if(item.getFieldName().equals("content")) {
			board.setContent(item.getString());
		}		
		if(item.getFieldName().equals("seqno")) {
			board.setSeqno(item.getString());
		}
		
		return board;
	}
	
	@Override
	public int getTotalRec(Criteria cri) {
		
		return boardDao.getTotalRec(cri);
		
	}
	@Override
	public void delete(String seqno) {

		Map<String, String> map = boardDao.deleteByNo(seqno);
		String savefilename = map.get("savefilename");
		String filepath = map.get("filepath");
		String thumb_filename = map.get("thumb_filename");
		
		if(savefilename != null) {
			//파일삭제
			File file = new File(filepath+savefilename);
			
			if(file.exists()) {
				file.delete();
			}
			
			//썸네일 삭제
			if(thumb_filename != null) {
				
				File thum_file = new File(filepath + "thumbnail/" + thumb_filename);
				if(thum_file.exists()) {
					thum_file.delete();
				}
			}
		}
		
	}

	public void reply(String seqno) {
		
		
		
	}

}
