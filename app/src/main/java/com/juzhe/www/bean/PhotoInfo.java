package com.juzhe.www.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by litong on 2016/11/17.
 * 九宫格图片实体类
 */
public class PhotoInfo implements Parcelable {

    public String url;
    public int w;
    public int h;

    protected PhotoInfo(Parcel in) {
        url = in.readString();
        w = in.readInt();
        h = in.readInt();
    }

    public PhotoInfo() {
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "url='" + url + '\'' +
                ", w=" + w +
                ", h=" + h +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(w);
        dest.writeInt(h);
    }
}
