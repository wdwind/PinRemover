package wddd.android.xposed.pinremover.hook;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class OkHttpClientBuilderHook implements Hook {

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

//    private class ImSureItsLegitTrustManager implements X509TrustManager {
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        }
//
//        public List<X509Certificate> checkServerTrusted(X509Certificate[] chain, String authType, String host) throws CertificateException {
//            ArrayList<X509Certificate> list = new ArrayList<X509Certificate>();
//            return list;
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[0];
//        }
//    }
}
