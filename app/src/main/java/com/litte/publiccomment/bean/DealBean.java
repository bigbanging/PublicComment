package com.litte.publiccomment.bean;

import java.util.List;

/**
 * Created by litte on 2018/1/22.
 */

public class DealBean {

    /**
     * status : OK
     * count : 1
     * deals : [{"deal_id":"1-5097286","title":"Le Camelia法式浪漫拿破仑","description":"仅售138元,价值238元法式浪漫拿破仑1个!2店通用!月星环球港新店开业!法式甜品经典,三十多层香脆起酥皮,融合清新奶油,口感层次丰富,幸福甜蜜妙不可言!","city":"上海","list_price":238,"current_price":138,"regions":["世纪公园","月星环球港"],"categories":["面包甜点"],"purchase_count":241,"purchase_deadline":"2014-01-23","publish_date":"2013-10-24","details":"团购详情\n 凭大众点评网团购券可享受以下内容：\n\n- 法式浪漫拿破仑（1个，价值238元）\n 16cm*16cm，约2磅左右\n- 精美蛋糕刀叉（1套，赠送）\n 含5个盘子、5个叉子、5张餐巾纸、1把餐刀、蛋糕盒1个、精美拎袋1个\n \n","image_url":"http://t2.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x280)/thumb.jpg","s_image_url":"http://t1.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(160x100)/thumb.jpg","more_image_urls":["http://t3.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x1024)/thumb.jpg","http://t3.s1.dpfile.com/pc/mc/cffd005b1a94a55cb59fb8494ea09804(450x1024)/aD0xMDI0Jms9L3BjL21jL2NmZmQwMDViMWE5NGE1NWNiNTlmYjg0OTRlYTA5ODA0JmxvZ289MCZtPXgmdz00NTA.a57d8a56f07afd7ce322cc6abbcac896/thumb.jpg","http://t2.s2.dpfile.com/pc/mc/004b104c126a4319a55ac0f999d44419(450x1024)/thumb.jpg"],"more_s_image_urls":["http://t3.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x1024)/thumb_1.jpg","http://t3.s1.dpfile.com/pc/mc/cffd005b1a94a55cb59fb8494ea09804(450x1024)/aD0xMDI0Jms9L3BjL21jL2NmZmQwMDViMWE5NGE1NWNiNTlmYjg0OTRlYTA5ODA0JmxvZ289MCZtPXgmdz00NTA.a57d8a56f07afd7ce322cc6abbcac896/thumb_1.jpg","http://t2.s2.dpfile.com/pc/mc/004b104c126a4319a55ac0f999d44419(450x1024)/thumb_1.jpg"],"is_popular":1,"restrictions":{"is_reservation_required":1,"is_refundable":1,"special_tips":"购买须知\n \n有效期 \n2013-10-25 至 2014-01-24\n \n预约 \n必须至少提前1-2天致电商家预约\n \n使用须知 \n仅限外带，外带免费\n仅限到店自提，不提供配送服务\n每天最多接受30张团购预约\n本团购设定3个提货点：\n1.喜玛拉雅中心：浦东新区芳甸路1188弄证大喜玛拉雅中心1-4号B1楼158号(近国际会展中心)（提货时间为：12:00-20:00）\n2.静安寺点：上海市静安区北京西路1701号静安中华大厦305室（靠近久光百货，地铁2号线静安寺站出口，步行约10分钟）（提货时间周一至周五：12:00-17:30） \n3 月星环球港店：普陀区中山北路3300号月星环球港4楼L4051室（提货时间为：12:00-20:00）\n电话预约时请告知团购验证码，提货时间和确定的提货地点，若提货时间和地点有变动，为保证蛋糕的美味和新鲜，务必提前1天电话告知，并且商家会做好您的信息改动记录\n拿破仑蛋糕保鲜及最佳食用时间为当天；若您是使用于生日蛋糕，可提前告知商家，将会免费为您提供数字生日蜡烛\n \n温馨提示 \n不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n \n \n"},"notice":"","deal_url":"http://dpurl.cn/p/gDbP5obfsx","deal_h5_url":"http://dpurl.cn/p/5bq8ZY4jCa","commission_ratio":0.03,"businesses":[{"name":"Le Camelia(月星环球港店)","id":13668043,"city":"上海","address":"中山北路3300号月星环球港4楼L4051室","latitude":31.23251,"longitude":121.41204,"url":"http://dpurl.cn/p/mE0A2CNMJv","h5_url":"http://dpurl.cn/p/F568FsEEAY"},{"name":"Le Camelia(喜玛拉雅中心店)","id":5710539,"city":"上海","address":"芳甸路1188弄证大喜玛拉雅中心1-4号B1楼158号","latitude":31.21005,"longitude":121.56168,"url":"http://dpurl.cn/p/O7qU9ZrzaV","h5_url":"http://dpurl.cn/p/S+3UKW9Is+"}]}]
     */

