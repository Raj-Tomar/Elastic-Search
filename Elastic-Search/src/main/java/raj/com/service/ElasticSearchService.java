package raj.com.service;

import org.springframework.http.ResponseEntity;

public interface ElasticSearchService {

	
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
	 */
	public ResponseEntity<String> filter(String search);
	
	/**
	 * @param search
	 * @return
	 */
	public ResponseEntity<String> mapping(String search);
}
