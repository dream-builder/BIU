package com.bitsofttech.biu;

/**
 * Created by Shahed on 4/8/2017.
 */

public class NewsModel {
    String NewsTitle;
    String NewsDetail;
    String NewsAttachment;
    String NewsID;
    String NewsServerPostID;
    String NewsDate;

    public String getNewsCategory() {
        return NewsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        NewsCategory = newsCategory;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String newsDate) {
        NewsDate = newsDate;
    }

    String NewsCategory;



    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDetail() {
        return NewsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        NewsDetail = newsDetail;
    }

    public String getNewsAttachment() {
        return NewsAttachment;
    }

    public void setNewsAttachment(String newsAttachment) {
        NewsAttachment = newsAttachment;
    }



    public String getNewsID() {
        return NewsID;
    }

    public void setNewsID(String newsID) {
        NewsID = newsID;
    }

    public String getNewsServerPostID() {
        return NewsServerPostID;
    }

    public void setNewsServerPostID(String newsServerPostID) {
        NewsServerPostID = newsServerPostID;
    }
}
