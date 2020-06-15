import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import 'page/first_page.dart';
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
    //注册页面
    FlutterBoost.singleton.registerPageBuilders(<String, PageBuilder>{
      MAIN_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterMainPage(),
      FIRST_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterFirstPage(params: params),
    });
    //监听页面状态
    FlutterBoost.singleton.addBoostNavigatorObserver(BoostNavigatorObserver());
  }

  //监听页面跳转情况
  void _onRoutePushed(
    String pageName,
    String uniqueId,
    Map<dynamic, dynamic> params,
    Route<dynamic> route,
    Future<dynamic> _,
  ) {}

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "boost demo",
      //初始化
      builder: FlutterBoost.init(postPush: _onRoutePushed),
      home: FlutterMainPage(),
    );
  }
}

class BoostNavigatorObserver extends NavigatorObserver {
  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
  }

  @override
  void didRemove(Route route, Route previousRoute) {
    super.didRemove(route, previousRoute);
  }

  @override
  void didReplace({Route newRoute, Route oldRoute}) {
    super.didReplace(newRoute: newRoute, oldRoute: oldRoute);
  }
}
