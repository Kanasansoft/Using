package com.kanasansoft.using;

import java.io.IOException;

public class MyIOException extends IOException {

	private static final long serialVersionUID = 1L;

	private int id = -1;
	private String place = null;

	private MyIOException() {
		super();
	}

	private MyIOException(String message, Throwable cause) {
		super(message, cause);
	}

	private MyIOException(String message) {
		super(message);
	}

	private MyIOException(Throwable cause) {
		super(cause);
	}

	MyIOException(int id,String place) {
		super();
		this.id    = id;
		this.place = place;
	}

	MyIOException(int id,String place, String message, Throwable cause) {
		super(message, cause);
		this.id    = id;
		this.place = place;
	}

	MyIOException(int id,String place, String message) {
		super(message);
		this.id    = id;
		this.place = place;
	}

	MyIOException(int id,String place, Throwable cause) {
		super(cause);
		this.id    = id;
		this.place = place;
	}

	int getId() {
		return id;
	}

	String getPlace() {
		return place;
	}

}
