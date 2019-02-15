package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class ContactInfo {
    private List<String> phoneNumber;
    private String name;

    public ContactInfo(List<String> phoneNumber, String name){
        this.setPhoneNumber(phoneNumber);
        this.setName(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name", name);
            int i = 0;
            for(String phone : phoneNumber){
                i++;
                jsonObject.put("phone-"+i, phone);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
