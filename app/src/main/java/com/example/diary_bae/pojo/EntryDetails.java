package com.example.diary_bae.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class EntryDetails implements Parcelable {
    private int id;
    private String entryTitle;
    private String entryDate;
    private String entryTime;

    public EntryDetails(int id, String name, String dob, String department) {
        this.id = id;
        this.entryTitle = name;
        this.entryDate = dob;
        this.entryTime = department;
    }

    protected EntryDetails(Parcel in) {
        id = in.readInt();
        entryTitle = in.readString();
        entryDate = in.readString();
        entryTime = in.readString();
    }

    public static final Creator<EntryDetails> CREATOR = new Creator<EntryDetails>() {
        @Override
        public EntryDetails createFromParcel(Parcel in) {
            return new EntryDetails(in);
        }

        @Override
        public EntryDetails[] newArray(int size) {
            return new EntryDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.entryTitle);
        dest.writeString(this.entryDate);
        dest.writeString(this.entryTime);
    }
}
