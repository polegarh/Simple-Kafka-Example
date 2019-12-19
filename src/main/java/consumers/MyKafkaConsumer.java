package consumers;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import data.DAO;

public class MyKafkaConsumer {
	private final static String TOPIC = "Customer_Data";
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";
    private final DAO dao = new DAO();
    
    public Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                    BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                                    "MyKafkaConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
    
    public void runConsumer(Consumer<String, String> consumer) throws InterruptedException {
        final int giveUp = 100;   int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            
            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record Recieved:\n Key: %s \n Value: %s \n",record.key(), record.value());
                sortData(record);
            });
            consumer.commitAsync();
        }
    }
    
    public void sortData (ConsumerRecord<String, String> record) {
    	if (record.value().contains("Customer")) {
    		JSONParser parser = new JSONParser();
            try {
				JSONObject jobj = (JSONObject) parser.parse(record.value());
				Map event = ((Map)jobj.get("event"));
				
				Long customerID = Long.parseLong((String) event.get("customerID"));
				Long phoneNumber = Long.parseLong((String) event.get("phoneNumber"));
				
				Map customerName = ((Map)event.get("customerName"));

				String firstName = (String) customerName.get("firstName");
				String secondName = (String) customerName.get("secondName");
				dao.addCustomer(customerID, firstName, secondName, phoneNumber);
			} catch (ParseException e) {
				System.out.println("Problem with the parser of JSON");
				e.printStackTrace();
			}  
    	} else if (record.value().contains("Product")) {
    		JSONParser parser = new JSONParser();
            try {
				JSONObject jobj = (JSONObject) parser.parse(record.value());
				Long productID = Long.parseLong((String) jobj.get("productID"));
				String productType = (String) jobj.get("productType");
				String secondName = (String) jobj.get("productVersion");
				String productPrice = (String) jobj.get("productPrice");
				dao.addProduct(productID, productType, secondName, productPrice);
			} catch (ParseException e) {
				System.out.println("Problem with the parser of JSON");
				e.printStackTrace();
			}  
    	} else if (record.value().contains("Sales")) {
    		JSONParser parser = new JSONParser();
            try {
				JSONObject jobj = (JSONObject) parser.parse(record.value());
				Long transactionID = Long.parseLong((String) jobj.get("transactionID"));
				Long customerID = Long.parseLong((String) jobj.get("customerID"));
				Long productID = Long.parseLong((String) jobj.get("productID"));
				String timestamp = (String) jobj.get("timestamp");
				String totalAmount = (String) jobj.get("totalAmount");
				int totalQuantity = (Integer) jobj.get("totalQuantity");
				dao.addSale(transactionID, customerID, productID, timestamp, totalAmount, totalQuantity);
			} catch (ParseException e) {
				System.out.println("Problem with the parser of JSON");
				e.printStackTrace();
			}  
    	} else if (record.value().contains("Refund")) {
    		JSONParser parser = new JSONParser();
            try {
				JSONObject jobj = (JSONObject) parser.parse(record.value());
				Long refundID = Long.parseLong((String) jobj.get("refundID"));
				Long transactionID = Long.parseLong((String) jobj.get("transactionID"));
				Long customerID = Long.parseLong((String) jobj.get("customerID"));
				Long productID = Long.parseLong((String) jobj.get("productID"));
				String timestamp = (String) jobj.get("timestamp");
				String refundAmount = (String) jobj.get("refundAmount");
				int refundQuantity = (Integer) jobj.get("refundQuantity");
				dao.addRefund(refundID, transactionID, customerID, productID, timestamp, refundAmount, refundQuantity);
			} catch (ParseException e) {
				System.out.println("Problem with the parser of JSON");
				e.printStackTrace();
			}  
    	} else {
    		System.out.println("record.value().contains(\"_____\") does not work");
    	}
    }
}
