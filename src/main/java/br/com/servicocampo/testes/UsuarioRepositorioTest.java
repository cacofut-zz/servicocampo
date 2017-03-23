package br.com.servicocampo.testes;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.servicocampo.config.RootConfig;
import br.com.servicocampo.model.Usuario;
import br.com.servicocampo.model.dao.UsuarioRepositorio;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("development")
public class UsuarioRepositorioTest {

	@Autowired
	private UsuarioRepositorio usarioRepositorio;

	@Test
	// @Transactional
	public void listarTodos() {

		List<Usuario> lista = usarioRepositorio.list();
		for (Usuario usuario : lista) {
			System.out.println(usuario);

		}

	}

}
