/*
 * @author R.Nayak 
 * May/24/2019
 * This Api's identifies images and all services to exposes anchore images info.
 */
package com.anchore.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.anchore.model.ImageEntity;
import com.anchore.pojo.Content;
import com.anchore.pojo.ImagePojo;
import com.anchore.pojo.MyResponse;
import com.anchore.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ImageController {
@Autowired
private ImageService imageService;
@Autowired
private RestTemplate restTemplate;
@Autowired
private Environment environment;

private static final  Logger LOGGER=Logger.getLogger(ImageController.class.getName());
private static final String ENDPOINT_URL="http://localhost:8228/v1/images";
private static final String BY_ID ="http://localhost:8228/v1/images/by_id/{imageId}";
private static final String Image_DIGEST ="http://localhost:8228/v1//images/{imageDigest}/content/{ctype}";


@ResponseBody
/*Authenticates servers counts to coordinates images with BasicAuth credentials.
 * 
 * 
 */

@RequestMapping(value ="allimages" ,method = RequestMethod.GET)
public Object[] getCompleteImageDetails() {
    LOGGER.info("########Images Details Fetching##############");
	try {
	HttpHeaders headers = new HttpHeaders();
	headers.setBasicAuth(environment.getProperty("cli-user"), environment.getProperty("cli-password"));
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity <Object[]> entity = new HttpEntity<Object[]>(headers);
    return restTemplate.exchange(ENDPOINT_URL, HttpMethod.GET, entity , Object[].class).getBody();
    }
	catch(HttpClientErrorException hcee) {
	hcee.printStackTrace();
	LOGGER.log(Level.SEVERE, "Credentials Mismatched: {} "+hcee.getMessage());
	return null;	
	}
}


@RequestMapping(value="/api/image/{imageId}" ,method = RequestMethod.POST)
@ResponseBody
public List<ImageEntity>getReponseImage(@RequestBody ImagePojo imagePojo){
       List<ImageEntity>actualValue=null;
       LOGGER.info("#######Requested Body########");
       if(imagePojo!=null) {
        actualValue=imageService.saveImageDetails(imagePojo);
       LOGGER.info("#######Added saved to POJO into List########"+actualValue.toString());
}
return actualValue;  	
}

/**
 * @param imageId
 * @return
 */
@RequestMapping(value="/images/{imageId}", method=RequestMethod.GET)
@ResponseBody
public Object getImage(@PathVariable String imageId) {
	
	
	 LOGGER.info("########Entering  By ImageId##############");
		try {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setBasicAuth(environment.getProperty("cli-user"), environment.getProperty("cli-password"));
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    
	    HttpEntity <Object> entity = new HttpEntity<Object>(headers);
	    Map<String,Object> map = new HashMap<>();
	    map.put("imageId", imageId);
	   
	    return restTemplate.exchange(BY_ID, HttpMethod.GET,entity,Object.class,map).getBody();
	    
	    }
		catch(HttpClientErrorException hcee) {
		hcee.printStackTrace();		
		LOGGER.log(Level.SEVERE, "Credentials Mismatched: {} "+hcee.getMessage());
		return null;	
		}
	 
	
}

@RequestMapping(value="/{imageDigest}/{ctype}", method=RequestMethod.GET)
@ResponseBody
public Object getImageDigest(@PathVariable ("imageDigest") String imageDigest, 
		@PathVariable ("ctype") String ctype) throws JsonProcessingException {
	
	
	 LOGGER.info("########Entering  By imageDigest##############");
	 ObjectMapper objectMapper = new ObjectMapper();
		try {
		HttpHeaders headers = new HttpHeaders();
		
		final String DIGEST_URL="http://localhost:8228/v1/images/";
		final String DIGEST_ID=imageDigest;
		final String CTYPE="/content/"+ctype;
		
		
		headers.setBasicAuth(environment.getProperty("cli-user"), environment.getProperty("cli-password"));
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    
	    HttpEntity <Object> entity = new HttpEntity<Object>(headers);
    Map<String,Object> map = new HashMap<>();
	    map.put("imageDigest", imageDigest);
	    map.put("ctype", ctype);
	   Object objectJson = restTemplate.exchange( Image_DIGEST,HttpMethod.GET,entity,Object.class,map).getBody();
	   
	   LOGGER.info("Object JSO :::" + objectJson);
	   objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	      
	   MyResponse imageDigestValue = objectMapper.convertValue(objectJson,MyResponse.class);
		 System.out.println("OUTPUT       :"+imageDigestValue.getContent_type() + "license" + imageDigestValue.getContent());
		 
		 for(Content c :imageDigestValue.getContent()) {
			 System.out.println("Json Values ::" + c);
		 }
		 	
	    return objectJson;
	    
	    }
		
		
		
		
		catch(HttpClientErrorException hcee) {
		hcee.printStackTrace();		
		LOGGER.log(Level.SEVERE, "Credentials Mismatched: {} "+hcee.getMessage());
		
		}
		return null;
	 
	
}



		
}




