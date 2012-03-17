package com.kanasansoft.using;

public class TargetUnrunnableUncloseable extends TargetBase {

	TargetUnrunnableUncloseable(int id) {
		super(id);
	}

	@Override
	boolean isRunnable() {
		return false;
	}

	@Override
	boolean isCloseable() {
		return false;
	}

}
