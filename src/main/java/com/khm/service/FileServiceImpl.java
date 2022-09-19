package com.khm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.khm.dao.FileDao;
import com.khm.dto.AttachFile;
import com.khm.dto.Thumbnail;
import net.coobird.thumbnailator.Thumbnails;



public class FileServiceImpl implements FileService {

	FileDao filedao = new FileDao();
	
	@Override
	public AttachFile fileupload(FileItem item) {
		
		AttachFile attachFile = null;
				
		//첨부파일 : 바이너리파일
		long filesize = item.getSize();
		System.out.println("업로드한 파일 사이즈 : " + filesize);
		
		if(filesize > 0) {
			String fileUploadPath = "D:/KHM/upload/";
			String fileName = item.getName();
			
			int idx = fileName.lastIndexOf(".");
			
			String split_fileName = fileName.substring(0,idx);
			String split_extension = fileName.substring(idx+1);
			
			System.out.println(split_fileName);
			System.out.println(split_extension);
			
			/*
			//방법2
			split_fileName = FilenameUtils.getBaseName(fileName);
			split_extension = FilenameUtils.getExtension(fileName);
			*/
			
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			String saveFileName = split_fileName + "_" + uid + "." + split_extension;
			System.out.println("업로드 파일이름 : " + fileName);

			//업로드 파일 저장
			File file = new File(fileUploadPath + saveFileName);
			try {
				item.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			attachFile = new AttachFile();
			attachFile.setFilename(fileName);
			attachFile.setSavefilename(saveFileName);
			attachFile.setFilepath(fileUploadPath);
			attachFile.setFilesize(String.valueOf(filesize));
			attachFile.setFiletype(item.getContentType());
			
			//이미지 파일타입 확인
			String fileType = item.getContentType();
			String type = fileType.substring(0, fileType.indexOf("/"));
			System.out.println("업로드 파일 타입 : " + type);

			if(type.equals("image")) {
				
				attachFile.setThumbnail(setThumbnail(file, saveFileName));
			}
		}
		
		return attachFile;
	}
	
	@Override
	public Thumbnail setThumbnail(File file, String saveFileName) {
		
		//썸네일 파일 저장
		String thumFileName = "thumb_200x200" + saveFileName;
		String thumFilePath = "d:/khm/upload/thumbnail/";
		File thumFile = new File(thumFilePath + thumFileName);
		
		try {
			Thumbnails.of(file).size(200, 200).toFile(thumFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thumbnail thumbNail = new Thumbnail();
		thumbNail.setFilename(thumFileName);
		thumbNail.setFilepath(thumFilePath);
		
		//파일 사이즈 구하기
		thumbNail.setFilesize(String.valueOf(thumFile.length()));
		
		return thumbNail;
	}
	

	@Override
	public void fileDown(HttpServletRequest request, HttpServletResponse response) {

		String savefilename = request.getParameter("savefilename");
		String filename = request.getParameter("filename");
		String filepath = request.getParameter("filepath");
	
		File file = new File(filepath + savefilename);
		
		try {
			InputStream in = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			
			response.reset(); // 이미 열려있는 출력스트림을 비움
			response.setHeader("Cache-Control", "no-cache"); //컴퓨터에있는 파일 말고 새로다운받겠다고 하는 명령어
			response.addHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
			byte[] fileByte = new byte[(int)file.length()];
			
			int readbyte = 0;
			while( (readbyte = in.read(fileByte)) > 0) {
				os.write(fileByte, 0, readbyte);
			}
			
			in.close();
			os.flush();
			os.close();
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
	}

	@Override
	public int delete(String no, String savefilename, String filepath, String thumb_filename) {
		
		int rs = 0;
		//attachfile 레코드 삭제
		filedao.deleteByNo(no);
		
		//파일삭제
		File file = new File(filepath+savefilename);
		
		if(file.exists()) {
			file.delete();
			rs=1;
		}
		
		//썸네일 삭제
		if(thumb_filename != null && rs == 1) {
			
			File thum_file = new File(filepath + "thumbnail/" + thumb_filename);
			if(thum_file.exists()) {
				thum_file.delete();
			}
		}
		return rs;
	}

	@Override
	public AttachFile fileupload(MultipartFile item) {
		
		AttachFile attachFile = null;
				
		//첨부파일 : 바이너리파일
		long filesize = item.getSize();
		System.out.println("업로드한 파일 사이즈 : " + filesize);
		
		if(filesize > 0) {
			String fileUploadPath = "D:/KHM/upload/";
			String fileName = item.getName();
			
			int idx = fileName.lastIndexOf(".");
			
			String split_fileName = fileName.substring(0,idx);
			String split_extension = fileName.substring(idx+1);
			
			System.out.println(split_fileName);
			System.out.println(split_extension);
			
			/*
			//방법2
			split_fileName = FilenameUtils.getBaseName(fileName);
			split_extension = FilenameUtils.getExtension(fileName);
			*/
			
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			String saveFileName = split_fileName + "_" + uid + "." + split_extension;
			System.out.println("업로드 파일이름 : " + fileName);

			//업로드 파일 저장
			File file = new File(fileUploadPath + saveFileName);
			try {
				item.transferTo(file);;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			attachFile = new AttachFile();
			attachFile.setFilename(fileName);
			attachFile.setSavefilename(saveFileName);
			attachFile.setFilepath(fileUploadPath);
			attachFile.setFilesize(String.valueOf(filesize));
			attachFile.setFiletype(item.getContentType());
			
			//이미지 파일타입 확인
			String fileType = item.getContentType();
			String type = fileType.substring(0, fileType.indexOf("/"));
			System.out.println("업로드 파일 타입 : " + type);

			if(type.equals("image")) {
				
				attachFile.setThumbnail(setThumbnail(file, saveFileName));
			}
		}
		
		return attachFile;
	}


}
