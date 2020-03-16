package com.smr.estate.Model;

//Category for Estate item

public class Category
{
    String id;
    String title;
    boolean isLastNode;

    public Category(String id, String title, boolean isLastNode)
    {
        this.id = id;
        this.title = title;
        this.isLastNode = isLastNode;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isLastNode()
    {
        return isLastNode;
    }
}
