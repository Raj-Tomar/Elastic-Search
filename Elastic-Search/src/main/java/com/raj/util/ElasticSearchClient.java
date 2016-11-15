package com.raj.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ElasticSearchClient implements InitializingBean {
	
	public static TransportClient esClient;
	private final static String ELASTIC_SEARCH_URL = "http://localhost:9200";
	private final static Logger logger = Logger.getLogger(ElasticSearchClient.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("TransportClient Initializing...");
		getEsClient();
		logger.info("TransportClient Initialization done.");
	}
	
	/**
	 * At the time of application start it create TransportClient instance.
	 * @return TransportClient instance
	 */
	@SuppressWarnings("resource")
	public static TransportClient getEsClient(){
		try {
			if(null == esClient){
				Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
				// on startup
				esClient = new PreBuiltTransportClient(settings)
						.addTransportAddress(new InetSocketTransportAddress(
								new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9300)));
				// on shutdown
				//esClient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return esClient;
	}
	
	/**
	 * This method calls sends the request(API call) to Elastic Search and 
	 * gets the response.
	 * @param url
	 * @param httpMethod
	 * @param json
	 * @return
	 */
	public static ResponseEntity<String> elasticSearchCall(String url, HttpMethod httpMethod, JSONObject json){
		logger.info("elasticSearchCall");
		ResponseEntity<String> response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			if(json != null)
				entity = new HttpEntity<String>(json.toString(), headers);
			else
				entity = new HttpEntity<String>(headers);
			response = restTemplate.exchange(ELASTIC_SEARCH_URL + url, httpMethod, entity, String.class);
			logger.info("\nElastic Search Response\n" + response.getBody().toString());
		} catch (Exception e) {
			response = new ResponseEntity<String>("Wrong Input", HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
