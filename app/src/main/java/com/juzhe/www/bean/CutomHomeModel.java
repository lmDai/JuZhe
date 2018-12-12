package com.juzhe.www.bean;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/12
 * @description:
 **/
public class CutomHomeModel {

    /**
     * result : true
     * data : [{"searchBar":[{"left":{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":11}},"center":{"backgroundImage":"","foregroundImage":"","mode":{"type":"haodanku","id":22}},"right":{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png","mode":{"type":"haodanku","id":33}}}]},{"banner":[{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":99}},{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":98}},{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png","mode":{"type":"haodanku","id":97}}]},{"searchBar":[{"center":{"backgroundImage":"","foregroundImage":"","mode":{"type":"haodanku","id":22}},"right":{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png","mode":{"type":"haodanku","id":33}}}]},{"menu":[{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"女装1","subItem":[{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装1","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装2","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装3","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装4","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装5","mode":{"type":"haodanku","id":97}}]},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"女装1","subItem":[{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装1","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装2","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装3","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装4","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装5","mode":{"type":"haodanku","id":97}}]}]},{"advert":[{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"女装","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"女装","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"女装","mode":{"type":"haodanku","id":97}}]},{"banner":[{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":99}},{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":98}},{"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png","mode":{"type":"haodanku","id":97}}]}]
     */

    private boolean result;
    private List<DataBean> data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SearchBarBean> searchBar;
        private List<BannerBean> banner;
        private List<MenuBean> menu;
        private List<AdvertBean> advert;

        public List<SearchBarBean> getSearchBar() {
            return searchBar;
        }

        public void setSearchBar(List<SearchBarBean> searchBar) {
            this.searchBar = searchBar;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class SearchBarBean {
            /**
             * left : {"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png","mode":{"type":"haodanku","id":11}}
             * center : {"backgroundImage":"","foregroundImage":"","mode":{"type":"haodanku","id":22}}
             * right : {"image":"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png","mode":{"type":"haodanku","id":33}}
             */

            private LeftBean left;
            private CenterBean center;
            private RightBean right;

            public LeftBean getLeft() {
                return left;
            }

            public void setLeft(LeftBean left) {
                this.left = left;
            }

            public CenterBean getCenter() {
                return center;
            }

            public void setCenter(CenterBean center) {
                this.center = center;
            }

            public RightBean getRight() {
                return right;
            }

            public void setRight(RightBean right) {
                this.right = right;
            }

            public static class LeftBean {
                /**
                 * image : http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png
                 * mode : {"type":"haodanku","id":11}
                 */

                private String image;
                private ModeBean mode;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public ModeBean getMode() {
                    return mode;
                }

                public void setMode(ModeBean mode) {
                    this.mode = mode;
                }

                public static class ModeBean {
                    /**
                     * type : haodanku
                     * id : 11
                     */

                    private String type;
                    private int id;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }

            public static class CenterBean {
                /**
                 * backgroundImage :
                 * foregroundImage :
                 * mode : {"type":"haodanku","id":22}
                 */

                private String backgroundImage;
                private String foregroundImage;
                private ModeBeanX mode;

                public String getBackgroundImage() {
                    return backgroundImage;
                }

                public void setBackgroundImage(String backgroundImage) {
                    this.backgroundImage = backgroundImage;
                }

                public String getForegroundImage() {
                    return foregroundImage;
                }

                public void setForegroundImage(String foregroundImage) {
                    this.foregroundImage = foregroundImage;
                }

                public ModeBeanX getMode() {
                    return mode;
                }

                public void setMode(ModeBeanX mode) {
                    this.mode = mode;
                }

                public static class ModeBeanX {
                    /**
                     * type : haodanku
                     * id : 22
                     */

                    private String type;
                    private int id;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }

            public static class RightBean {
                /**
                 * image : http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png
                 * mode : {"type":"haodanku","id":33}
                 */

                private String image;
                private ModeBeanXX mode;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public ModeBeanXX getMode() {
                    return mode;
                }

                public void setMode(ModeBeanXX mode) {
                    this.mode = mode;
                }

                public static class ModeBeanXX {
                    /**
                     * type : haodanku
                     * id : 33
                     */

                    private String type;
                    private int id;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }
        }

        public static class BannerBean {
            /**
             * image : http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png
             * mode : {"type":"haodanku","id":99}
             */

            private String image;
            private ModeBeanXXX mode;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public ModeBeanXXX getMode() {
                return mode;
            }

            public void setMode(ModeBeanXXX mode) {
                this.mode = mode;
            }

            public static class ModeBeanXXX {
                /**
                 * type : haodanku
                 * id : 99
                 */

                private String type;
                private int id;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }

        public static class MenuBean {
            /**
             * image : http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg
             * title : 女装1
             * subItem : [{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装1","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装2","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装3","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装4","mode":{"type":"haodanku","id":97}},{"image":"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg","title":"sub女装5","mode":{"type":"haodanku","id":97}}]
             */

            private String image;
            private String title;
            private List<SubItemBean> subItem;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<SubItemBean> getSubItem() {
                return subItem;
            }

            public void setSubItem(List<SubItemBean> subItem) {
                this.subItem = subItem;
            }

            public static class SubItemBean {
                /**
                 * image : http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg
                 * title : sub女装1
                 * mode : {"type":"haodanku","id":97}
                 */

                private String image;
                private String title;
                private ModeBeanXXXX mode;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public ModeBeanXXXX getMode() {
                    return mode;
                }

                public void setMode(ModeBeanXXXX mode) {
                    this.mode = mode;
                }

                public static class ModeBeanXXXX {
                    /**
                     * type : haodanku
                     * id : 97
                     */

                    private String type;
                    private int id;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }
        }

        public static class AdvertBean {
            /**
             * image : http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg
             * title : 女装
             * mode : {"type":"haodanku","id":97}
             */

            private String image;
            private String title;
            private ModeBeanXXXXX mode;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public ModeBeanXXXXX getMode() {
                return mode;
            }

            public void setMode(ModeBeanXXXXX mode) {
                this.mode = mode;
            }

            public static class ModeBeanXXXXX {
                /**
                 * type : haodanku
                 * id : 97
                 */

                private String type;
                private int id;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