    private String status;
    private int count;
    private List<DealsBean> deals;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(List<DealsBean> deals) {
        this.deals = deals;
    }

    public static class DealsBean {
        /**
         * deal_id : 1-5097286
         * title : Le Camelia法式浪漫拿破仑
         * description : 仅售138元,价值238元法式浪漫拿破仑1个!2店通用!月星环球港新店开业!法式甜品经典,三十多层香脆起酥皮,融合清新奶油,口感层次丰富,幸福甜蜜妙不可言!
         * city : 上海
         * list_price : 238
         * current_price : 138
         * regions : ["世纪公园","月星环球港"]
         * categories : ["面包甜点"]
         * purchase_count : 241
         * purchase_deadline : 2014-01-23
         * publish_date : 2013-10-24
         * details : 团购详情
         凭大众点评网团购券可享受以下内容：

         - 法式浪漫拿破仑（1个，价值238元）
         16cm*16cm，约2磅左右
         - 精美蛋糕刀叉（1套，赠送）
         含5个盘子、5个叉子、5张餐巾纸、1把餐刀、蛋糕盒1个、精美拎袋1个


         * image_url : http://t2.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x280)/thumb.jpg
         * s_image_url : http://t1.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(160x100)/thumb.jpg
         * more_image_urls : ["http://t3.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x1024)/thumb.jpg","http://t3.s1.dpfile.com/pc/mc/cffd005b1a94a55cb59fb8494ea09804(450x1024)/aD0xMDI0Jms9L3BjL21jL2NmZmQwMDViMWE5NGE1NWNiNTlmYjg0OTRlYTA5ODA0JmxvZ289MCZtPXgmdz00NTA.a57d8a56f07afd7ce322cc6abbcac896/thumb.jpg","http://t2.s2.dpfile.com/pc/mc/004b104c126a4319a55ac0f999d44419(450x1024)/thumb.jpg"]
         * more_s_image_urls : ["http://t3.s2.dpfile.com/pc/mc/e75efd4b642251e21af2691a9df77399(450x1024)/thumb_1.jpg","http://t3.s1.dpfile.com/pc/mc/cffd005b1a94a55cb59fb8494ea09804(450x1024)/aD0xMDI0Jms9L3BjL21jL2NmZmQwMDViMWE5NGE1NWNiNTlmYjg0OTRlYTA5ODA0JmxvZ289MCZtPXgmdz00NTA.a57d8a56f07afd7ce322cc6abbcac896/thumb_1.jpg","http://t2.s2.dpfile.com/pc/mc/004b104c126a4319a55ac0f999d44419(450x1024)/thumb_1.jpg"]
         * is_popular : 1
         * restrictions : {"is_reservation_required":1,"is_refundable":1,"special_tips":"购买须知\n \n有效期 \n2013-10-25 至 2014-01-24\n \n预约 \n必须至少提前1-2天致电商家预约\n \n使用须知 \n仅限外带，外带免费\n仅限到店自提，不提供配送服务\n每天最多接受30张团购预约\n本团购设定3个提货点：\n1.喜玛拉雅中心：浦东新区芳甸路1188弄证大喜玛拉雅中心1-4号B1楼158号(近国际会展中心)（提货时间为：12:00-20:00）\n2.静安寺点：上海市静安区北京西路1701号静安中华大厦305室（靠近久光百货，地铁2号线静安寺站出口，步行约10分钟）（提货时间周一至周五：12:00-17:30） \n3 月星环球港店：普陀区中山北路3300号月星环球港4楼L4051室（提货时间为：12:00-20:00）\n电话预约时请告知团购验证码，提货时间和确定的提货地点，若提货时间和地点有变动，为保证蛋糕的美味和新鲜，务必提前1天电话告知，并且商家会做好您的信息改动记录\n拿破仑蛋糕保鲜及最佳食用时间为当天；若您是使用于生日蛋糕，可提前告知商家，将会免费为您提供数字生日蜡烛\n \n温馨提示 \n不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n \n \n"}
         * notice :
         * deal_url : http://dpurl.cn/p/gDbP5obfsx
         * deal_h5_url : http://dpurl.cn/p/5bq8ZY4jCa
         * commission_ratio : 0.03
         * businesses : [{"name":"Le Camelia(月星环球港店)","id":13668043,"city":"上海","address":"中山北路3300号月星环球港4楼L4051室","latitude":31.23251,"longitude":121.41204,"url":"http://dpurl.cn/p/mE0A2CNMJv","h5_url":"http://dpurl.cn/p/F568FsEEAY"},{"name":"Le Camelia(喜玛拉雅中心店)","id":5710539,"city":"上海","address":"芳甸路1188弄证大喜玛拉雅中心1-4号B1楼158号","latitude":31.21005,"longitude":121.56168,"url":"http://dpurl.cn/p/O7qU9ZrzaV","h5_url":"http://dpurl.cn/p/S+3UKW9Is+"}]
         */

