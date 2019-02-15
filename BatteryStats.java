package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

class BatteryStats {

    int cold;
    int dead;
    int good;
    int overheat;
    int voltage;
    int health_unknown;
    int failure;
    int ac;
    int usb;
    int wireless;
    int capacity;
    int counter;
    int average;
    int now;
    int status;
    int charging;
    int discharging;
    int full;
    int status_unknown;

    public BatteryStats(int cold, int dead, int good, int overheat, int voltage, int health_unknown, int failure, int ac, int usb, int wireless, int capacity, int counter, int average, int now, int status, int charging, int discharging, int full, int status_unknown) {
        this.cold = cold;
        this.dead = dead;
        this.good = good;
        this.overheat = overheat;
        this.voltage = voltage;
        this.health_unknown = health_unknown;
        this.failure = failure;
        this.ac = ac;
        this.usb = usb;
        this.wireless = wireless;
        this.capacity = capacity;
        this.counter = counter;
        this.average = average;
        this.now = now;
        this.status = status;
        this.charging = charging;
        this.discharging = discharging;
        this.full = full;
        this.status_unknown = status_unknown;
    }

    public int getCold() {
        return cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getOverheat() {
        return overheat;
    }

    public void setOverheat(int overheat) {
        this.overheat = overheat;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getHealth_unknown() {
        return health_unknown;
    }

    public void setHealth_unknown(int health_unknown) {
        this.health_unknown = health_unknown;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getUsb() {
        return usb;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public int getWireless() {
        return wireless;
    }

    public void setWireless(int wireless) {
        this.wireless = wireless;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCharging() {
        return charging;
    }

    public void setCharging(int charging) {
        this.charging = charging;
    }

    public int getDischarging() {
        return discharging;
    }

    public void setDischarging(int discharging) {
        this.discharging = discharging;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public int getStatus_unknown() {
        return status_unknown;
    }

    public void setStatus_unknown(int status_unknown) {
        this.status_unknown = status_unknown;
    }

    @Override
    public String toString() {
        try {
            JSONObject json = new JSONObject();
            json.put("cold",cold);
            json.put("dead",dead);
            json.put("good",good);
            json.put("overheat",overheat);
            json.put("voltage",voltage);
            json.put("failure",failure);
            json.put("ac",ac);
            json.put("usb",usb);
            json.put("wireless",wireless);
            json.put("capacity",capacity);
            json.put("counter",counter);
            json.put("average",average);
            json.put("now",now);
            json.put("counter",counter);
            json.put("status",status);
            json.put("charging",charging);
            json.put("discharging",discharging);
            json.put("full",full);
            json.put("charging",charging);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
