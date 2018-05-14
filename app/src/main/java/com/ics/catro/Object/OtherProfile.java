package com.ics.catro.Object;

/**
 * Created by Ichsan.Fatiha on 5/11/2018.
 */

public class OtherProfile {
    private int id_article;
    private String article;
    private String image;
    private String tgl_posting;
    private String time_posting;
    private String username;
    private String email;
    private String user_image;
    private int followers;
    private int followed;
    private String score;

    public String getScore() {
        return score;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowed() {
        return followed;
    }

    public int getId_article() {
        return id_article;
    }

    public String getArticle() {
        return article;
    }

    public String getImage() {
        return image;
    }

    public String getTgl_posting() {
        return tgl_posting;
    }

    public String getTime_posting() {
        return time_posting;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_image() {
        return user_image;
    }
}
