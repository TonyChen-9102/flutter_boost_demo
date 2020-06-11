package com.example.flutterapp3.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;
import com.idlefish.flutterboost.interfaces.IFlutterViewContainer;

import java.io.Serializable;
import java.util.Map;

public class PageRouter {
    public static final String NATIVR_PARAM_KEY = "params";
    //---protocol---
    public static final String PROTOCOL_NATIVE = "native";
    public static final String PROTOCOL_FLUTTER = "flutter";
    //---- pages----
    //flutter
    public static final String FLUTTER_MAIN_PAGE = "flutter://flutterMainPage";
    public static final String FLUTTER_FIRST_PAGE = "flutter://flutterFirstPage";
    //native
    public static final String NATIVE_MAIN_FIRST_PAGE = "/main/firstPage";
    public static final String NATIVE_MAIN_SECOND_PAGE = "/main/secondPage";

    public static boolean openPageByUrl(Context context, String url, Map<String, Object> params, int requestCode) {
        String path = url.split("\\?")[0];
        String protocol = url.split("://")[0];

        if (TextUtils.equals(PROTOCOL_FLUTTER, protocol)) {
            Intent intent = BoostFlutterActivity.withNewEngine().url(path).params(params)
                    .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                activity.startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
            return true;
        } else if (TextUtils.equals(PROTOCOL_NATIVE, protocol)) {
            String nativePath = null;
            if (!TextUtils.isEmpty(path)) {
                String[] aar = path.split("://");
                if (aar.length > 1) {
                    nativePath = aar[1];
                    nativePath = "/" + nativePath;
                }
            }
            if (TextUtils.isEmpty(nativePath)) {
                return false;
            }

            Bundle bundle = new Bundle();
            if (params != null) {
                bundle.putSerializable(NATIVR_PARAM_KEY, (Serializable) params);
            }

            if (context instanceof Activity) {
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
    public static Intent setNativeBackResult(Map<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(IFlutterViewContainer.RESULT_KEY, (Serializable) map);
        return intent;
    }

    /**
     * 获取返回的数据
     *
     * @param intent
     * @return
     */
    public static Map<String, Object> getNativeBackResult(Intent intent) {
        Map<String, Object> result = null;
        if (intent != null) {
            Serializable rlt = intent.getSerializableExtra(IFlutterViewContainer.RESULT_KEY);
            if (rlt instanceof Map) {
                result = (Map<String, Object>) rlt;
            }
        }
        return result;
    }

    public static String getNativeWholePath(String nativePath) {
        return PageRouter.PROTOCOL_NATIVE + ":/" + nativePath;
    }
}
