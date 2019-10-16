package wddd.android.xposed.pinremover;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import wddd.android.xposed.pinremover.hook.AndroidOpenSSLSocketHook;
import wddd.android.xposed.pinremover.hook.JavaxNetHook;
import wddd.android.xposed.pinremover.hook.OkHttp3Hook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {

    public static final String TAG = "PinRemover";

    private static final List<String> APPS = Arrays.asList("twitter", "amazon");

    private OkHttp3Hook okHttp3Hook = new OkHttp3Hook();
    private JavaxNetHook javaxNetHook = new JavaxNetHook();
    private AndroidOpenSSLSocketHook androidOpenSSLSocketHook = new AndroidOpenSSLSocketHook();

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        final String currentPackageName = lpparam.packageName;

        boolean shouldCheckApp = false;
        for (String app : APPS) {
            if (currentPackageName.toLowerCase().contains(app)) {
                shouldCheckApp = true;
                break;
            }
        }

        if (!shouldCheckApp) {
            Log.e(TAG, "Stop hooking app " + currentPackageName);
            return;
        }

        Log.i(TAG, "Start hooking app " + currentPackageName);

        // Multi-dex support: https://github.com/rovo89/XposedBridge/issues/30#issuecomment-68486449
        findAndHookMethod("android.app.Application",
                lpparam.classLoader,
                "attach",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Context context = (Context) param.args[0];
                        ClassLoader classLoader = context.getClassLoader();

                        okHttp3Hook.hook(classLoader);
                        javaxNetHook.hook(classLoader);
                        androidOpenSSLSocketHook.hook(classLoader);
                    }
                }
        );
    }
}