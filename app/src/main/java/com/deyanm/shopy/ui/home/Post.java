package com.deyanm.shopy.ui.home;

public class Post {
    private String profilepicUrl;
    private String postdate;
    private String postpicUrl;
    private String postDesc;
    private String name;
    private int total_likes;
    private int total_comment;
    private String postID;
    private String userid;
    private String city;
    private String category;

    public Post(String name, String category, String postdate, String postDesc, String city, String userid) {
        this.name = name;
        this.category = category;
        this.postdate = postdate;
        this.postDesc = postDesc;
        this.postID = postID;
        this.userid = userid;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getProfilepicUrl() {
        return profilepicUrl;
    }

    public void setProfilepicUrl(String profilepicUrl) {
        this.profilepicUrl = profilepicUrl;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getPostpicUrl() {
        return postpicUrl;
    }

    public void setPostpicUrl(String postpicUrl) {
        this.postpicUrl = postpicUrl;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(int total_comment) {
        this.total_comment = total_comment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
