import 'package:flutter/material.dart';
import 'package:flutterboostdemo/router.dart';

class FlutterFirstPage extends StatelessWidget {
  final Map<dynamic, dynamic> params;

  FlutterFirstPage({this.params});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FirstPage"),
      ),
      body: Column(
        children: <Widget>[
          Text("入参：" + params.toString()),
          RaisedButton(
            child: Text("返回参数"),
            onPressed: () {
              //关闭并返回参数
              Router.close(context,
                  result: <String, dynamic>{'result': 'data from fist page'});
            },
          ),
        ],
      ),
    );
  }
}
