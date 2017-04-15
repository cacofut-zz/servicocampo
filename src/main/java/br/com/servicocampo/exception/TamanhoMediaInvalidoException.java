package br.com.servicocampo.exception;

public class TamanhoMediaInvalidoException extends RuntimeException{

	public TamanhoMediaInvalidoException() {
		this( "Tamanho da midia invalido" );
	}
	
	public TamanhoMediaInvalidoException( String mensagem ) {
		super( mensagem );
	}
}
