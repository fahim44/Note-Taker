package com.astro_monkey.notetaker.model_class;

/**
 * Created by fahim on 04-Mar-15.
 */
public class Notes {

    private Long id;
    private String title;
    private String note;

    public void setId(Long x){
        id = x;
    }

    public Long getId(){
        return id;
    }

    public void setTitle(String s){
        title = s;
    }

    public String getTitle(){
        return title;
    }

    public void setNote(String n){
        note = n;
    }

    public String getNote(){
        return note;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
