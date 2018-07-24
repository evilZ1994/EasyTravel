package me.codekiller.easytravel.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordsResult {
    /**
     * code : 0
     * message : OK
     * data : {"class":[],"angle":1.54296875,"items":[{"itemcoord":{"x":17,"y":327,"width":989,"height":167},"words":[{"character":"安","confidence":0.9999868869781494},{"character":"纳","confidence":0.9999496936798096},{"character":"斯","confidence":0.999909520149231},{"character":"塔","confidence":0.9998189806938172},{"character":"西","confidence":0.9999535083770752},{"character":"奥","confidence":0.9939618706703186}],"itemstring":"安纳斯塔西奥"}],"session_id":"1252662799-1926042801"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * class : []
         * angle : 1.54296875
         * items : [{"itemcoord":{"x":17,"y":327,"width":989,"height":167},"words":[{"character":"安","confidence":0.9999868869781494},{"character":"纳","confidence":0.9999496936798096},{"character":"斯","confidence":0.999909520149231},{"character":"塔","confidence":0.9998189806938172},{"character":"西","confidence":0.9999535083770752},{"character":"奥","confidence":0.9939618706703186}],"itemstring":"安纳斯塔西奥"}]
         * session_id : 1252662799-1926042801
         */

        private double angle;
        private String session_id;
        @SerializedName("class")
        private List<?> classX;
        private List<ItemsBean> items;

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public List<?> getClassX() {
            return classX;
        }

        public void setClassX(List<?> classX) {
            this.classX = classX;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * itemcoord : {"x":17,"y":327,"width":989,"height":167}
             * words : [{"character":"安","confidence":0.9999868869781494},{"character":"纳","confidence":0.9999496936798096},{"character":"斯","confidence":0.999909520149231},{"character":"塔","confidence":0.9998189806938172},{"character":"西","confidence":0.9999535083770752},{"character":"奥","confidence":0.9939618706703186}]
             * itemstring : 安纳斯塔西奥
             */

            private ItemcoordBean itemcoord;
            private String itemstring;
            private List<WordsBean> words;

            public ItemcoordBean getItemcoord() {
                return itemcoord;
            }

            public void setItemcoord(ItemcoordBean itemcoord) {
                this.itemcoord = itemcoord;
            }

            public String getItemstring() {
                return itemstring;
            }

            public void setItemstring(String itemstring) {
                this.itemstring = itemstring;
            }

            public List<WordsBean> getWords() {
                return words;
            }

            public void setWords(List<WordsBean> words) {
                this.words = words;
            }

            public static class ItemcoordBean {
                /**
                 * x : 17
                 * y : 327
                 * width : 989
                 * height : 167
                 */

                private int x;
                private int y;
                private int width;
                private int height;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }

            public static class WordsBean {
                /**
                 * character : 安
                 * confidence : 0.9999868869781494
                 */

                private String character;
                private double confidence;

                public String getCharacter() {
                    return character;
                }

                public void setCharacter(String character) {
                    this.character = character;
                }

                public double getConfidence() {
                    return confidence;
                }

                public void setConfidence(double confidence) {
                    this.confidence = confidence;
                }
            }
        }
    }
}
