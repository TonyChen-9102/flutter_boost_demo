package com.example.flutterapp3.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flutterapp3.R;
import com.example.flutterapp3.router.PageRouter;

import java.util.HashMap;
import java.util.Map;

@Route(path = PageRouter.NATIVE_MAIN_FIRST_PAGE)
public class FirstActivity extends Activity {
    private TextView tvParams;
    private Button buttonBack;
    private Button buttonBack2;
    private Button buttonBack3;

    private Map<String, Object> params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        parseIntent();

        tvParams = findViewById(R.id.tvParams);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack2 = findViewById(R.id.buttonBack2);
        buttonBack3 = findViewById(R.id.buttonBack3);

        if (params != null) {
            tvParams.setText("入参：" + params.toString());
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("result", "FirstActivity result");
                PageRouter.close(FirstActivity.this, map);
            }
        });
        buttonBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("param1", "FirstActivity parames");
                PageRouter.open(FirstActivity.this, PageRouter.FLUTTER_FIRST_PAGE,
                        params, 3);
            }
        });
        buttonBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("param1", "FirstActivity parames");
                PageRouter.open(FirstActivity.this, PageRouter.NATIVE_MAIN_SECOND_PAGE,
                        params, 3);
            }
        });
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        params = PageRouter.getIntentToMap2(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        params = PageRouter.getIntentToMap(data);
        if (params != null) {
            tvParams.setText("返回参数：" + params.toString());
        }
    }
}
