package com.raj.memcache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachedConnection {

	public static void main(String[] args) throws InterruptedException, ExecutionException{
		MemcachedClient mcc = getMemcachedClient();
		mcc.set("first", 1000, "first");
		System.out.println("Data from Memcached: " + mcc.get("first") );
		Future<Object> f = mcc.asyncGet("first");
		System.out.println("Data from Memcached: " + f.get());
		mcc.delete("first");
		System.out.println("Data from Memcached: " + mcc.get("first") );
	}
	
	
	
   public static MemcachedClient getMemcachedClient(){
	   MemcachedClient mcc = null;
	   try {
	         // Connecting to Memcached server on localhost
	         //MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("192.168.10.39", 11211));
	         mcc = new MemcachedClient(new BinaryConnectionFactory(),
	        		 AddrUtil.getAddresses("192.168.10.39:11211"));
	         System.out.println("Connection to server sucessful.");
	         // Shutdowns the memcached client
	         //mcc.shutdown();
	      } catch(Exception ex){
	         System.out.println( ex.getMessage() );
	      } 
	   return mcc;
   }
}