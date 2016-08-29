package com.jason.listviewtest.model;

import java.util.List;

/**
 * Created by Jason on 2016/8/27.
 */
public class SpotTicketInfoFromQUNAER {

    /**
     * errNum : 0
     * errMsg : success
     * retData : {"id":"1361653183","ticketDetail":{"loc":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&bd_ref_id=","lastmod":"2016-08-28","changefreq":"hourly","priority":"1.0","data":{"display":{"ticket":{"spotName":"白云温泉山庄","alias":"佛冈白云温泉山庄","spotID":"qn_35203","description":"清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...","detailUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=","address":"广东省清远市佛冈县汤塘黄花湖旅游区","province":"广东","city":"清远","coordinates":"113.511236_23.770184","imageUrl":"http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=","comments":"0","priceList":[{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]},"category":"lvyou","subcate":"ticket","source":"去哪儿门票"}}}}
     */

    private String errMsg;
    /**
     * id : 1361653183
     * ticketDetail : {"loc":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&bd_ref_id=","lastmod":"2016-08-28","changefreq":"hourly","priority":"1.0","data":{"display":{"ticket":{"spotName":"白云温泉山庄","alias":"佛冈白云温泉山庄","spotID":"qn_35203","description":"清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...","detailUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=","address":"广东省清远市佛冈县汤塘黄花湖旅游区","province":"广东","city":"清远","coordinates":"113.511236_23.770184","imageUrl":"http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=","comments":"0","priceList":[{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]},"category":"lvyou","subcate":"ticket","source":"去哪儿门票"}}}
     */

    private RetDataBean retData;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String id;
        /**
         * loc : http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&bd_ref_id=
         * lastmod : 2016-08-28
         * changefreq : hourly
         * priority : 1.0
         * data : {"display":{"ticket":{"spotName":"白云温泉山庄","alias":"佛冈白云温泉山庄","spotID":"qn_35203","description":"清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...","detailUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=","address":"广东省清远市佛冈县汤塘黄花湖旅游区","province":"广东","city":"清远","coordinates":"113.511236_23.770184","imageUrl":"http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=","comments":"0","priceList":[{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]},"category":"lvyou","subcate":"ticket","source":"去哪儿门票"}}
         */

        private TicketDetailBean ticketDetail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public TicketDetailBean getTicketDetail() {
            return ticketDetail;
        }

        public void setTicketDetail(TicketDetailBean ticketDetail) {
            this.ticketDetail = ticketDetail;
        }

        public static class TicketDetailBean {
            private String loc;
            private String lastmod;
            private String changefreq;
            private String priority;
            /**
             * display : {"ticket":{"spotName":"白云温泉山庄","alias":"佛冈白云温泉山庄","spotID":"qn_35203","description":"清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...","detailUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=","address":"广东省清远市佛冈县汤塘黄花湖旅游区","province":"广东","city":"清远","coordinates":"113.511236_23.770184","imageUrl":"http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=","comments":"0","priceList":[{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]},"category":"lvyou","subcate":"ticket","source":"去哪儿门票"}
             */

            private DataBean data;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getLastmod() {
                return lastmod;
            }

            public void setLastmod(String lastmod) {
                this.lastmod = lastmod;
            }

            public String getChangefreq() {
                return changefreq;
            }

            public void setChangefreq(String changefreq) {
                this.changefreq = changefreq;
            }

            public String getPriority() {
                return priority;
            }

