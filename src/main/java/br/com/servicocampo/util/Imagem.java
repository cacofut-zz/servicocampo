package br.com.servicocampo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class Imagem extends Midia{

	private final long MAX_SIZE = 3145728; // 3 megabytes
	private final String DEFAULT_FOLDER = "imagens";
	
	private Resizer imgRS;
	private double width;
	private double height;
	private static final int VALUE_MAX = 1024;
	private static final String[] TIPOS_IMAGENS_PERMITIDOS = { 
		"image/jpg", 
		"image/jpeg", 
		"image/pjpeg", 
		"image/png", 
		"image/x-png" 
	};
	
	public Imagem(MultipartFile file, Resizer imgRS) {
		this( file, imgRS, null, null, null, VALUE_MAX, VALUE_MAX );
	}
	
	public Imagem( MultipartFile file,Resizer imgRS, String basePath ) {
		this( file, imgRS, basePath, null, null, VALUE_MAX, VALUE_MAX );
	}
	
	public Imagem( MultipartFile file,Resizer imgRS, String basePath, String folder ) {
		this( file, imgRS, basePath, folder, null, VALUE_MAX, VALUE_MAX );
	}
	
	public Imagem( MultipartFile file,Resizer imgRS, String basePath, String folder, String nome ) {
		this( file, imgRS, basePath, folder, nome, VALUE_MAX, VALUE_MAX );
	}
	
	public Imagem( MultipartFile file,Resizer imgRS, int width, int height ) {
		this( file, imgRS, null, null, null, width, height );
	}
	
	public Imagem( MultipartFile file, Resizer imgRS, String nome, int width, int height ) {
		this( file, imgRS, null, null, nome, width, height );
	}
	
	public Imagem( MultipartFile file, Resizer imgRS, String folder, String nome, int width, int height ) {
		this( file, imgRS, null, folder, nome, width, height );
	}
	
	public Imagem( MultipartFile file, Resizer imgRS, String baseDir, String folder, String nome, int width, int height ) {
		super( file, baseDir, folder, nome );		
		if( !(imgRS instanceof Resizer) )
			throw new IllegalArgumentException( "Esperado um objeto que implemente a interface Resizer" );
		
		setWidth( width );
		setHeight( height );
		this.imgRS = imgRS;
	}
	
	public Resizer getImgRS() {
		return imgRS;
	}

	public void setImgRS(Resizer imgRS) {
		this.imgRS = imgRS;
	}

	public double getWidth() {
		return width;
	}

	
	public void setWidth(int width) {
		this.width = width >= 0 ? width : VALUE_MAX;		
	}

	public void setHeight(int height) {
		this.height = height >= 0 ? height : VALUE_MAX;
	}

	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return super.toString() + " Imagem [imgRS=" + imgRS + ", width=" + width + ", height=" + height + "]";
	}

	@Override
	public String[] getTiposValidos() {
		return TIPOS_IMAGENS_PERMITIDOS;
	}

	@Override
	public byte[] getBytes() throws IOException {	
		
		InputStream is = getFile().getInputStream();
		String file    = getFile().getOriginalFilename();
		byte[] buffer  = imgRS.read(is, file, this.width, this.height );
		return buffer;
		
	}

	@Override
	public long getMaxSize() {
		return MAX_SIZE;
	}
	
	@Override
	public String getDefaultFolder() {
		return DEFAULT_FOLDER;
	}
	
	

}
