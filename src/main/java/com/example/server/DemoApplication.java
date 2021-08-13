package com.example.server;

import com.example.server.dao.DBService;
import com.example.server.service.AuctionService;
import com.example.server.service.RaftService;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class DemoApplication {
//32379
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(DemoApplication.class, args);
		/*RaftService raft = new RaftService();
		raft.putAuction(456);
		DBService dbService = new DBService();
		AuctionService auction = new AuctionService();*/

		//raft.getAllAuctions();


		/*SpringApplication.run(DemoApplication.class, args);
		RaftService raft = new RaftService();
		//raft.put("foo","bar");
		Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
		KV kvClient = client.getKVClient();
		ByteSequence key = ByteSequence.from("hej2".getBytes());
		ByteSequence value = ByteSequence.from("hej3".getBytes());
		System.out.println("svaranu");
		kvClient.put(key,value);
		//Thread.sleep(1000);
		CompletableFuture<GetResponse> getFuture = kvClient.get(key);
		GetResponse response = getFuture.get();
		System.out.println(response);
		//raft.put("foo","bar");*/
	}

}
