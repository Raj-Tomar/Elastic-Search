package com.raj.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchClient implements InitializingBean {
	
	public static TransportClient esClient = null;
	
	private final static Logger logger = Logger.getLogger(ElasticSearchClient.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("TransportClient Initializing...");
		getEsClient();
		logger.info("TransportClient Initialization done.");
	}
	
	@SuppressWarnings("resource")
	public static TransportClient getEsClient(){
		try {
			if(esClient == null){
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
}
