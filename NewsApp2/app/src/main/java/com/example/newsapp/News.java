package com.example.newsapp;

public class News {
    private String mId;
    private String mType;
    private String mWebPublicationDate;
    private String mWebTitle;
    private String mWebUrl;
    private String msectionName;

    public News(String Id,String Type, String WebPublicationDate, String WebTitle, String WebUrl,String sectionName) {
        this.mId=Id;
        this.mType = Type;
        this.mWebPublicationDate = WebPublicationDate;
        this.mWebTitle = WebTitle;
        this.mWebUrl = WebUrl;
        this.msectionName=sectionName;
    }

    public String getType() {
        return mType;
    }

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getSectionName() {
        return msectionName;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getId() {
        return mId;
    }
}
