package com.nk.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class File {
	HttpServletRequest request;
	HttpServletResponse response;	
	
	public File(HttpServletRequest request,HttpServletResponse response) {
		this.request=request;
		this.response=response;
	}
	
	
	
	public void insertFile() {
		String file="C:\\private_work\\Pro_one\\src\\main\\file";
		try {
			MultipartRequest mr = new MultipartRequest(request,file);
			
			Enumeration<?> em = mr.getFileNames();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
