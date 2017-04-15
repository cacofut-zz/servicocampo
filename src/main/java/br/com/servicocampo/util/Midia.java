package br.com.servicocampo.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import br.com.servicocampo.exception.TamanhoMediaInvalidoException;
import br.com.servicocampo.exception.TipoFormatoInvalidoException;

public abstract class Midia {

	private MultipartFile file;
	private String name;
	private String send;
	private String folder;
	private String baseDir;
	
	public Midia( MultipartFile file ) {
		this( file, null, null, null );
	}
	
	public Midia( MultipartFile file, String nome ) {
		this( file, null, null, nome );
	}
	
	public Midia( MultipartFile file, String folder, String nome ) {
		this( file, null, folder, nome );
	}
	
	public Midia( MultipartFile file, String baseDir, String folder, String nome ) {
	
		if( !(file instanceof MultipartFile) )
			throw new IllegalArgumentException( "Esperado um objeto que implemente a interface MultipartFile" );

		this.file = file;
		setBaseDir(baseDir);
		setFolder(folder);
		setName(nome);		
		
	}

	public abstract String[] getTiposValidos();
	public abstract byte[] getBytes() throws IOException;
	public abstract long getMaxSize();
	public abstract String getDefaultFolder();
	
	public boolean upload() throws IOException, TipoFormatoInvalidoException, TamanhoMediaInvalidoException{
		
		String tipo    = file.getContentType();
		boolean contem = Arrays.asList(getTiposValidos()).contains(tipo);
		boolean sizeValido = file.getSize() <= getMaxSize();
		
		if( !contem )
			throw new TipoFormatoInvalidoException();
		if( !sizeValido )
			throw new TamanhoMediaInvalidoException();
		
		checkFolder(folder);
		
		byte[] buffer  = getBytes();	
		salvar(buffer);
		return true;

	}
	

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	protected void salvar(byte[] bytes) throws IOException {
	
		FileOutputStream fos = new FileOutputStream(baseDir + send + name);
		fos.write(bytes);
		FileDescriptor fd = fos.getFD();
		fos.flush();
		fd.sync();
		fos.close();
		
	}

	public void setBaseDir(String baseDir) {

		if (baseDir == null)
			this.baseDir = "/arquivos/uploads/";
		else
			this.baseDir = baseDir;

		File file = new File( this.baseDir );
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);
		}

	}// fim método

	private void createFolder(String folder) {
		File file = new File(baseDir + folder);
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
		String fileName = name + suffix;
		File file = new File(baseDir + send + fileName);
		if (file.exists())
			fileName = name + "-" + System.currentTimeMillis() + suffix;

		name = fileName;

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
		send = folder + "/" + ano + "/" + mes + "/";
		setFileName();

	}
	
	public void setName(String name) {
		String fileName = file.getOriginalFilename();
		this.name = name != null ? name : fileName.substring(0, fileName.indexOf("."));
	}

	public void setFolder(String folder) {
		this.folder = folder != null ? folder : getDefaultFolder();
	}


	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getName() {
		return name;
	}

	public String getFolder() {
		return folder;
	}

	public String getBaseDir() {
		return baseDir;
	}
	
	@Override
	public String toString() {
		return "Midia [file=" + file + ", name=" + name + ", send=" + send + ", folder=" + folder + ", baseDir="
				+ baseDir + "]";
	}
	
}
