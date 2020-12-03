package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class modelSepeda  implements Parcelable {

    private String id;
    private String kode;
    private String jenis;
    private String merk;
    private String warna;
    private String hargasewa;

    protected modelSepeda(Parcel in) {
        id = in.readString();
        kode = in.readString();
        jenis = in.readString();
        merk = in.readString();
        warna = in.readString();
        hargasewa = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getHargasewa() {
        return hargasewa;
    }

    public void setHargasewa(String hargasewa) {
        this.hargasewa = hargasewa;
    }

    public static final Creator<modelSepeda> CREATOR = new Creator<modelSepeda>() {
        @Override
        public modelSepeda createFromParcel(Parcel in) {
            return new modelSepeda(in);
        }

        @Override
        public modelSepeda[] newArray(int size) {
            return new modelSepeda[size];
        }
    };
    public modelSepeda() {

    }

    public static Creator<modelSepeda> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(kode);
        parcel.writeString(jenis);
        parcel.writeString(merk);
        parcel.writeString(warna);
        parcel.writeString(hargasewa);
    }
}
