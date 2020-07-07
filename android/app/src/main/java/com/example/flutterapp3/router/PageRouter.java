package com.example.flutterapp3.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.Platform;
import com.idlefish.flutterboost.Utils;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;
import com.idlefish.flutterboost.interfaces.IFlutterViewContainer;
import com.idlefish.flutterboost.interfaces.INativeRouter;

import java.io.Serializable;
import java.util.Map;

import io.flutter.embedding.android.FlutterView;

import static android.app.Activity.RESULT_OK;

public class PageRouter {
    public static final String NATIVR_PARAM_KEY = "params";
    //---protocol---
    public static final String PROTOCOL_NATIVE = "native";
    public static final String PROTOCOL_FLUTTER = "flutter";
    //---- pages----
    //flutter
    public static final String FLUTTER_MAIN_PAGE = "flutter://flutterMainPage";
    public static final String FLUTTER_FIRST_PAGE = "flutter://flutterFirstPage";
    //native arouter形式
    public static final String NATIVE_MAIN_FIRST_PAGE = "/main/firstPage";
    public static final String NATIVE_MAIN_SECOND_PAGE = "/main/secondPage";

    public static void init(Application app) {
        //flutter_boost
        Platform platform = new FlutterBoost.ConfigBuilder(app, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build();
        FlutterBoost.instance().init(platform);
        //arouter
        ARouter.init(app);
    }

    //监听跳转
    static INativeRouter router = new INativeRouter() {
        @Override
        public void openContainer(Context context, String url, Map<String, Object> urlParams, int requestCode, Map<String, Object> exts) {
            String assembleUrl = Utils.assembleUrl(url, urlParams);
            PageRouter.openPageByUrl(context, assembleUrl, urlParams, requestCode);
        }
    };
    //监听状态
    static FlutterBoost.BoostLifecycleListener boostLifecycleListener = new FlutterBoost.BoostLifecycleListener() {
        @Override
        public void beforeCreateEngine() {

        }

        @Override
        public void onEngineCreated() {

        }

        @Override
        public void onPluginsRegistered() {

        }

        @Override
        public void onEngineDestroy() {

        }
    };

    public static boolean openPageByUrl(Context context, String url, Map<String, Object> params, int requestCode) {
        //获取域名
        String path = url.split("\\?")[0];
        //获取协议
        String protocol = url.split("://")[0];

        if (TextUtils.equals(PROTOCOL_FLUTTER, protocol)) {
            Intent intent = BoostFlutterActivity.withNewEngine().url(path).params(params)
                    .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
            if (context instanceof Activity && requestCode != -1) {
                Activity activity = (Activity) context;
                activity.startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
            return true;
        } else if (TextUtils.equals(PROTOCOL_NATIVE, protocol)) {
            String nativePath = getBoostToRouter(path);
            if (TextUtils.isEmpty(nativePath)) {
                return false;
            }

            Bundle bundle = new Bundle();
            if (params != null) {
                bundle.putSerializable(NATIVR_PARAM_KEY, (Serializable) params);
            }

            if (context instanceof Activity && requestCode != -1) {
                Activity activity = (Activity) context;
                getArouter(nativePath).with(bundle).navigation(activity, requestCode);
            } else {
                gotoActivity(nativePath, bundle);
            }
            return true;
        }
        return false;
    }

    /**
     * Arouter跳转
     *
     * @param path
     * @return
     */
    public static Postcard getArouter(String path) {
        Postcard postcard = ARouter.getInstance()
                .build(path);
        postcard.setName("");
        return postcard;
    }

    /**
     * Arouter跳转
     */
    public static void gotoActivity(String path) {
        getArouter(path).navigation();
    }

    /**
     * Arouter跳转
     */
    public static void gotoActivity(String path, Bundle bundle) {
        Postcard postcard = getArouter(path)
                .with(bundle);
        postcard.navigation();
    }

    /**
     * 设置native返回intent
     *
     * @param map
     * @return
     */
    public static Intent getMapToIntent(Map<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(IFlutterViewContainer.RESULT_KEY, (Serializable) map);
        return intent;
    }

    /**
     * result 数据返回
     *
     * @param intent
     * @return
     */
    public static Map<String, Object> getIntentToMap(Intent intent) {
        Map<String, Object> result = null;
        if (intent != null) {
            Serializable rlt = intent.getSerializableExtra(IFlutterViewContainer.RESULT_KEY);
            if (rlt instanceof Map) {
                result = (Map<String, Object>) rlt;
            }
        }
        return result;
    }

    /**
     * 接受数据
     *
     * @param intent
     * @return
     */
    public static Map<String, Object> getIntentToMap2(Intent intent) {
        Map<String, Object> result = null;
        if (intent != null) {
            Serializable rlt = intent.getSerializableExtra(NATIVR_PARAM_KEY);
            if (rlt instanceof Map) {
                result = (Map<String, Object>) rlt;
            }
        }
        return result;
    }

    /**
     * arouter地址转换成fluter-boost地址
     *
     * @param nativePath
     * @return
     */
    public static String getArouterToBoost(String nativePath) {
        return PageRouter.PROTOCOL_NATIVE + ":/" + nativePath;
    }

    /**
     * fluter-boost地址转换成Arouter地址
     *
     * @param path
     * @return
     */
    public static String getBoostToRouter(String path) {
        String nativePath = null;
        if (!TextUtils.isEmpty(path)) {
            String[] aar = path.split("://");
            if (aar.length > 1) {
                nativePath = aar[1];
                nativePath = "/" + nativePath;
            }
        }
        return nativePath;
    }

    public static void open(Context context, String url, Map<String, Object> params) {
        open(context, url, params, -1);
    }

    public static void open(Context context, String url, Map<String, Object> params, int requestCode) {
        //获取协议
        String protocol = url.split("://")[0];
        if (!TextUtils.isEmpty(protocol)) {
            PageRouter.openPageByUrl(context, url, params, requestCode);
        } else {
            PageRouter.openPageByUrl(context, PageRouter.getArouterToBoost(url), params, requestCode);
        }
    }

    public static void close(@NonNull Activity activity, Map<String, Object> map) {
        activity.setResult(RESULT_OK, PageRouter.getMapToIntent(map));
        activity.finish();
    }
}
