package com.acme.acmevendor.activity.dashboard;

public class AdminCrudDataClass {

    int id =0;
    String name= "";
    String image= "images/GghzUXeuU09hlQg1k7LxN4t5RDobBgcn8rzoUMJF.jpg";
    String uid= "";

    public void setId(int id)
    {
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setImage(String image)
    {
        this.image=image;
    }

    public String getImage(){
        return image;
    }

    public void setUid(String uid)
    {
        this.uid=uid;
    }

    public String getUid(){
        return uid;
    }

}
