package wddd.android.xposed.pinremover.hook;

import de.robv.android.xposed.XC_MethodReplacement;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class JavaxNetHook extends Hook {

    @Override
    public void hook(ClassLoader classLoader) {
        try {
            hookHttpsURLConnectionSetSSLSocketFactory(classLoader);
        } catch (Throwable e) {
            log("setSSLSocketFactory hook exception: ", e);
        }

        try {
            hookHttpsURLConnectionSetSSLSocketFactoryWithStrongCipherSSLSocketFactory(classLoader);
        } catch (Throwable e) {
            log("setSSLSocketFactory for StrongCipherSSLSocketFactory hook exception: ", e);
        }
    }

    private void hookHttpsURLConnectionSetSSLSocketFactoryWithStrongCipherSSLSocketFactory(ClassLoader classLoader) {
        Class<?> sslSocketFactory = findClass("com.amazon.clouddrive.library.http.StrongCipherSSLSocketFactory", classLoader);

        findAndHookMethod("javax.net.ssl.HttpsURLConnection",
                classLoader,
                "setSSLSocketFactory",
                sslSocketFactory,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("setSSLSocketFactory for StrongCipherSSLSocketFactory hook succeeded.");
                        return null;
                    }
                });
    }

    private void hookHttpsURLConnectionSetSSLSocketFactory(ClassLoader classLoader) {
        Class<?> sslSocketFactory = findClass("javax.net.ssl.SSLSocketFactory", classLoader);

        findAndHookMethod("javax.net.ssl.HttpsURLConnection",
                classLoader,
                "setSSLSocketFactory",
                sslSocketFactory,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("setSSLSocketFactory hook succeeded.");
                        return null;
                    }
                });
    }
}
