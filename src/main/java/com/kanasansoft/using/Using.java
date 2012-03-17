package com.kanasansoft.using;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Using {

	static public IOException[] execute (Runnable runnable) {

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable is null.");
		}

		List<IOException> exceptions = new ArrayList<IOException>();

		try {
			runnable.run();
		} catch (IOException e) {
			exceptions.add(e);
		} finally {
			exceptions.addAll(closeAll(runnable.closeables));
		}

		return exceptions.toArray(new IOException[]{});

	}

	private static List<IOException> closeAll(List<? extends Closeable> closeables) {

		ArrayList<IOException> exceptions = new ArrayList<IOException>();

		if (closeables.isEmpty()) {
			return exceptions;
		}

		Closeable closeable = closeables.get(0);

		try {
			closeable.close();
		} catch (IOException e) {
			exceptions.add(e);
		} finally {
			exceptions.addAll(closeAll(closeables.subList(1, closeables.size())));
		}

		return exceptions;

	}

}
