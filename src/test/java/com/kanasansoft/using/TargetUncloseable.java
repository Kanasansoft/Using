package com.kanasansoft.using;

public class TargetUncloseable extends TargetBase {

	TargetUncloseable(int id) {
		super(id);
	}

	@Override
	boolean isRunnable() {
		return true;
	}

	@Override
	boolean isCloseable() {
		return false;
	}

}
