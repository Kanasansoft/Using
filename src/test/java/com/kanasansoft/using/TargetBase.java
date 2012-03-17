package com.kanasansoft.using;

import java.io.Closeable;
import java.io.IOException;

public abstract class TargetBase implements Closeable {

	private int id = -1;

	TargetBase() throws Exception {
		throw new Exception("need id");
	}

	TargetBase(int id) {
		this.id  = id;
	}

	public void run() throws IOException {
		if (!isRunnable()) {
			throw new MyIOException(id, "run");
		}
	}

	public void close() throws IOException {
		if (!isCloseable()) {
			throw new MyIOException(id, "close");
		}
	}

	abstract boolean isRunnable();

	abstract boolean isCloseable();

}
