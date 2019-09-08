package wddd.android.xposed.pinremover;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import wddd.android.xposed.pinremover.hook.OkHttp3Hook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {

    private static final String PACKAGE_NAME = "com.twitter.android";

    private OkHttp3Hook okHttp3Hook = new OkHttp3Hook();

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        String currentPackageName = lpparam.packageName;

        if (!currentPackageName.contains(PACKAGE_NAME)
                && !PACKAGE_NAME.contains(currentPackageName)) {
            XposedBridge.log("Stop hooking app " + currentPackageName);
            return;
        }

        XposedBridge.log("Start hooking app " + PACKAGE_NAME);

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
                    }
                }
        );
    }
}