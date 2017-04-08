package br.com.servicocampo.util;

import java.io.IOException;
import java.io.InputStream;

public interface Resizer {

	byte[] read( InputStream arquivo, String file,  double requiredWidth, double requiredHeight) throws IOException;
	byte[] read(InputStream file) throws IOException;
}
