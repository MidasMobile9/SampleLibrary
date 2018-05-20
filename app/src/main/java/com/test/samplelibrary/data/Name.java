package com.test.samplelibrary.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Name implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Name createFromParcel(Parcel src) {
            return new Name(src);
        }

        public Name[] newArray(int size) {
            return new Name[size];
        }
    };

    @SerializedName("no")
    private int no;
    @SerializedName("name")
    private String name;

    public Name() { }

    public Name(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public Name(Parcel src) {
        no = src.readInt();
        name = src.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(name);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