            public void setPriority(String priority) {
                this.priority = priority;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * ticket : {"spotName":"白云温泉山庄","alias":"佛冈白云温泉山庄","spotID":"qn_35203","description":"清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...","detailUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=","address":"广东省清远市佛冈县汤塘黄花湖旅游区","province":"广东","city":"清远","coordinates":"113.511236_23.770184","imageUrl":"http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=","comments":"0","priceList":[{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]}
                 * category : lvyou
                 * subcate : ticket
                 * source : 去哪儿门票
                 */

                private DisplayBean display;

                public DisplayBean getDisplay() {
                    return display;
                }

                public void setDisplay(DisplayBean display) {
                    this.display = display;
                }

                public static class DisplayBean {
                    /**
                     * spotName : 白云温泉山庄
                     * alias : 佛冈白云温泉山庄
                     * spotID : qn_35203
                     * description : 清远佛冈白云温泉山庄座落在著名的汤塘黄花湖旅游区内，临黄花湖而建，山庄花草树木、柴门木屋、假山瀑布，以清风、明月、白云、山水泳池，氡温泉构成独有的文化底蕴，给人以轻松、自然、 舒适的旅游感受。...
                     * detailUrl : http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&ex_track=bd_apistore_menpiao_detail&bd_ref_id=
                     * address : 广东省清远市佛冈县汤塘黄花湖旅游区
                     * province : 广东
                     * city : 清远
                     * coordinates : 113.511236_23.770184
                     * imageUrl : http://img1.qunarzz.com/sight/p0/201212/18/2d277c11ef09934e93835fbb.jpg?bd_ref_id=_512x512_e2dadf52.jpg?bd_ref_id=
                     * comments : 0
                     * priceList : [{"ticketTitle":"白云温泉成人票","ticketID":"3061","priceType":"0","price":"46","normalPrice":"100","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id="},{"ticketTitle":"白云温泉成人票+正餐","ticketID":"235216","priceType":"0","price":"88","normalPrice":"168","num":"-1","type":"0","bookUrl":"http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=235216&ex_track=bd_apistore_menpiao_detail&bd_ref_id="}]
                     */

                    private TicketBean ticket;
                    private String category;
                    private String subcate;
                    private String source;

                    public TicketBean getTicket() {
                        return ticket;
                    }

                    public void setTicket(TicketBean ticket) {
                        this.ticket = ticket;
                    }

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public String getSubcate() {
                        return subcate;
                    }

                    public void setSubcate(String subcate) {
                        this.subcate = subcate;
                    }

                    public String getSource() {
                        return source;
                    }

                    public void setSource(String source) {
                        this.source = source;
                    }

                    public static class TicketBean {
                        private String spotName;
                        private String alias;
                        private String spotID;
                        private String description;
                        private String detailUrl;
                        private String address;
                        private String province;
                        private String city;
                        private String coordinates;
                        private String imageUrl;
                        private String comments;
                        /**
                         * ticketTitle : 白云温泉成人票
                         * ticketID : 3061
                         * priceType : 0
                         * price : 46
                         * normalPrice : 100
                         * num : -1
                         * type : 0
                         * bookUrl : http://touch.piao.qunar.com/touch/detail.htm?id=1361653183&tid=3061&ex_track=bd_apistore_menpiao_detail&bd_ref_id=
                         */

                        private List<PriceListBean> priceList;

                        public String getSpotName() {
                            return spotName;
                        }

                        public void setSpotName(String spotName) {
                            this.spotName = spotName;
                        }

                        public String getAlias() {
                            return alias;
                        }

                        public void setAlias(String alias) {
                            this.alias = alias;
                        }

                        public String getSpotID() {
                            return spotID;
                        }

                        public void setSpotID(String spotID) {
                            this.spotID = spotID;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public String getDetailUrl() {
                            return detailUrl;
                        }

                        public void setDetailUrl(String detailUrl) {
                            this.detailUrl = detailUrl;
                        }

                        public String getAddress() {
                            return address;
                        }

                        public void setAddress(String address) {
                            this.address = address;
                        }

                        public String getProvince() {
                            return province;
                        }

                        public void setProvince(String province) {
                            this.province = province;
                        }

                        public String getCity() {
                            return city;
                        }

                        public void setCity(String city) {
                            this.city = city;
                        }

                        public String getCoordinates() {
                            return coordinates;
                        }

                        public void setCoordinates(String coordinates) {
                            this.coordinates = coordinates;
                        }

                        public String getImageUrl() {
                            return imageUrl;
                        }

                        public void setImageUrl(String imageUrl) {
                            this.imageUrl = imageUrl;
                        }

                        public String getComments() {
                            return comments;
                        }

                        public void setComments(String comments) {
                            this.comments = comments;
                        }

                        public List<PriceListBean> getPriceList() {
                            return priceList;
                        }

                        public void setPriceList(List<PriceListBean> priceList) {
                            this.priceList = priceList;
                        }

                        public static class PriceListBean {
                            private String ticketTitle;
                            private String ticketID;
                            private String priceType;
                            private String price;
                            private String normalPrice;
                            private String num;
                            private String type;
                            private String bookUrl;

                            public String getTicketTitle() {
                                return ticketTitle;
                            }

                            public void setTicketTitle(String ticketTitle) {
                                this.ticketTitle = ticketTitle;
                            }

                            public String getTicketID() {
                                return ticketID;
                            }

                            public void setTicketID(String ticketID) {
                                this.ticketID = ticketID;
                            }

                            public String getPriceType() {
                                return priceType;
                            }

                            public void setPriceType(String priceType) {
                                this.priceType = priceType;
                            }

                            public String getPrice() {
                                return price;
                            }

                            public void setPrice(String price) {
                                this.price = price;
                            }

                            public String getNormalPrice() {
                                return normalPrice;
                            }

                            public void setNormalPrice(String normalPrice) {
                                this.normalPrice = normalPrice;
                            }

                            public String getNum() {
                                return num;
                            }

                            public void setNum(String num) {
                                this.num = num;
                            }

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public String getBookUrl() {
                                return bookUrl;
                            }

                            public void setBookUrl(String bookUrl) {
                                this.bookUrl = bookUrl;
                            }
                        }
                    }
                }
            }
        }
    }
}

