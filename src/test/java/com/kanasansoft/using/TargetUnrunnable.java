package com.kanasansoft.using;

public class TargetUnrunnable extends TargetBase {

	TargetUnrunnable(int id) {
		super(id);
	}

	@Override
	boolean isRunnable() {
		return false;
	}

	@Override
	boolean isCloseable() {
		return true;
	}

}
