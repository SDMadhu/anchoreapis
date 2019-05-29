package com.anchore.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Content implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String license;

	@JsonAlias({ "pack", "package" })
	//@SerializedName("package")
    private String pack;

    private String size;

    private String origin;

    private String type;

    private String version;

    public String getLicense ()
    {
        return license;
    }

    public void setLicense (String license)
    {
        this.license = license;
    }

    public String getPackage ()
    {
        return pack;
    }

    public void setPackage (String pack)
    {
        this.pack = pack;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    public String getOrigin ()
    {
        return origin;
    }

    public void setOrigin (String origin)
    {
        this.origin = origin;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [license = "+license+", package = "+pack+", size = "+size+", origin = "+origin+", type = "+type+", version = "+version+"]";
    }
}
