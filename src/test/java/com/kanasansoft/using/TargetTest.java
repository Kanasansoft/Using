package com.kanasansoft.using;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.junit.Test;

import com.kanasansoft.using.TargetBase.STATUS;

/**
 * テスト用オブジェクトのテスト
 */
public class TargetTest {

	class CheckStatus extends HashMap<TYPE,STATUS> {
		private static final long serialVersionUID = 1L;
	}

	private enum TYPE {INITIALIZE, RUN, CLOSE};

	@Test
	public void 普通のターゲット() throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		check(TargetNormally.class, 1,
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DONE    ,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DONE    ,STATUS.DONE    ),
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.DONE    )
		);
	}

	@Test
	public void 実行できないターゲット() throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		check(TargetUnrunnable.class, 1,
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DOING   ,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DOING   ,STATUS.DONE    ),
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.DONE    )
		);
	}

	@Test
	public void クローズできないターゲット() throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		check(TargetUncloseable.class, 1,
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DONE    ,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DONE    ,STATUS.DOING   ),
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.DOING   )
		);
	}

	@Test
	public void 実行もクローズもできないターゲット() throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		check(TargetUnrunnableUncloseable.class, 1,
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DOING   ,STATUS.NOT_DONE),
				getStatus(STATUS.DONE,STATUS.DOING   ,STATUS.DOING   ),
				getStatus(STATUS.DONE,STATUS.NOT_DONE,STATUS.DOING   )
		);
	}

	private CheckStatus getStatus(final STATUS initialize, final STATUS run, final STATUS close) {
		return new CheckStatus(){
			private static final long serialVersionUID = 1L;
		{
			put(TYPE.INITIALIZE, initialize);
			put(TYPE.RUN, run);
			put(TYPE.CLOSE, close);
			}};
	}

	private void check(
			Class<? extends TargetBase> clazz,
			int id,
			CheckStatus checkInitialized,
			CheckStatus checkExecuted,
			CheckStatus checkClosed,
			CheckStatus checkClosedOnly
			) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

		Constructor<? extends TargetBase> constructor = clazz.getDeclaredConstructor(int.class);
		Object[] args = {Integer.valueOf(id)};

		{
			TargetBase target = constructor.newInstance(args);
			checkStatus(target, checkInitialized);
			try {
				target.run();
			} catch (IOException e) {
			}
			checkStatus(target, checkExecuted);
			try {
				target.close();
			} catch (IOException e) {
			}
			checkStatus(target, checkClosed);
		}

		{
			TargetBase target = constructor.newInstance(args);
			checkStatus(target, checkInitialized);
			try {
				target.close();
			} catch (IOException e) {
			}
			checkStatus(target, checkClosedOnly);
		}

	}

	private void checkStatus(TargetBase target, CheckStatus status) {
		assertThat(target.getStatesInitialize(), is(status.get(TYPE.INITIALIZE)));
		assertThat(target.getStatesRun(),        is(status.get(TYPE.RUN       )));
		assertThat(target.getStatesClose(),      is(status.get(TYPE.CLOSE     )));
	}
}
