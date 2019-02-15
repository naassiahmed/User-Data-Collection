package bouzid.spyme.mosaic.bouzid;

import org.json.JSONException;
import org.json.JSONObject;

class HardwareInfo {
    private long totalMemory;
    private long avalaibleMemory;
    private int cores;
    private String cpuModelName;
    private float cpuMhz;
    private String model;
    private String brand;

    public HardwareInfo(long totalMemory, long avalaibleMemory, int cores, String cpuModelName, float cpuMhz, String model, String brand) {
        this.totalMemory = totalMemory;
        this.avalaibleMemory = avalaibleMemory;
        this.cores = cores;
        this.cpuModelName = cpuModelName;
        this.cpuMhz = cpuMhz;
        this.model = model;
        this.brand = brand;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getAvalaibleMemory() {
        return avalaibleMemory;
    }

    public void setAvalaibleMemory(long avalaibleMemory) {
        this.avalaibleMemory = avalaibleMemory;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public String getCpuModelName() {
        return cpuModelName;
    }

    public void setCpuModelName(String cpuModelName) {
        this.cpuModelName = cpuModelName;
    }

    public float getCpuMhz() {
        return cpuMhz;
    }

    public void setCpuMhz(float cpuMhz) {
        this.cpuMhz = cpuMhz;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {

        try {
            JSONObject json = new JSONObject();
            json.put("totalMemory", totalMemory);
            json.put("avalaibleMemory", avalaibleMemory);
            json.put("cores", cores);
            json.put("cpuModelName", cpuModelName);
            json.put("cpuMhz", cpuMhz);
            json.put("model", model);
            json.put("brand", brand);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
