package com.litte.publiccomment.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by litte on 2018/1/25.
 */
@DatabaseTable
public class CityPinYinBean {
    @DatabaseField(id = true)
    private String pyName;//城市拼音名称
    @DatabaseField
    private String cityName;//城市中文名称
    @DatabaseField
    private char letter; //城市拼音首字母

    public CityPinYinBean() {
    }

    public CityPinYinBean(String cityName, String pyName, char letter) {
        this.cityName = cityName;
        this.pyName = pyName;
        this.letter = letter;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "CityPinYinBean{" +
                "cityName='" + cityName + '\'' +
                ", pyName='" + pyName + '\'' +
                ", letter=" + letter +
                '}';
    }
}
