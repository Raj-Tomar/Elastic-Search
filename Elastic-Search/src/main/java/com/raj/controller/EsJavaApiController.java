package com.raj.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import raj.com.service.ElasticSearchJavaApi;

@Controller
@RequestMapping(value = "/api")
public class EsJavaApiController {
	
	@Autowired
	ElasticSearchJavaApi service;
	
	private final static Logger logger = Logger.getLogger(EsJavaApiController.class);
	
	
	@PostMapping(value = "/estest", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> testES(@RequestBody String reqestBody){
		logger.info("testES controller");
		ResponseEntity<String> result = null;
		try {
			result = service.TestES();
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return result;
	}
	
	@PostMapping(value = "/createIndex", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> createIndex(@RequestBody String reqestBody){
		logger.info("createIndex controller");
		ResponseEntity<String> result = null;
		try {
			result = service.createIndex(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return result;
	}
	
	@PostMapping(value = "/getById", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> getById(@RequestBody String reqestBody){
		logger.info("getById controller");
		ResponseEntity<String> result = null;
		try {
			result = service.getById(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return result;
	}
	
	@PostMapping(value = "/deleteDocument", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> deleteDocument(@RequestBody String reqestBody){
		logger.info("deleteDocument controller");
		ResponseEntity<String> result = null;
		try {
			result = service.deleteDocument(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return result;
	}
	
	@PostMapping(value = "/search", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> search(@RequestBody String reqestBody){
		logger.info("search controller");
		ResponseEntity<String> result = null;
		try {
			result = service.search(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return result;
	}
	
	@PostMapping(value = "/filter", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> filter(@RequestBody String reqestBody){
		logger.info("filter controller");
		ResponseEntity<String> result = null;
		try {
			result = service.filter(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@PostMapping(value = "/mapping", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> mapping(@RequestBody String reqestBody){
		logger.info("mapping controller");
		ResponseEntity<String> result = null;
		try {
			result = service.mapping(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@PostMapping(value = "/delete", consumes = "application/json", produces = "applicatin/json")
	public ResponseEntity<String> delete(@RequestBody String reqestBody){
		logger.info("delete controller");
		ResponseEntity<String> result = null;
		try {
			result = service.deleteDocument(reqestBody);
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
