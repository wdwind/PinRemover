package wddd.android.xposed.pinremover.hook;

import java.net.ProxySelector;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class OkHttp3Hook implements Hook {

    @Override
    public void hook(ClassLoader classLoader) {
//        try {
//            hookBasicCertificateChainCleaner(classLoader);
//        } catch (Exception e) {
//            log("BasicCertificateChainCleaner hook exception: ", e);
//        }

//        try {
//            hookAndroidCertificateChainCleaner(classLoader);
//        } catch (Exception e) {
//            log("AndroidCertificateChainCleaner hook exception: ", e);
//        }

        // OkHttpClient.Builder
        try {
            hookSslSocketFactory(classLoader);
        } catch (Exception e) {
            log("SslSocketFactory hook exception: ", e);
        }

//        try {
//            hookConnectionSpecs(classLoader);
//        } catch (Exception e) {
//            log(": ConnectionSpecs hook exception: ", e);
//        }
//
//        try {
//            hookProxySelector(classLoader);
//        } catch (Exception e) {
//            log("ProxySelector hook exception: ", e);
//        }
//
//        try {
//            hookProxyAuthenticator(classLoader);
//        } catch (Exception e) {
//            log("ProxyAuthenticator hook exception: ", e);
//        }
//
//        try {
//            hookAddInterceptor(classLoader);
//        } catch (Exception e) {
//            log("AddInterceptor hook exception: ", e);
//        }
    }

    private void hookBasicCertificateChainCleaner(ClassLoader classLoader) {
        findAndHookMethod("okhttp3.internal.tls.BasicCertificateChainCleaner",
                classLoader,
                "clean",
                List.class,
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("BasicCertificateChainCleaner.clean hook succeeded.");
                        return param.args[0];
                    }
                });
    }

    private void hookAndroidCertificateChainCleaner(ClassLoader classLoader) {
        findAndHookMethod("okhttp3.internal.platform.AndroidPlatform.AndroidCertificateChainCleaner",
                classLoader,
                "clean",
                List.class,
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("AndroidCertificateChainCleaner.clean hook succeeded.");
                        return param.args[0];
                    }
                });
    }

    // OhHttpClient.Builder hooks
    private void hookSslSocketFactory(ClassLoader classLoader) {
        findAndHookMethod("okhttp3.OkHttpClient.Builder",
                classLoader,
                "sslSocketFactory",
                SSLSocketFactory.class,
                X509TrustManager.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("sslSocketFactory hook succeeded.");
                        return param.thisObject;
                    }
                });
    }

    private void hookConnectionSpecs(ClassLoader classLoader) {
        findAndHookMethod("okhttp3.OkHttpClient.Builder",
                classLoader,
                "connectionSpecs",
                List.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("connectionSpecs hook succeeded.");
                        return param.thisObject;
                    }
                });
    }

    private void hookProxySelector(ClassLoader classLoader) {
        findAndHookMethod("okhttp3.OkHttpClient.Builder",
                classLoader,
                "proxySelector",
                ProxySelector.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("proxySelector hook succeeded.");
                        return param.thisObject;
                    }
                });
    }

    private void hookProxyAuthenticator(ClassLoader classLoader) {
        Class<?> authenticator = findClass("okhttp3.Authenticator", classLoader);

        findAndHookMethod("okhttp3.OkHttpClient.Builder",
                classLoader,
                "proxyAuthenticator",
                authenticator,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("proxyAuthenticator hook succeeded.");
                        return param.thisObject;
                    }
                });
    }

    private void hookAddInterceptor(ClassLoader classLoader) {
        Class<?> interceptor = findClass("okhttp3.Interceptor", classLoader);

        findAndHookMethod("okhttp3.OkHttpClient.Builder",
                classLoader,
                "addInterceptor",
                interceptor,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("addInterceptor hook succeeded.");
                        return param.thisObject;
                    }
                });
    }

    private void log(String message) {
        this.log(message, null);
    }

    private void log(String message, Exception e) {
        XposedBridge.log(this.getClass().getName() + ": " + message);
        if (e != null) {
            XposedBridge.log(e);
        }
    }
}
