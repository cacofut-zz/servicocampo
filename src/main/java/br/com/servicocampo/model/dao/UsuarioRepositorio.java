package br.com.servicocampo.model.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import br.com.servicocampo.model.Usuario;

public interface UsuarioRepositorio extends Repositorio<Usuario>{

	public Usuario findByUserName( String username );	
	public boolean desativaUsuario( long id );
	public boolean ativaUsuario( long id );
	
}
