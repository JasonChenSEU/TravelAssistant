package com.jason.listviewtest.model.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jason on 2016/8/29.
 */
public class SpotDetailInfoFromBAIDU {

    /**
     * error : 0
     * status : Success
     * date : 2016-08-29
     * result : {"name":"颐和园","location":{"lng":116.26844967549,"lat":39.992182483805},"telephone":"010-62881144","star":"5","url":"http://lvyou.baidu.com/yiheyuan","abstract":"我国现存规模最大、保存最完整的皇家园林，久负盛名。","description":"颐和园位于北京西北郊海淀区内，距北京城区15公里，是我国现存规模最大，保存最完整的皇家园林之一，也是享誉世界的旅游胜地之一。 颐和园是利用昆明湖、万寿山为基址，以杭州西湖风景为蓝本，汲取江南园林的某些设计手法和意境而建成的一座大型天然山水园，也是保存得最完整的一座皇家行宫御苑，被誉为皇家园林博物馆。 颐和园景区规模宏大，园内建筑以佛香阁为中心，园中有景点建筑物百余座、大小院落20余处，3555古建筑，面积70000多平方米，共有亭、台、楼、阁、廊、榭等不同形式的建筑3000多间。古树名木1600余株。其中佛香阁、长廊、石舫、苏州街、十七孔桥、谐趣园、大戏台等都已成为家喻户晓的代表性建筑。","ticket_info":{"price":"1. 旺季（4月1日~10月31日）：30.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：60.00元 2. 淡季（11月1日~3月31日）：20.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：50.00元","open_time":"1. 旺季（4月1日~10月31日）：06:30~20:00 停止售票时间：18:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：08:30~17:00 2. 淡季（11月1日~3月31日）：07:00~19:00 停止售票时间：17:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：09:00~18:00","attention":[{"name":"【门票优惠政策】","description":"1. 身高1.2米以下儿童免票。 2. 北京市65岁以上老年人凭老年证免票；外地70周岁以上（含70周岁）老年人凭有效证件，门票半价优惠。 3. 大、中、小学学生（不含成人教育学生）、外国留学生凭学生证，门票半价优惠。 4. 残疾人、离休干部、离休军人、现役军人、武警官兵、省、部级以上劳模凭有效证件免票。 5. 持有社会保障金领取证的人员凭有效证件，门票半价优惠。"}]}}
     */

    private int error;
    private String status;
    private String date;
    /**
     * name : 颐和园
     * location : {"lng":116.26844967549,"lat":39.992182483805}
     * telephone : 010-62881144
     * star : 5
     * url : http://lvyou.baidu.com/yiheyuan
     * abstract : 我国现存规模最大、保存最完整的皇家园林，久负盛名。
     * description : 颐和园位于北京西北郊海淀区内，距北京城区15公里，是我国现存规模最大，保存最完整的皇家园林之一，也是享誉世界的旅游胜地之一。 颐和园是利用昆明湖、万寿山为基址，以杭州西湖风景为蓝本，汲取江南园林的某些设计手法和意境而建成的一座大型天然山水园，也是保存得最完整的一座皇家行宫御苑，被誉为皇家园林博物馆。 颐和园景区规模宏大，园内建筑以佛香阁为中心，园中有景点建筑物百余座、大小院落20余处，3555古建筑，面积70000多平方米，共有亭、台、楼、阁、廊、榭等不同形式的建筑3000多间。古树名木1600余株。其中佛香阁、长廊、石舫、苏州街、十七孔桥、谐趣园、大戏台等都已成为家喻户晓的代表性建筑。
     * ticket_info : {"price":"1. 旺季（4月1日~10月31日）：30.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：60.00元 2. 淡季（11月1日~3月31日）：20.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：50.00元","open_time":"1. 旺季（4月1日~10月31日）：06:30~20:00 停止售票时间：18:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：08:30~17:00 2. 淡季（11月1日~3月31日）：07:00~19:00 停止售票时间：17:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：09:00~18:00","attention":[{"name":"【门票优惠政策】","description":"1. 身高1.2米以下儿童免票。 2. 北京市65岁以上老年人凭老年证免票；外地70周岁以上（含70周岁）老年人凭有效证件，门票半价优惠。 3. 大、中、小学学生（不含成人教育学生）、外国留学生凭学生证，门票半价优惠。 4. 残疾人、离休干部、离休军人、现役军人、武警官兵、省、部级以上劳模凭有效证件免票。 5. 持有社会保障金领取证的人员凭有效证件，门票半价优惠。"}]}
     */

    private ResultBean result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String name;
        /**
         * lng : 116.26844967549
         * lat : 39.992182483805
         */

        private LocationBean location;
        private String telephone;
        private String star;
        private String url;
        @SerializedName("abstract")
        private String abstractX;
        private String description;
        /**
         * price : 1. 旺季（4月1日~10月31日）：30.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：60.00元 2. 淡季（11月1日~3月31日）：20.00元 德和园：5.00元 佛香阁：10.00元 苏州街：10.00元 文昌院：20.00元 联票（含门票、文昌院、德和园、佛香阁、苏州街澹宁堂）：50.00元
         * open_time : 1. 旺季（4月1日~10月31日）：06:30~20:00 停止售票时间：18:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：08:30~17:00 2. 淡季（11月1日~3月31日）：07:00~19:00 停止售票时间：17:00 园中园（含文昌院、德和园、佛香阁、苏州街澹宁堂）：09:00~18:00
         * attention : [{"name":"【门票优惠政策】","description":"1. 身高1.2米以下儿童免票。 2. 北京市65岁以上老年人凭老年证免票；外地70周岁以上（含70周岁）老年人凭有效证件，门票半价优惠。 3. 大、中、小学学生（不含成人教育学生）、外国留学生凭学生证，门票半价优惠。 4. 残疾人、离休干部、离休军人、现役军人、武警官兵、省、部级以上劳模凭有效证件免票。 5. 持有社会保障金领取证的人员凭有效证件，门票半价优惠。"}]
         */

        private TicketInfoBean ticket_info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TicketInfoBean getTicket_info() {
            return ticket_info;
        }

        public void setTicket_info(TicketInfoBean ticket_info) {
            this.ticket_info = ticket_info;
        }

        public static class LocationBean {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class TicketInfoBean {
            private String price;
            private String open_time;
            /**
             * name : 【门票优惠政策】
             * description : 1. 身高1.2米以下儿童免票。 2. 北京市65岁以上老年人凭老年证免票；外地70周岁以上（含70周岁）老年人凭有效证件，门票半价优惠。 3. 大、中、小学学生（不含成人教育学生）、外国留学生凭学生证，门票半价优惠。 4. 残疾人、离休干部、离休军人、现役军人、武警官兵、省、部级以上劳模凭有效证件免票。 5. 持有社会保障金领取证的人员凭有效证件，门票半价优惠。
             */

            private List<AttentionBean> attention;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public List<AttentionBean> getAttention() {
                return attention;
            }

            public void setAttention(List<AttentionBean> attention) {
                this.attention = attention;
            }

            public static class AttentionBean {
                private String name;
                private String description;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }
    }
}
