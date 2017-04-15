package br.com.servicocampo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class Video extends Midia{
	
	private final long MAX_SIZE = 10485760; // 10 megabytes
	private final String DEFAULT_FOLDER = "videos";
	
	public Video( MultipartFile file ) {
		super( file );
	}
	
	public Video( MultipartFile file, String nome ) {
		super( file, nome );
	}
	
	public Video( MultipartFile file, String folder, String nome ) {
		super( file, folder, nome );
	}
	
	public Video( MultipartFile file, String baseDir, String folder, String nome ) {
		super( file, baseDir, folder, nome );
	}

	private static final String[] TIPOS_VIDEOS_PERMITIDOS = { 
		"audio/basic", 
		"audio/aiff", 
		"audio/x-wav", 
		"video/quicktime",
		"video/mpeg", 
		"video/x-msvideo",
		"video/mp4"
	};


	@Override
	public String[] getTiposValidos() {
		return TIPOS_VIDEOS_PERMITIDOS;
	}

	@Override
	public byte[] getBytes() throws IOException {
		InputStream input = getFile().getInputStream();	
		byte[] bytes = IOUtils.toByteArray( input );
		return bytes;
	}

	@Override
	public long getMaxSize() {
		return MAX_SIZE;
	}

	@Override
	public String toString() {
		return super.toString() + " Video [MAX_SIZE=" + MAX_SIZE + "]";
	}
 
	@Override
	public String getDefaultFolder() {
		return DEFAULT_FOLDER;
	}
}
