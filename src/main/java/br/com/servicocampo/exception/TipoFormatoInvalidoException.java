package br.com.servicocampo.exception;

public class TipoFormatoInvalidoException extends RuntimeException{

	public TipoFormatoInvalidoException() {
		this( "Tipo de formato não suportado" );
	}
	
	public TipoFormatoInvalidoException( String mensagem ) {
		super( mensagem );
	}
}
