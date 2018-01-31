package com.litte.publiccomment.bean;

import java.util.List;

/**
 * Created by litte on 2018/1/31.
 */

public class RegionBean {

    /**
     * status : OK
     * cities : [{"city_name":"上海","districts":
     * [{"district_name":"卢湾区","neighborhoods":["淮海路","打浦桥","新天地","瑞金宾馆区"]},
     * {"district_name":"徐汇区","neighborhoods":["徐家汇","万体馆","衡山路","复兴西路/丁香花园","肇嘉浜路沿线","音乐学院","龙华","漕河泾/田林","上海南站"]},"......"]}]
     */

    private String status;
    private List<CitiesBean> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * city_name : 上海
         * districts : [{"district_name":"卢湾区","neighborhoods":["淮海路","打浦桥","新天地","瑞金宾馆区"]},{"district_name":"徐汇区","neighborhoods":["徐家汇","万体馆","衡山路","复兴西路/丁香花园","肇嘉浜路沿线","音乐学院","龙华","漕河泾/田林","上海南站"]},"......"]
         */

        private String city_name;
        private List<DistrictsBean> districts;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public List<DistrictsBean> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictsBean> districts) {
            this.districts = districts;
        }

        public static class DistrictsBean {
            /**
             * district_name : 卢湾区
             * neighborhoods : ["淮海路","打浦桥","新天地","瑞金宾馆区"]
             */

            private String district_name;
            private List<String> neighborhoods;

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public List<String> getNeighborhoods() {
                return neighborhoods;
            }

            public void setNeighborhoods(List<String> neighborhoods) {
                this.neighborhoods = neighborhoods;
            }
        }
    }
}
