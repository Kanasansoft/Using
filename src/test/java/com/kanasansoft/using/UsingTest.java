package com.kanasansoft.using;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class UsingTest {

	@Test
	public void 普通の処理() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetNormally t1 = new TargetNormally(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				t1.run();
				t2.run();
			}
		});
		assertThat(es.length, is(0));
	}

	@Test
	public void 外部クラスの変数を処理() {
		final TargetNormally t1 = new TargetNormally(1);
		final TargetNormally t2 = new TargetNormally(2);
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				register(t1);
				register(t2);
				t1.run();
				t2.run();
			}
		});
		assertThat(es.length, is(0));
	}

	@Test
	public void 一つ目の変数が実行できない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetUnrunnable t1 = new TargetUnrunnable(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetNormally t3 = new TargetNormally(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(1));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
	}

	@Test
	public void 二つ目の変数が実行できない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetNormally t1 = new TargetNormally(1);
				register(t1);
				TargetUnrunnable t2 = new TargetUnrunnable(2);
				register(t2);
				TargetNormally t3 = new TargetNormally(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(2));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
	}

	@Test
	public void 三つ目の変数が実行できない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetNormally t1 = new TargetNormally(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetUnrunnable t3 = new TargetUnrunnable(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(3));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
	}

	@Test
	public void 一つ目の変数がクローズできない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetUncloseable t1 = new TargetUncloseable(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetNormally t3 = new TargetNormally(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(1));
		assertThat(((MyIOException)es[0]).getPlace(), is("close"));
	}

	@Test
	public void 二つ目の変数がクローズできない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetNormally t1 = new TargetNormally(1);
				register(t1);
				TargetUncloseable t2 = new TargetUncloseable(2);
				register(t2);
				TargetNormally t3 = new TargetNormally(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(2));
		assertThat(((MyIOException)es[0]).getPlace(), is("close"));
	}

	@Test
	public void 三つ目の変数がクローズできない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetNormally t1 = new TargetNormally(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetUncloseable t3 = new TargetUncloseable(3);
				register(t3);
				t1.run();
				t2.run();
				t3.run();
			}
		});
		assertThat(es.length, is(1));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(3));
		assertThat(((MyIOException)es[0]).getPlace(), is("close"));
	}

	@Test
	public void 一つ目の変数が実行もクローズもできない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetUnrunnableUncloseable t1 = new TargetUnrunnableUncloseable(1);
				register(t1);
				t1.run();
			}
		});
		assertThat(es.length, is(2));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(es[1], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(1));
		assertThat(((MyIOException)es[1]).getId(), is(1));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
		assertThat(((MyIOException)es[1]).getPlace(), is("close"));
	}

	@Test
	public void 三つ目の変数が実行できず一つ目と五つ目の変数がクローズできない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetUncloseable t1 = new TargetUncloseable(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetUnrunnable t3 = new TargetUnrunnable(3);
				register(t3);
				TargetNormally t4 = new TargetNormally(4);
				register(t4);
				TargetUncloseable t5 = new TargetUncloseable(5);
				register(t5);
				t1.run();
				t2.run();
				t3.run();
				t4.run();
				t5.run();
			}
		});
		assertThat(es.length, is(3));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(es[1], instanceOf(IOException.class));
		assertThat(es[2], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(3));
		assertThat(((MyIOException)es[1]).getId(), is(1));
		assertThat(((MyIOException)es[2]).getId(), is(5));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
		assertThat(((MyIOException)es[1]).getPlace(), is("close"));
		assertThat(((MyIOException)es[2]).getPlace(), is("close"));
	}

	@Test
	public void 一つ目と五つ目の変数が実行できず三つ目の変数がクローズできない_五つ目は処理されない() {
		IOException[] es = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				TargetUnrunnable t1 = new TargetUnrunnable(1);
				register(t1);
				TargetNormally t2 = new TargetNormally(2);
				register(t2);
				TargetUncloseable t3 = new TargetUncloseable(3);
				register(t3);
				TargetNormally t4 = new TargetNormally(4);
				register(t4);
				TargetUnrunnable t5 = new TargetUnrunnable(5);
				register(t5);
				t1.run();
				t2.run();
				t3.run();
				t4.run();
				t5.run();
			}
		});
		assertThat(es.length, is(2));
		assertThat(es[0], instanceOf(IOException.class));
		assertThat(es[1], instanceOf(IOException.class));
		assertThat(((MyIOException)es[0]).getId(), is(1));
		assertThat(((MyIOException)es[1]).getId(), is(3));
		assertThat(((MyIOException)es[0]).getPlace(), is("run"));
		assertThat(((MyIOException)es[1]).getPlace(), is("close"));
	}

	@Test
	public void Runnableがnull_executeの引数がnull() {
		try {
			Using.execute(null);
		} catch (Throwable t) {
			assertThat(t, instanceOf(IllegalArgumentException.class));
			assertThat(t.getMessage(), is("Runnable is null."));
		}
	}

	@Test
	public void Closeableにnull_registerの引数がnull() {
		try {
			Using.execute(new Runnable() {
				@Override
				public void run() throws IOException {
					register(null);
				}
			});
		} catch (Throwable t) {
			assertThat(t, instanceOf(IllegalArgumentException.class));
			assertThat(t.getMessage(), is("Closeable is null."));
		}
	}

}
