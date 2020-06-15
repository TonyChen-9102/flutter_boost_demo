package com.example.flutterapp3;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flutterapp3.router.PageRouter;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.Platform;
import com.idlefish.flutterboost.Utils;
import com.idlefish.flutterboost.interfaces.INativeRouter;

import java.util.Map;

import io.flutter.app.FlutterApplication;
import io.flutter.embedding.android.FlutterView;

public class MyApplication extends FlutterApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        Platform platform = new FlutterBoost.ConfigBuilder(this, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build();
        FlutterBoost.instance().init(platform);

        ARouter.init(this);
    }

    //监听跳转
    INativeRouter router = new INativeRouter() {
        @Override
        public void openContainer(Context context, String url, Map<String, Object> urlParams, int requestCode, Map<String, Object> exts) {
            String assembleUrl = Utils.assembleUrl(url, urlParams);
            PageRouter.openPageByUrl(context, assembleUrl, urlParams,requestCode);
        }
    };
    //监听状态
    FlutterBoost.BoostLifecycleListener boostLifecycleListener = new FlutterBoost.BoostLifecycleListener() {
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
}
