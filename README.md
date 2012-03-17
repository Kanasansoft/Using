# Using - can close resource safely on Java6

## Overview

**Using** can close resource safely on Java6.


### note

C# has syntax that **using** statement can close resource safely.
Java7 has syntax that **try-with-resources** statement can close resource safely too.
But Java6 has not syntax like feture.
This library can close resource safely on Java6.

### usage

Simple to use.

```
import com.kanasansoft.using.Runnable;
import com.kanasansoft.using.Using;
...
		Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				XxxOutputStream os = new XxxOutputStream();
				register(os);
				XxxInputStream is = new XxxInputStream();
				register(is);
				...
			}
		});
...
```

If you need exception occurred…

```
import com.kanasansoft.using.Runnable;
import com.kanasansoft.using.Using;
...
		IOException[] exceptions = Using.execute(new Runnable() {
			@Override
			public void run() throws IOException {
				XxxOutputStream os = new XxxOutputStream();
				register(os);
				XxxInputStream is = new XxxInputStream();
				register(is);
				...
			}
		});
...
```
