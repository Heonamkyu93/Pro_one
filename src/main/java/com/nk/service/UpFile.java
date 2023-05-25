package com.nk.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@MultipartConfig(
		// location = "/tmp", //절대경로
		fileSizeThreshold = 1024 * 1024, // 1메가가 넘으면 메모리대신 디스크사용
		maxFileSize = 1024 * 1024 * 10, // 파일1개의 최대크기
		maxRequestSize = 1024 * 1024 * 10 * 5 // 모든요청의 최대크기
)
public class UpFile {
	HttpServletRequest request;
	HttpServletResponse response;
	public UpFile(HttpServletRequest request, HttpServletResponse response) {
		this.request =request;
		this.response=response;
	}

	public void insertFile() {
			String upPath = request.getSession().getServletContext().getRealPath("upload");
			int size=10*1024*1024;
			try {
				MultipartRequest multi = new MultipartRequest(request, upPath,size,"utf-8",new DefaultFileRenamePolicy());
				multi.getParameter("te");
				multi.getParameter("te2");
				multi.getFilesystemName("file");
				multi.getOriginalFileName("file");
				multi.getContentType("file");

			
				File f = new File(upPath);
				if(!f.isDirectory()) {
					System.out.println("폴더없음");
					f.mkdir();
				}
				
				System.out.println(upPath);
				
				
				System.out.println("파일타입="+multi.getContentType("file"));
				System.out.println(	"파일이름="+multi.getFilesystemName("file"));
				System.out.println("오리지날="+multi.getOriginalFileName("file"));
				System.out.println("오리지날2="+multi.getOriginalFileName("file2"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		
	}
}