        private String deal_id;
        private String title;
        private String description;
        private String city;
        private int list_price;
        private int current_price;
        private int purchase_count;
        private String purchase_deadline;
        private String publish_date;
        private String details;
        private String image_url;
        private String s_image_url;
        private int is_popular;
        private RestrictionsBean restrictions;
        private String notice;
        private String deal_url;
        private String deal_h5_url;
        private double commission_ratio;
        private List<String> regions;
        private List<String> categories;
        private List<String> more_image_urls;
        private List<String> more_s_image_urls;
        private List<BusinessesBean> businesses;

        public String getDeal_id() {
            return deal_id;
        }

        public void setDeal_id(String deal_id) {
            this.deal_id = deal_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getList_price() {
            return list_price;
        }

        public void setList_price(int list_price) {
            this.list_price = list_price;
        }

        public int getCurrent_price() {
            return current_price;
        }

        public void setCurrent_price(int current_price) {
            this.current_price = current_price;
        }

        public int getPurchase_count() {
            return purchase_count;
        }

        public void setPurchase_count(int purchase_count) {
            this.purchase_count = purchase_count;
        }

        public String getPurchase_deadline() {
            return purchase_deadline;
        }

        public void setPurchase_deadline(String purchase_deadline) {
            this.purchase_deadline = purchase_deadline;
        }

        public String getPublish_date() {
            return publish_date;
        }

        public void setPublish_date(String publish_date) {
            this.publish_date = publish_date;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getS_image_url() {
            return s_image_url;
        }

        public void setS_image_url(String s_image_url) {
            this.s_image_url = s_image_url;
        }

        public int getIs_popular() {
            return is_popular;
        }

        public void setIs_popular(int is_popular) {
            this.is_popular = is_popular;
        }

        public RestrictionsBean getRestrictions() {
            return restrictions;
        }

        public void setRestrictions(RestrictionsBean restrictions) {
            this.restrictions = restrictions;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getDeal_url() {
            return deal_url;
        }

        public void setDeal_url(String deal_url) {
            this.deal_url = deal_url;
        }

        public String getDeal_h5_url() {
            return deal_h5_url;
        }

        public void setDeal_h5_url(String deal_h5_url) {
            this.deal_h5_url = deal_h5_url;
        }

        public double getCommission_ratio() {
            return commission_ratio;
        }

        public void setCommission_ratio(double commission_ratio) {
            this.commission_ratio = commission_ratio;
        }

        public List<String> getRegions() {
            return regions;
        }

        public void setRegions(List<String> regions) {
            this.regions = regions;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<String> getMore_image_urls() {
            return more_image_urls;
        }

        public void setMore_image_urls(List<String> more_image_urls) {
            this.more_image_urls = more_image_urls;
        }

        public List<String> getMore_s_image_urls() {
            return more_s_image_urls;
        }

        public void setMore_s_image_urls(List<String> more_s_image_urls) {
            this.more_s_image_urls = more_s_image_urls;
        }

        public List<BusinessesBean> getBusinesses() {
            return businesses;
        }

        public void setBusinesses(List<BusinessesBean> businesses) {
            this.businesses = businesses;
        }

        public static class RestrictionsBean {
            /**
             * is_reservation_required : 1
             * is_refundable : 1
             * special_tips : 购买须知

             有效期
             2013-10-25 至 2014-01-24

             预约
             必须至少提前1-2天致电商家预约

             使用须知
             仅限外带，外带免费
             仅限到店自提，不提供配送服务
             每天最多接受30张团购预约
             本团购设定3个提货点：
             1.喜玛拉雅中心：浦东新区芳甸路1188弄证大喜玛拉雅中心1-4号B1楼158号(近国际会展中心)（提货时间为：12:00-20:00）
             2.静安寺点：上海市静安区北京西路1701号静安中华大厦305室（靠近久光百货，地铁2号线静安寺站出口，步行约10分钟）（提货时间周一至周五：12:00-17:30）
             3 月星环球港店：普陀区中山北路3300号月星环球港4楼L4051室（提货时间为：12:00-20:00）
             电话预约时请告知团购验证码，提货时间和确定的提货地点，若提货时间和地点有变动，为保证蛋糕的美味和新鲜，务必提前1天电话告知，并且商家会做好您的信息改动记录
             拿破仑蛋糕保鲜及最佳食用时间为当天；若您是使用于生日蛋糕，可提前告知商家，将会免费为您提供数字生日蜡烛

             温馨提示
             不可与其他优惠同享
             如需团购券发票，请在消费时向商户提出



             */

            private int is_reservation_required;
            private int is_refundable;
            private String special_tips;

            public int getIs_reservation_required() {
                return is_reservation_required;
            }

            public void setIs_reservation_required(int is_reservation_required) {
                this.is_reservation_required = is_reservation_required;
            }

            public int getIs_refundable() {
                return is_refundable;
            }

            public void setIs_refundable(int is_refundable) {
                this.is_refundable = is_refundable;
            }

            public String getSpecial_tips() {
                return special_tips;
            }

            public void setSpecial_tips(String special_tips) {
                this.special_tips = special_tips;
            }
        }

        public static class BusinessesBean {
            /**
             * name : Le Camelia(月星环球港店)
             * id : 13668043
             * city : 上海
             * address : 中山北路3300号月星环球港4楼L4051室
             * latitude : 31.23251
             * longitude : 121.41204
             * url : http://dpurl.cn/p/mE0A2CNMJv
             * h5_url : http://dpurl.cn/p/F568FsEEAY
             */

            private String name;
            private int id;
            private String city;
            private String address;
            private double latitude;
            private double longitude;
            private String url;
            private String h5_url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getH5_url() {
                return h5_url;
            }

            public void setH5_url(String h5_url) {
                this.h5_url = h5_url;
            }
        }
    }
}
