package com.ics.catro.Object;


import com.ics.catro.View.ArticleFragment;

import java.util.List;

/**
 * Created by Ichsan.Fatiha on 3/9/2018.
 */

public class Value {
    String value;
    String message;
    List<Profile> profile;
    List<Article> article;

    public List<Article> getArticle() {
        return article;
    }

    public List<Profile> getProfileList() {
        return profile;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
