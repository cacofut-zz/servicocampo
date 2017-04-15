package br.com.servicocampo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class Arquivo extends Midia{
	
	private final long MAX_SIZE = 5242880; // 5 megabytes
	private final String DEFAULT_FOLDER = "arquivos";
	
	private static final String[] TIPOS_ARQUIVOS_PERMITIDOS = { 
		"application/pdf", 
		"application/x-mobipocket-ebook", 
		"application/epub+zip", 
		"application/zip"
	};

	public Arquivo( MultipartFile file ) {
		super( file );
	}
	
	public Arquivo( MultipartFile file, String nome ) {
		super( file, nome );
	}
	
	public Arquivo( MultipartFile file, String folder, String nome ) {
		super( file, folder, nome );
	}
	
	public Arquivo( MultipartFile file, String baseDir, String folder, String nome ) {
		super( file, baseDir, folder, nome );
	}


	@Override
	public String[] getTiposValidos() {
		return TIPOS_ARQUIVOS_PERMITIDOS;
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
		return super.toString() + " Arquivo [MAX_SIZE=" + MAX_SIZE + "]";
	}

	@Override
	public String getDefaultFolder() {
		return DEFAULT_FOLDER;
	}

}
