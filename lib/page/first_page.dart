import 'package:flutter/material.dart';
import 'package:flutter_boost/container/boost_container.dart';
import 'package:flutter_boost/flutter_boost.dart';

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
              final BoostContainerSettings settings =
                  BoostContainer.of(context).settings;
              FlutterBoost.singleton.close(settings.uniqueId,
                  result: <String, dynamic>{'result': 'data from fist page'});
            },
          ),
        ],
      ),
    );
  }
}
