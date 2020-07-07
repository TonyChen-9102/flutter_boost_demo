package com.example.flutterapp3;

import com.example.flutterapp3.router.PageRouter;

import io.flutter.app.FlutterApplication;

public class MyApplication extends FlutterApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        PageRouter.init(this);
    }
}
