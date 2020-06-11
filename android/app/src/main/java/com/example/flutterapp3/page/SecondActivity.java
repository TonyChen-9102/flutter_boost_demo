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
@Route(path = PageRouter.NATIVE_MAIN_SECOND_PAGE)
public class SecondActivity extends Activity {
    TextView textView;
    Button button;

    private Map<String, Object> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        parseIntent();

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        if (params != null) {
            textView.setText("入参：" + params.toString());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("result", "SecondActivity result");
                setResult(RESULT_OK, PageRouter.setNativeBackResult(map));
                finish();
            }
        });
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        params = (Map<String, Object>) intent.getSerializableExtra(PageRouter.NATIVR_PARAM_KEY);
    }
}
