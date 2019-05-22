package com.example.cs639springhw5;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimalDisplay implements Parcelable {

    private int mIcon;
    private String mName;
    private String mBio;
    boolean isChecked;

    public AnimalDisplay(int icon, String name, String bio) {
        mIcon = icon;
        mName = name;
        mBio = bio;
        isChecked = true;
    }

    protected AnimalDisplay(Parcel in) {
        mIcon = in.readInt();
        mName = in.readString();
        mBio = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<AnimalDisplay> CREATOR = new Creator<AnimalDisplay>() {
        @Override
        public AnimalDisplay createFromParcel(Parcel in) {
            return new AnimalDisplay(in);
        }

        @Override
        public AnimalDisplay[] newArray(int size) {
            return new AnimalDisplay[size];
        }
    };

    int getIcon() {
        return mIcon;
    }
    String getName() { return mName; }
    String getBio() { return mBio; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIcon);
        dest.writeString(mName);
        dest.writeString(mBio);
        dest.writeByte((byte) (isChecked ? 1 : 0));

    }
}
