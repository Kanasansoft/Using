package com.kanasansoft.using;

public class Target extends TargetBase {

	Target(int id) {
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
