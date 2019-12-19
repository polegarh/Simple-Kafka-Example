package producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONObject;

public class MyKafkaProducer {
	private final static String TOPIC = "Customer_Data";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";
    
    
	public Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                            BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MyKafkaProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                        StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                    StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
	
	public void runProducer(Producer<String, String> producer, long id, JSONObject jobj) throws Exception {

	      try {
	          System.out.println("Record Sent: ");
	          System.out.println("Key: " + id);
	          System.out.println("Value: " + jobj);
	          
	          producer.send(new ProducerRecord<>(TOPIC, Long.toString(id), jobj.toString()));

	      } finally {
	          producer.flush();
	          producer.close();
	      }
	}
}
