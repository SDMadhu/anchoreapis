package com.anchore.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name="sys.anchore_demo")
public class ImageEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="digest",nullable = false)
	private String digest;
	@Column(name="tag", nullable = false)
	private String tag;
	@CreatedDate
	@Column(name="created_date",nullable = false)
	private String created_at;
	
   @Embedded
   @ElementCollection
   @AttributeOverrides(value = {
           @AttributeOverride(name = "status", column = @Column(length = 10))
   })
   private Collection<Digest> lisOfDigest = new ArrayList<Digest>();
	 
   public Long getId()  
    {  
        return id;  
    }  
   public void setId(Long id)  
	    {  
	        this.id = id;  
	    }  
	
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public Collection<Digest> getlistOfDigest() {
		return lisOfDigest;
	}
	public void setlistOfDigest(Collection<Digest> lisOfDigest) {
		this.lisOfDigest =lisOfDigest;
	}

}
