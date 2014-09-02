/*
 * MIT License
 * (c) 2013 bernd@snowhow.info
 *
*/
package info.snowhow.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import android.content.Intent;

public class GPSTrack extends CordovaPlugin {

  private static final String LOG_TAG = "GPSTrack";

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("record")) {
      final String trackFile = args.getString(0);
      final float precision = (float) args.getDouble(1);
      final boolean adaptiveRecording = (boolean) args.getBoolean(2);
      if (trackFile.indexOf("file:///") > -1) {
        record(trackFile.substring(7), precision, adaptiveRecording);
      } else {
        record(trackFile, precision, adaptiveRecording);
      }
      JSONObject obj = new JSONObject();
      obj.put("test", 1);
      PluginResult res = new PluginResult(PluginResult.Status.OK, obj);
      res.setKeepCallback(true);
      callbackContext.sendPluginResult(res);
      Log.d(LOG_TAG, "done sending to webapp");
//       cordova.getActivity().runOnUiThread(new Runnable() {
//         @Override
//         public void run() {
//           GPSTrack.this.record(trackFile);
//           // callbackContext.success();
//         }
//       });
    } else if (action.equals("listen")) {
      JSONObject obj = new JSONObject();
      obj.put("test", 1);
      PluginResult res = new PluginResult(PluginResult.Status.OK, obj);
      res.setKeepCallback(true);
      callbackContext.sendPluginResult(res);
      Log.d(LOG_TAG, "done sending to webapp");
    } else {
      return false;
    }
    return true;
  }

  public void record(final String trackFile, final float precision, final boolean adaptiveRecording) {
    Context context = cordova.getActivity().getApplicationContext();
    Intent intent = new Intent(context, RecorderService.class);
    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("fileName", trackFile);
    intent.putExtra("precision", precision);
    intent.putExtra("adaptiveRecording", adaptiveRecording);
    Log.d(LOG_TAG, "in record ... should start intent now");
    context.startService(intent);
  }

}
