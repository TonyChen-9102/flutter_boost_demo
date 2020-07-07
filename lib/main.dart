import 'package:flutter/material.dart';

import 'page/main_page.dart';
import 'router.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    Router.register();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "boost demo",
      //初始化
      builder: Router.init(),
      home: FlutterMainPage(),
    );
  }
}
