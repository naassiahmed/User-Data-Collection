package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

class CalendarInfo {
    private String nameEvent;
    private String startDate;
    private String endDate;
    private String description;

    public CalendarInfo(String nameEvent, String startDate, String endDate, String description) {
        this.setNameEvent(nameEvent);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setDescription(description);
    }


    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("nameEvent", nameEvent);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
            jsonObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
