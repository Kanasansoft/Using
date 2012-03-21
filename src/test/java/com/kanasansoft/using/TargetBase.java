package com.kanasansoft.using;

import java.io.Closeable;
import java.io.IOException;

public abstract class TargetBase implements Closeable {

	enum STATUS {NOT_DONE, DOING, DONE}

	private int id = -1;
	private STATUS statusInitialize = STATUS.NOT_DONE;
	private STATUS statusRun        = STATUS.NOT_DONE;
	private STATUS statusClose      = STATUS.NOT_DONE;

	TargetBase() throws Exception {
		throw new Exception("need id");
	}

	TargetBase(int id) {
		statusInitialize = STATUS.DOING;
		this.id  = id;
		statusInitialize = STATUS.DONE;
	}

	public void run() throws IOException {
		statusRun = STATUS.DOING;
		if (!isRunnable()) {
			throw new MyIOException(id, "run");
		}
		statusRun = STATUS.DONE;
	}

	public void close() throws IOException {
		statusClose = STATUS.DOING;
		if (!isCloseable()) {
			throw new MyIOException(id, "close");
		}
		statusClose = STATUS.DONE;
	}

	abstract boolean isRunnable();

	abstract boolean isCloseable();

	public STATUS getStatesInitialize() {
		return statusInitialize;
	}

	public STATUS getStatesRun() {
		return statusRun;
	}

	public STATUS getStatesClose() {
		return statusClose;
	}

}
