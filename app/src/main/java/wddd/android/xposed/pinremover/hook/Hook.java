package wddd.android.xposed.pinremover.hook;

import android.util.Log;

import static wddd.android.xposed.pinremover.Main.TAG;

abstract class Hook {
    abstract void hook(final ClassLoader classLoader);

    void log(String message) {
        this.log(message, null);
    }

    void log(String message, Throwable e) {
        if (e != null) {
            Log.e(TAG, message, e);
        } else {
            Log.i(TAG, message);
        }
    }
}
