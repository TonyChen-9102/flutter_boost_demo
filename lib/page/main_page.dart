import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import '../router.dart';

class FlutterMainPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => FlutterMainPageState();
}

class FlutterMainPageState extends State<FlutterMainPage> {
  Map<dynamic, dynamic> value;

  void setValue(Map<dynamic, dynamic> newValue) {
    setState(() {
      this.value = newValue;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("MainPage"),
      ),
      body: Column(
        children: <Widget>[
          Text("返回内容" + (value != null ? value.toString() : "")),
          RaisedButton(
            child: Text("跳转flutter;有传参；接收返回数据"),
            onPressed: () {
              FlutterBoost.singleton.open(FIRST_PAGE,
                  urlParams: <String, dynamic>{
                    "param1": "111"
                  }).then((Map<dynamic, dynamic> value) {
                setValue(value);
              });
            },
          ),
          RaisedButton(
            child: Text("跳转native;有传参；接收返回数据"),
            onPressed: () {
              FlutterBoost.singleton.open(NATIVE_PAGE,
                  urlParams: <String, dynamic>{
                    "param1": "333"
                  }).then((Map<dynamic, dynamic> value) {
                setValue(value);
              });
            },
          )
        ],
      ),
    );
  }
}
