package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

public class CallLogInfo {
    private String  number;
    private String  type;
    private String  date;
    private String  datausage;
    private int     callDuration; // in seconds

    public CallLogInfo(String number, String type, String date, String datausage, int callDuration) {
        this.number = number;
        this.type = type;
        this.date = date;
        this.datausage = datausage;
        this.setCallDuration(callDuration);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatausage() {
        return datausage;
    }

    public void setDatausage(String datausage) {
        this.datausage = datausage;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    @Override
    public String toString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone_number",      number);
            jsonObject.put("type",              type);
            jsonObject.put("date",              date);
            jsonObject.put("datausage",         datausage);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
