package com.kanasansoft.using;

public class TargetNormally extends TargetBase {

	TargetNormally(int id) {
		super(id);
	}

	@Override
	boolean isRunnable() {
		return true;
	}

	@Override
	boolean isCloseable() {
		return true;
	}

}
