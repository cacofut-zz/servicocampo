package br.com.servicocampo.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import br.com.servicocampo.model.Usuario;

@Repository
public class JdbcUsuarioRepositorio implements UsuarioRepositorio {
	
	private static final String ALL_COLUMNS = "id_user, usuario_user, senha_user, ativo_user";
	private static final String SELECT_ALL_USUARIOS = "select "+ ALL_COLUMNS +" from user";
	private static final String SELECT_BY_ID_USUARIO = "select "+ ALL_COLUMNS +" from user where id_user = ?";
	private static final String UPDATE_USUARIO = "update user set usuario_user=?, senha_user=?, ativo_user=? where id_user=?";
	private static final String DELETE_USUARIO = "delete from user where id_user=?";
	private static final String SELECT_USER_BY_USERNAME = "select " + ALL_COLUMNS + " from user where usuario_user = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Usuario> list() {
		System.out.println( System.currentTimeMillis() );
		return jdbcTemplate.query( SELECT_ALL_USUARIOS , new UsuarioRowMapper() );
	}

	public Usuario save(Usuario usuario) {
		long id = insertUsuarioAndReturnId( usuario );
		return new Usuario( id, usuario.getUsuario(), usuario.getSenha(), usuario.isAtivo() );
	}
	
	
	private static final class UsuarioRowMapper implements RowMapper<Usuario>{

		public Usuario mapRow(ResultSet rs, int index ) throws SQLException {
			
			Usuario usuario = new Usuario();
			usuario.setId     ( rs.getLong   ( "id_user" ) );
			usuario.setUsuario( rs.getString ( "usuario_user" ) );
			usuario.setSenha  ( rs.getString ( "senha_user" ) );
			usuario.setAtivo  ( rs.getBoolean( "ativo_user" ) );
			return usuario;
			
		}
			
	}
	
	
	private long insertUsuarioAndReturnId( Usuario usuario ){
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert( jdbcTemplate ).withTableName( "user" );
		jdbcInsert.setGeneratedKeyName( "id_user" );
		Map<String, Object> args = new HashMap<String, Object>();
		args.put( "usuario_user", usuario.getUsuario() );
		args.put( "senha_user",   usuario.getSenha() );
		args.put( "ativo_user",   usuario.isAtivo() );
		long id = jdbcInsert.executeAndReturnKey( args ).longValue();
		return id;
		
	}

	public int delete( Long id ) {
		return jdbcTemplate.update( DELETE_USUARIO, id );
	}

	public Usuario update(Usuario usuario) {
		jdbcTemplate.update( UPDATE_USUARIO, 
			usuario.getUsuario(),
			usuario.getSenha(), 
			usuario.isAtivo(), 
			usuario.getId() );
		return null;
	}

	public Usuario findOne(long id) {
		System.out.println( "findOne()" );
		return jdbcTemplate.queryForObject( SELECT_BY_ID_USUARIO, new UsuarioRowMapper(), id );
	}

	public Usuario findByUserName(String username) {
		System.out.println( "findByUserName()" );
		return jdbcTemplate.queryForObject( SELECT_USER_BY_USERNAME, new UsuarioRowMapper(), username );
	}

	public boolean desativaUsuario(long id) {
		return false;
	}

	public boolean ativaUsuario(long id) {
		return false;
	}




	

}
