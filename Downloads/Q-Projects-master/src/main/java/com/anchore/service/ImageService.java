package com.anchore.service;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
/*
 * created by RK.Nayak
 * May 24/2019
 * 
 */
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.anchore.helper.HibernateUtil;
import com.anchore.model.Digest;
import com.anchore.model.ImageEntity;
import com.anchore.pojo.ImagePojo;

@Service
public class ImageService {
private static final  Logger LOGGER=Logger.getLogger(ImageService.class.getName());

public  List<ImageEntity> saveImageDetails(final ImagePojo imagePojo) {
	LOGGER.info("############saving Image Details##############");
    Session ses=HibernateUtil.getSessionFactory().openSession();
 	ImageEntity imageEntity=new ImageEntity();
 	imageEntity.setDigest(imagePojo.getDigest());
 	imageEntity.setTag(imagePojo.getTag());
	imageEntity.setCreated_at(imagePojo.getCreated_at());
	Digest digest=new Digest();
	digest.setStatus(imagePojo.getDockerfile().stream().iterator().next().getStatus());
	imageEntity.getlistOfDigest().add(digest);
	try {
		/*
		 * we need to handle here for unnatural exception occurences
		 */
	Transaction tx=ses.beginTransaction();
	LOGGER.info("####Transaction begins for images######"+imagePojo.toString());
	ses.save(imageEntity);
  	tx.commit();

}
	catch(Exception e) {
		LOGGER.log(Level.WARNING, ">>>>>>transaction Failied<<<<<<<<<<");;
		e.printStackTrace();
	
	}
 LOGGER.log(Level.CONFIG, "########Query Executed #######");		
  List<ImageEntity>list= ses.createQuery("from ImageEntity").list();
  return list;
   
    
}

}
