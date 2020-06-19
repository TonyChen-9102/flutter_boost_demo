package com.example.flutterapp3;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flutterapp3.router.PageRouter;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;

public class MainActivity extends BoostFlutterActivity {

    @Override
    public String getContainerUrl() {
        return "flutter://flutterMainPage";
    }
}
