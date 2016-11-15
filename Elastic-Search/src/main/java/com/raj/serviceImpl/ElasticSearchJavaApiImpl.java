package com.raj.serviceImpl;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.raj.util.ElasticSearchClient;

import raj.com.service.ElasticSearchJavaApi;

@Service
public class ElasticSearchJavaApiImpl implements ElasticSearchJavaApi {

	private final static Logger logger = Logger.getLogger(ElasticSearchJavaApiImpl.class);

	@Override
	public ResponseEntity<String> TestES() {
		logger.info("TestES");
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall("", HttpMethod.GET, null);
		return response;
	}

	@Override
	public ResponseEntity<String> createIndex(String search) {
		/*
		TransportClient client = ElasticSearchClient.getEsClient();
		IndexResponse res = client.prepareIndex("book", "article", "1")
        .setSource(putJsonDocument("ElasticSearch: Java API",
                                   "ElasticSearch provides the Java API, all operations "
                                   + "can be executed asynchronously using a client object.",
                                   new Date(), new String[]{"elasticsearch, ES-Java-Api"},
                                   "Raj Tomar")).execute().actionGet();
		return new ResponseEntity<String>(res.toString(), HttpStatus.OK);
		*/
		String url = "book/article/1";
		Map<String, Object> document = putJsonDocument("ElasticSearch: Java API",
                "ElasticSearch provides the Java API, all operations "
                + "can be executed asynchronously using a client object.",
                new Date(), new String[]{"elasticsearch, ES-Java-Api"},
                "Raj Tomar");
		JSONObject json = new JSONObject(document);
		logger.info("\n Data \n" + json);
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.PUT, json);
		logger.info("\n Response \n" + response);
		return response; 
	}

	@Override
	public ResponseEntity<String> getById(String search) {
		TransportClient client = ElasticSearchClient.getEsClient();
		GetResponse getResponse = client.prepareGet("book", "article", "1").execute().actionGet();
		Map<String, Object> source = getResponse.getSource();
		logger.info("------------------------------");
		logger.info("Index: " + getResponse.getIndex());
		logger.info("Type: " + getResponse.getType());
		logger.info("Id: " + getResponse.getId());
		logger.info("Version: " + getResponse.getVersion());
		logger.info(source);
		logger.info("------------------------------");
		return new ResponseEntity<String>(getResponse.toString(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> search(String search) {
		/*
		TransportClient client = ElasticSearchClient.getEsClient();
		SearchResponse response = client.prepareSearch("book")
				.setTypes("article")
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(fieldQuery("title", "ElasticSearch"))
				.setFrom(0).setSize(60).setExplain(true)
				.execute()
				.actionGet();
		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);
		for (SearchHit hit : results) {
			System.out.println("------------------------------");
			Map<String,Object> result = hit.getSource();   
			System.out.println(result);
		}
		*/
		return null;
	}
	
	@Override
	public ResponseEntity<String> updateDocument(String search) {
		return null;
	}

	@Override
	public ResponseEntity<String> filter(String search) throws UnknownHostException {
		TransportClient client = ElasticSearchClient.getEsClient();
		// QueryBuilder qb = QueryBuilders.termQuery("movies", "movie");
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		SearchResponse res = client.prepareSearch("book").setQuery(qb).execute().actionGet();
		ResponseEntity<String> response = new ResponseEntity<String>(res.toString(), HttpStatus.OK);
		logger.info(response);
		return response;
	}

	@Override
	public ResponseEntity<String> mapping(String search) {
		return null;
	}
	
	@Override
	public ResponseEntity<String> deleteDocument(String search) {
		String url = "book/article/1";
		ResponseEntity<String> response = ElasticSearchClient.elasticSearchCall(url, HttpMethod.DELETE, null);
		logger.info("\n Response \n" + response);
		return response;
	}

	public static Map<String, Object> putJsonDocument(String title, String content, Date postDate, String[] tags, String author) {
		Map<String, Object> jsonDocument = new HashMap<String, Object>();
		jsonDocument.put("title", title);
		jsonDocument.put("content", content);
		jsonDocument.put("postDate", postDate);
		jsonDocument.put("tags", tags);
		jsonDocument.put("author", author);
		return jsonDocument;
	}
	
}