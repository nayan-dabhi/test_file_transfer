package com.pbakondy;

import android.app.Activity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenSize extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("get")) {

      // http://developer.android.com/reference/android/util/DisplayMetrics.html

      DisplayMetrics dm = new DisplayMetrics();
      this.cordova.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

      int width = dm.widthPixels;
      int height = dm.heightPixels;

      double xdpi = (double)dm.xdpi;
      double ydpi = (double)dm.ydpi;
      double wi = (double)width / xdpi;
      double he = (double)height / ydpi;
      double x = Math.pow(wi, 2);
      double y = Math.pow(he, 2);
      double screenInches = Math.round(Math.sqrt(x + y) * 100.0) / 100.0;

      String densityName = "";

      if (dm.density >= 4.0) {
        densityName = "xxxhdpi";
      } else if (dm.density >= 3.0) {
        densityName = "xxhdpi";
      } else if (dm.density >= 2.0) {
        densityName = "xhdpi";
      } else if (dm.density >= 1.5) {
        densityName = "hdpi";
      } else if (dm.density >= 1.0) {
        densityName = "mdpi";
      } else {
        densityName = "ldpi";
      }

      JSONObject result = new JSONObject();
      try {
        result.put("width", width);
        result.put("height", height);
        result.put("diameter", screenInches);
        result.put("xdpi", xdpi);
        result.put("ydpi", ydpi);
        result.put("density_point", dm.density);
        result.put("density_name", densityName);
      } catch (JSONException e) {}

      callbackContext.success(result);

      return true;
    } else {
      return false;
    }
  }
}
