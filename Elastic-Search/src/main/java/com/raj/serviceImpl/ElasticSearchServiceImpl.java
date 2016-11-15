package com.raj.serviceImpl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.raj.dto.Movie;
import com.raj.util.ElasticSearchClient;

import raj.com.service.ElasticSearchService;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
	
	@Autowired
	Properties query;
	
	private final static Logger logger = Logger.getLogger(ElasticSearchServiceImpl.class);
	
	@Override
	public ResponseEntity<String> TestES(){
		logger.info("TestES");
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall("", HttpMethod.GET, null);
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
		String url = "/movies/movie/1";
		Gson gson = new Gson();
		Movie movie = gson.fromJson(search, Movie.class);
		JSONObject jObj = new JSONObject(movie);
		logger.info("\nCreate Index Json Data\n" + jObj);
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.POST , jObj);
		return response;
	}
	
	@Override
	public ResponseEntity<String> getById(String search) {
		logger.info("getById");
		String url = "/movies/movie/1";
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.GET , null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> deleteDocument(String search) {
		logger.info("deleteDocument");
		String url = "/movies/movie/1";
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.DELETE , null);
		return response;
	}
	
	@Override
	public ResponseEntity<String> search(String search) {
		logger.info("search");
		
		/* we make requests to an URL following this pattern: 
		 * <index>/<type>/_search where index and type are both optional. 
		 */
		
		String url = "/_search";
		//String url = ELASTIC_SEARCH_URL + "/movies/_search";
		//String url = ELASTIC_SEARCH_URL + "/movies/movie/_search";
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.POST , null);
		
		
		Map<String,Float> fields = new HashMap<String,Float>();
		fields.put("iin", 100f);
		fields.put("desc", 100f);
		
		QueryBuilder qb = QueryBuilders.queryStringQuery("*Product1*")
				.fields(fields)
				.analyzeWildcard(true);
		
		QueryBuilder gdq = QueryBuilders.geoDistanceQuery("geo_locations")  
			    .point(16.5062, 80.6480)
			    .distance(200, DistanceUnit.KILOMETERS);
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
				.must(qb)
				.must(gdq)
				.filter(QueryBuilders.termsQuery("item_status", "true"));
		 
		
		logger.info("\n Query \n" + boolQuery);
		
		return response;
	}
	
	@Override
	public ResponseEntity<String> filter(String search) throws UnknownHostException {
		logger.info("filter");
		ResponseEntity<String> response = null;
		String url = "/_search";
		search = "ford";
		try {
			//String queryString = MessageFormat.format(query.getProperty("fieldSearch").trim(), search);
			String queryString = query.getProperty("fieldSearch").trim();
			logger.info("\nFilter Query\n" + queryString);
			JSONObject jObj = new JSONObject(queryString);
			response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.POST , jObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseEntity<String> mapping(String search) {
		logger.info("mapping");
		ResponseEntity<String> response = null;
		String url = "/_search";
		try {
			String queryString = query.getProperty("mapping").trim();
			logger.info("\nMapping Query\n" + queryString);
			JSONObject jObj = new JSONObject(queryString);
			response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.POST , jObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
