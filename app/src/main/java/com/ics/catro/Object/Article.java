package com.ics.catro.Object;

public class Article {
    private int id_article;
    private String article;
    private String gambar;
    private String tgl_posting;
    private String username;
    private int isLiked;

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

    public int getIsLiked() {
        return isLiked;
    }

    public String getTgl_posting() {
        return tgl_posting;
    }
}
