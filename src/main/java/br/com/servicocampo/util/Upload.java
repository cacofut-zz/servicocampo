package br.com.servicocampo.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public abstract class Upload {
	
	private Midia midia;

	private MultipartFile file;
	@Autowired
	private Resizer imgRS;
	private String name;
	private String send;


	private String folder;
	private static String baseDir;

	public Upload() {
		this(null);
	}

	public Upload(String diretorioBase) {
		setBaseDir(diretorioBase);
	}
	

	public void setImgRS(Resizer imgRS) {
		this.imgRS = imgRS;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public void setName(String name) {
		String fileName = this.file.getOriginalFilename();
		this.name = name != null ? name : fileName.substring(0, fileName.indexOf("."));
	}


	public void setSend(String send) {
		this.send = send;
	}


	public void setFolder(String folder) {
		this.folder = folder != null ? folder : "images";
	}

	public String getName() {
		return name;
	}

	public String getSend() {
		return send;
	}
	
	public static String getBaseDir() {
		return baseDir;
	}
	
	public String getCompleteFileName(){
		return Upload.baseDir + this.send + this.name;
	}

	public static void setBaseDir(String baseDir) {

		if (baseDir == null)
			Upload.baseDir = "/arquivos/uploads/";
		else
			Upload.baseDir = baseDir;

		File file = new File(Upload.baseDir);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);
		}

	}// fim método
	
	public boolean upload( MultipartFile file ) throws IOException {	
		return midia.upload();
	}

	public boolean image(MultipartFile file, String name, int width, int height, String folder) throws Exception {
		
		this.file = file;
		setName(name);
		setFolder(folder);
		checkFolder(this.folder);
		return uploadImage();
	}

	private void checkFolder(String folder) {

		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy/MM");
		String[] data = hoje.format(formatador).split("/");
		String ano = data[0];
		String mes = data[1];
		createFolder(folder);
		createFolder(folder + "/" + ano);
		createFolder(folder + "/" + ano + "/" + mes + "/");
		this.send = folder + "/" + ano + "/" + mes + "/";
		setFileName();

	}

	public boolean uploadImage() throws Exception {

		String tipo    = this.file.getContentType();
		String[] tipos = { "image/jpg", "image/jpeg", "image/pjpeg", "image/png", "image/x-png" };
		boolean contem = Arrays.asList(tipos).contains(tipo);

		if ( contem ){
			InputStream is = this.file.getInputStream();
			String file    = this.file.getOriginalFilename();
			//byte[] buffer  = imgRS.read(is, file, this.width, this.height );
			//salvar(buffer);
			salvar(null);
			return true;

		} else {
			return false;
		}

	}

	private void createFolder(String folder) {
		File file = new File(Upload.baseDir + folder);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);
		}
	}

	
	private void setFileName() {
		
		String originalFileName = this.file.getOriginalFilename();
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		String fileName = this.name + suffix;
		File file = new File(Upload.baseDir + this.send + fileName);
		if (file.exists())
			fileName = this.name + "-" + System.currentTimeMillis() + suffix;

		this.name = fileName;

	}
	

	private void ByteToImage(byte[] bytes) throws Exception {
		byte[] imgBytes = bytes;
		try {
			FileOutputStream fos = new FileOutputStream(Upload.baseDir + this.send + this.name );
			fos.write(imgBytes);
			FileDescriptor fd = fos.getFD();
			fos.flush();
			fd.sync();
			fos.close();
		} catch (Exception e) {
			throw new Exception("Erro ao converter os bytes recebidos para imagem");
		}
	}
	
	
	protected void salvar(byte[] bytes) throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(Upload.baseDir + this.send + this.name );
			fos.write(bytes);
			FileDescriptor fd = fos.getFD();
			fos.flush();
			fd.sync();
			fos.close();
		} catch (Exception e) {
			throw new Exception("Erro ao salvar media");
		}
	}
	

	private byte[] imageToByte(String image) throws IOException {
		InputStream is = null;
		byte[] buffer = null;
		is = new FileInputStream(image);
		buffer = new byte[is.available()];
		is.read(buffer);
		is.close();
		return buffer;
	}

	

}
