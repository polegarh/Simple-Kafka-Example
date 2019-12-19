package data;

import org.apache.kafka.clients.consumer.Consumer;

import consumers.MyKafkaConsumer;

public class Data {
	public static void main (String [] args) {
		MyKafkaConsumer myconsumer = new MyKafkaConsumer();
		Consumer<String, String> consumer = myconsumer.createConsumer();
		try {
			myconsumer.runConsumer(consumer);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when running consumer");
		}
		consumer.close();
        System.out.println("DONE");
	}
}
