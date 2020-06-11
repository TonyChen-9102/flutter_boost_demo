package com.example.flutterapp3;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flutterapp3.router.PageRouter;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageRouter.openPageByUrl(this, PageRouter.FLUTTER_MAIN_PAGE, null, 0);
        finish();
    }
}
