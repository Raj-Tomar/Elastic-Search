package com.raj.serviceImpl;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.raj.dto.Movie;

import raj.com.service.ElasticSearchService;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
	
	@Autowired
	Properties query;
	
	private final String ELASTIC_SEARCH_URL = "http://localhost:9200";
	private final static Logger logger = Logger.getLogger(ElasticSearchServiceImpl.class);
	
	@Override
	public ResponseEntity<String> TestES(){
		logger.info("TestES");
		ResponseEntity<String> response = elasticSearchCall(ELASTIC_SEARCH_URL, HttpMethod.GET, null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> createIndex(String search) {
		logger.info("createIndex");
		
		/* PUT request to the REST API to a URL made up of the index name, type name and ID. 
		 * That is: http://localhost:9200/<index>/<type>/[<id>].
		 * Index and type are required while the id part is optional.
		 * If we don't specify an ID ElasticSearch will generate one for us.
		 */
		String url = ELASTIC_SEARCH_URL + "/movies/movie/1";
		Gson gson = new Gson();
		Movie movie = gson.fromJson(search, Movie.class);
		JSONObject jObj = new JSONObject(movie);
		logger.info("\nCreate Index Json Data\n" + jObj);
		ResponseEntity<String> response = elasticSearchCall(url, HttpMethod.POST , jObj);
		return response;
	}
	
	@Override
	public ResponseEntity<String> getById(String search) {
		logger.info("getById");
		String url = ELASTIC_SEARCH_URL + "/movies/movie/1";
		ResponseEntity<String> response = elasticSearchCall(url, HttpMethod.GET , null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> deleteDocument(String search) {
		logger.info("deleteDocument");
		String url = ELASTIC_SEARCH_URL + "/movies/movie/1";
		ResponseEntity<String> response = elasticSearchCall(url, HttpMethod.DELETE , null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> search(String search) {
		logger.info("search");
		
		/* we make requests to an URL following this pattern: 
		 * <index>/<type>/_search where index and type are both optional. 
		 */
		
		String url = ELASTIC_SEARCH_URL + "/_search";
		//String url = ELASTIC_SEARCH_URL + "/movies/_search";
		//String url = ELASTIC_SEARCH_URL + "/movies/movie/_search";
		ResponseEntity<String> response = elasticSearchCall(url, HttpMethod.POST , null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> filter(String search) {
		logger.info("filter");
		ResponseEntity<String> response = null;
		String url = ELASTIC_SEARCH_URL + "/_search";
		search = "ford";
		try {
			String queryString = MessageFormat.format(query.getProperty("fieldSearch").trim(), search);
			//String queryString = query.getProperty("fieldSearch").trim();
			logger.info("\nFilter Query\n" + queryString);
			JSONObject jObj = new JSONObject(queryString);
			response = elasticSearchCall(url, HttpMethod.POST , jObj);
			
			//QueryBuilder qb = QueryBuilders.termQuery("name", "some string");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseEntity<String> mapping(String search) {
		logger.info("mapping");
		ResponseEntity<String> response = null;
		String url = ELASTIC_SEARCH_URL + "/_search";
		try {
			String queryString = query.getProperty("mapping").trim();
			logger.info("\nMapping Query\n" + queryString);
			JSONObject jObj = new JSONObject(queryString);
			response = elasticSearchCall(url, HttpMethod.POST , jObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

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
			response = restTemplate.exchange(url, httpMethod, entity, String.class);
			logger.info("\nElastic Search Response\n" + response.getBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
