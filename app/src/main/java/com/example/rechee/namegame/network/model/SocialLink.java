package com.example.rechee.namegame.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rechee on 11/11/2017.
 */

public class SocialLink implements Parcelable {
    private String type;
    private String callToAction;
    private String url;

    public SocialLink(Parcel in) {
        type = in.readString();
        callToAction = in.readString();
        url = in.readString();
    }

    public static final Creator<SocialLink> CREATOR = new Creator<SocialLink>() {
        @Override
        public SocialLink createFromParcel(Parcel in) {
            return new SocialLink(in);
        }

        @Override
        public SocialLink[] newArray(int size) {
            return new SocialLink[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public String getUrl() {

        if(url == null){
            return "";
        }

        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.callToAction);
        dest.writeString(this.url);
    }
}
