package br.com.servicocampo.testes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.servicocampo.config.RootConfig;
import br.com.servicocampo.model.Usuario;
import br.com.servicocampo.model.dao.UsuarioRepositorio;
import br.com.servicocampo.util.Arquivo;
import br.com.servicocampo.util.ImageResizerService;
import br.com.servicocampo.util.Imagem;
import br.com.servicocampo.util.Midia;
import br.com.servicocampo.util.Resizer;
import br.com.servicocampo.util.Upload;
import br.com.servicocampo.util.Video;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("development")
public class UsuarioRepositorioTest {

	@Autowired
	private UsuarioRepositorio usarioRepositorio;

	/**
	 * @throws Exception
	 */
	@Test
	// @Transactional
	public void listarTodos() throws Exception {
		
		Resizer rs = new ImageResizerService();
		File file1 = new File( "/arquivos/spring/10-hibernate-dev-process.mp4" );
		File file2 = new File( "/home/cristiano/Downloads/37835.jpg" );
		File file3 = new File( "/home/cristiano/Downloads/spring-mvc-domine-o-principal-framework-web-java.pdf" );
		
		FileInputStream input1 = new FileInputStream(file1);
		MultipartFile multipartFile1 = new MockMultipartFile("file", file1.getName(), "video/mp4", IOUtils.toByteArray( input1 ));

		FileInputStream input2 = new FileInputStream(file2);
		MultipartFile multipartFile2 = new MockMultipartFile("file2", file2.getName(), "image/jpeg", IOUtils.toByteArray( input2 ));
		
		FileInputStream input3 = new FileInputStream(file3);
		MultipartFile multipartFile3 = new MockMultipartFile("file3", file3.getName(), "application/pdf", IOUtils.toByteArray( input3 ));
		
		System.out.println( multipartFile3.getContentType() );
		System.out.println( multipartFile3.getSize() );
		
		Midia midia1 = new Video(multipartFile1 );
		Midia midia2 = new Imagem(multipartFile2, rs );
		Midia midia3 = new Arquivo(multipartFile3 );
		midia1.upload();
		midia2.upload();
		midia3.upload();
		
		System.out.println( midia1 );
		System.out.println( midia2 );
		System.out.println( midia3 );
		/*
		List<Usuario> lista = usarioRepositorio.list();
		for (Usuario usuario : lista) {
			System.out.println(usuario);

		}
		*/
		
		
		
		
		
	}

	
	
	

}
