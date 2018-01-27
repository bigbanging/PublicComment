package com.litte.publiccomment.bean;

import java.util.List;

/**
 * Created by litte on 2018/1/25.
 */

public class CityBean {

    /**
     * status : OK
     * cities : ["阿坝","鞍山","安康","安庆","安阳","......"]
     */

    private String status;
    private List<String> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "status='" + status + '\'' +
                ", cities=" + cities +
                '}';
    }
}
