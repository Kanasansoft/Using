package com.kanasansoft.using;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Runnable {

	List<Closeable> closeables = new ArrayList<Closeable>();

	public abstract void run() throws IOException;

	public void register(Closeable closeable) {
		if (closeable == null) {
			throw new IllegalArgumentException("Closeable is null.");
		}
		closeables.add(closeable);
	}

}
