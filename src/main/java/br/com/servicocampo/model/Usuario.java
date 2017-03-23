package br.com.servicocampo.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 6353383633015336357L;
	
	private long id;
	
	@NotNull
	@Size( min = 5, max = 30 )
	private String usuario;
	
	@NotNull
	@Size( min = 5, max = 20 )
	private String senha;
	
	
	private boolean ativo;
	
		
	public Usuario(long id, String usuario, String senha, boolean ativo) {
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.ativo = ativo;
	}
		
	public Usuario(String usuario, String senha, boolean ativo) {
		this.usuario = usuario;
		this.senha = senha;
		this.ativo = ativo;
	}

	public Usuario() {

	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", senha=" + senha + ", ativo=" + ativo + "]";
	}
		
	
	
	

}
