package com.khm.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.khm.dto.AttachFile;
import com.khm.dto.Thumbnail;


public interface FileService {
	
	//�꽌釉붾┸
	public AttachFile fileupload(FileItem item);

	//�뒪�봽留�
	public AttachFile fileupload(MultipartFile item);

	public void fileDown(HttpServletRequest request, HttpServletResponse response);

	public int delete(String no, String savefilename, String filepath, String thumb_filename);

	Thumbnail setThumbnail(File file, String saveFileName); 

}


