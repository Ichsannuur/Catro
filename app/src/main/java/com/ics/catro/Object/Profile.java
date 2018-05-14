package com.ics.catro.Object;

/**
 * Created by Ichsan.Fatiha on 3/15/2018.
 */

public class Profile {
    int id_article;
    String article;
    String gambar;
    String username;
    String tgl_posting;
    int followers;
    int followed;
    String score;

    public String getScore() {
        return score;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowed() {
        return followed;
    }

    public String getUsername() {
        return username;
    }

    public int getId_article() {
        return id_article;
    }

    public String getArticle() {
        return article;
    }

    public String getGambar() {
        return gambar;
    }

    public String getTgl_posting() {
        return tgl_posting;
    }
}
