package com.anchore.pojo;

import java.util.List;

public class MyResponse {
	private String content_type;

    private List<Content> content;

    private String imageDigest;

    public String getContent_type ()
    {
        return content_type;
    }

    public void setContent_type (String content_type)
    {
        this.content_type = content_type;
    }

    public List<Content> getContent ()
    {
        return content;
    }

    public void setContent (List<Content> content)
    {
        this.content = content;
    }

    public String getImageDigest ()
    {
        return imageDigest;
    }

    public void setImageDigest (String imageDigest)
    {
        this.imageDigest = imageDigest;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content_type = "+content_type+", content = "+content+", imageDigest = "+imageDigest+"]";
    }
}
