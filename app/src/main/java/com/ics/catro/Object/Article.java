package com.ics.catro.Object;

public class Article {
    private int id_article;
    private String article;
    private String gambar;
    private String tgl_posting;
    private String time_posting;
    private String user_image;
    private String email;
    private String username;
    private int isLiked;
    private int countLiked;
    private int id_like;

    public String getUser_image() {
        return user_image;
    }

    public int getId_like() {
        return id_like;
    }

    public int getCountLiked() {
        return countLiked;
    }

    public String getUsername() {
        return username;
    }

    public int getId_article() {
        return id_article;
    }

    public String getTime_posting() {
        return time_posting;
    }

    public String getArticle() {
        return article;
    }

    public String getEmail() {
        return email;
    }

    public String getGambar() {
        return gambar;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public String getTgl_posting() {
        return tgl_posting;
    }
}
