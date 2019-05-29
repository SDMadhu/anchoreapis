package com.anchore.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
public class Digest {
@Column(name ="status")
private String status;

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}


	

}
