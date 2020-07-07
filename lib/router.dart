import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import 'page/first_page.dart';
import 'page/main_page.dart';

//flutter
const String MAIN_PAGE = "flutter://flutterMainPage";
const String FIRST_PAGE = "flutter://flutterFirstPage";
//native
const String NATIVE_PAGE = "native://main/firstPage";

class Router {
  static TransitionBuilder init() {
    return FlutterBoost.init(postPush: Router.onRoutePushed);
  }

  //初始化
  static void register() {
    registerRoutes();
    //监听页面状态
    FlutterBoost.singleton.addBoostNavigatorObserver(BoostNavigatorObserver());
  }

  //注册页面
  static void registerRoutes() {
    FlutterBoost.singleton.registerPageBuilders(<String, PageBuilder>{
      MAIN_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterMainPage(),
      FIRST_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterFirstPage(params: params),
    });
  }

  //监听页面跳转情况
  static void onRoutePushed(
    String pageName,
    String uniqueId,
    Map<dynamic, dynamic> params,
    Route<dynamic> route,
    Future<dynamic> _,
  ) {}

  // 打开页面
  static Future<Map> open(
    String url, {
    Map<dynamic, dynamic> urlParams,
    Map<dynamic, dynamic> exts,
  }) {
    return FlutterBoost.singleton.open(url, urlParams: urlParams, exts: exts);
  }

  // 关闭页面
  static Future<bool> close(BuildContext context,
      {Map<dynamic, dynamic> result, Map<dynamic, dynamic> exts}) {
    String uniqueId = BoostContainer.of(context).uniqueId;
    return FlutterBoost.singleton.close(uniqueId, result: result, exts: exts);
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
