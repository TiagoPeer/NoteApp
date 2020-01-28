package com.xstudioo.noteme;

public class Note {
    private long mId;
    private String mTitle;
    private String mContent;
    private String mDate;
    private String mTime;
    private int mNumberTimesEdited;



    public Note(String pTitle, String pContent, String pDate, String pTime, int pNumberTimesEdited) {
        this.mTitle = pTitle;
        this.mContent = pContent;
        this.mDate = pDate;
        this.mTime = pTime;
        this.mNumberTimesEdited = pNumberTimesEdited;
    }

    public Note(long id, String title, String content, String date, String time, int pNumberTimesEdited) {
        this.mId = id;
        this.mTitle = title;
        this.mContent = content;
        this.mDate = date;
        this.mTime = time;
        this.mNumberTimesEdited = pNumberTimesEdited;
    }

    public Note() {
        // empty constructor
    }

    public long getId() {
        return mId;
    }

    public void setId(long pId) {
        this.mId = pId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        this.mTitle = pTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String pContent) {
        this.mContent = pContent;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String pDate) {
        this.mDate = pDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String pTime) {
        this.mTime = pTime;
    }

    public int getNumberTimesEdited() {
        return mNumberTimesEdited;
    }

    public void setNumberTimesEdited(int pNumberTimesEdited) {
        this.mNumberTimesEdited = pNumberTimesEdited;
    }
}
