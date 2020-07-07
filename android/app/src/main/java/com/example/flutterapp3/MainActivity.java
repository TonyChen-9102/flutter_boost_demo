package com.example.flutterapp3;

import com.idlefish.flutterboost.containers.BoostFlutterActivity;

public class MainActivity extends BoostFlutterActivity {

    @Override
    public String getContainerUrl() {
        return "flutter://flutterMainPage";
    }
}
