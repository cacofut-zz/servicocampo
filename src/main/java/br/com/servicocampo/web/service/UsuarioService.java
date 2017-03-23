package br.com.servicocampo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servicocampo.model.Usuario;
import br.com.servicocampo.model.dao.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public Usuario save( Usuario usuario ){
		return usuarioRepositorio.save( usuario );
	}

	public List<Usuario> list() {
		return usuarioRepositorio.list();
	}

	public int delete(Long id) {
		return usuarioRepositorio.delete( id );
	}

	public Usuario findOne(Long id) {
		return usuarioRepositorio.findOne( id );
	}
}
