package com.kit.backpackers.project_kit.Utils;

/**
 * Created by Naila Kayani on 5/4/2017.
 */
public class LocationVo {

    private double mLatitude, mLongitude;
    private int mLocId;
    private String mLocAddress;

    public int getmLocId() {
        return mLocId;
    }

    public void setmLocId(int mLocId) {
        this.mLocId = mLocId;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmLocAddress() {
        return mLocAddress;
    }

    public void setmLocAddress(String mLocAddress) {
        this.mLocAddress = mLocAddress;
    }
}
