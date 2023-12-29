package com.carto.sn.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
	
	 private byte[] input;
	 
	 public  CustomMultipartFile(byte[] input) {
		 this.input = input;
	 }
	 
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		 return input == null || input.length == 0;
	}

	@Override
	public long getSize() {
		 return input.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return input;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(input);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		try(FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(input);
        }
		
	}

}
