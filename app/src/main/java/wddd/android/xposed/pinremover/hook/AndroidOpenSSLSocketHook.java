package wddd.android.xposed.pinremover.hook;

import de.robv.android.xposed.XC_MethodReplacement;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class AndroidOpenSSLSocketHook extends Hook {

    @Override
    public void hook(ClassLoader classLoader) {
        try {
            hookOpenSSLSocketImplVerifyCertificateChain(classLoader);
        } catch (Throwable e) {
            log("verifyCertificateChain hook exception: ", e);
        }

        try {
            hookOpenSSLSocketImplVerifyCertificateChain2(classLoader);
        } catch (Throwable e) {
            log("verifyCertificateChain2 hook exception: ", e);
        }

        try {
            hookOpenSSLSocketImplVerifyCertificateChain3(classLoader);
        } catch (Throwable e) {
            log("verifyCertificateChain3 hook exception: ", e);
        }
    }

    private void hookOpenSSLSocketImplVerifyCertificateChain(ClassLoader classLoader) {
        findAndHookMethod("com.android.org.conscrypt.OpenSSLSocketImpl",
                classLoader,
                "verifyCertificateChain",
                long[].class,
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("verifyCertificateChain hook succeeded.");
                        return null;
                    }
                });
    }

    private void hookOpenSSLSocketImplVerifyCertificateChain2(ClassLoader classLoader) {
        findAndHookMethod("com.android.org.conscrypt.OpenSSLSocketImpl",
                classLoader,
                "verifyCertificateChain",
                byte[][].class,
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("verifyCertificateChain2 hook succeeded.");
                        return null;
                    }
                });
    }

    private void hookOpenSSLSocketImplVerifyCertificateChain3(ClassLoader classLoader) {
        findAndHookMethod("com.android.org.conscrypt.OpenSSLSocketImpl",
                classLoader,
                "verifyCertificateChain",
                long.class,
                long[].class,
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        log("verifyCertificateChain3 hook succeeded.");
                        return null;
                    }
                });
    }
}
