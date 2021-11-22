package de.ninafoss.domain.usecases;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.exception.FatalBackendException;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
class CopyData {

	private static final int EOF = -1;
	private final InputStream source;
	private final OutputStream target;

	public CopyData(@Parameter InputStream source, @Parameter OutputStream target) {
		this.source = source;
		this.target = target;
	}

	public void execute() throws BackendException {
		try {
			byte[] buffer = new byte[4096];
			int read = 0;
			while (read != EOF) {
				read = source.read(buffer);
				if (read > 0) {
					target.write(buffer, 0, read);
				}
			}
		} catch (IOException e) {
			throw new FatalBackendException(e);
		}
	}

}
