package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

class ApplicationInfo {

    String appName;
    String packageName;
    String versionName;
    String versionCode;

    public ApplicationInfo(String appName, String packageName, String versionName, String versionCode) {
        this.appName = appName;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("appName", appName);
            jsonObject.put("packageName", packageName);
            jsonObject.put("versionCode", versionCode);
            jsonObject.put("versionName", versionName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
