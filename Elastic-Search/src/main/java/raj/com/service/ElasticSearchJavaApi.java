package raj.com.service;

import java.net.UnknownHostException;

import org.springframework.http.ResponseEntity;

public interface ElasticSearchJavaApi {
	
	/**
	 * @return
	 */
	public ResponseEntity<String> TestES();
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> createIndex(String search);
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> getById(String search);
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> deleteDocument(String search);
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> search(String search);
	
	/**
	 * @param search
	 * @return
	 * @throws UnknownHostException 
	 */
	public ResponseEntity<String> filter(String search) throws UnknownHostException;
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> mapping(String search);

}
