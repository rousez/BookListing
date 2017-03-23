package com.zrouse.booklisting;

public class Book {

    private String[] mAuthors;
    private String mTitle;

    public Book(String[] authors, String title) {
        mAuthors = authors;
        mTitle = title;
    }

    public String[] getAuthor() {
        return mAuthors;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setAuthors(String[] authors) {
         this.mAuthors = authors;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

}
