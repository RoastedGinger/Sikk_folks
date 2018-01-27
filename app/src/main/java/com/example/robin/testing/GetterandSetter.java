package com.example.robin.testing;

/**
 * Created by robin on 22/1/18.
 */

public class GetterandSetter {

    private String title,desc,image;
    public  GetterandSetter(){

    }

    public GetterandSetter(String title,String desc,String image){
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
