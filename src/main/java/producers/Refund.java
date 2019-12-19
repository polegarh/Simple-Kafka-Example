package producers;

import java.sql.Timestamp;

import org.apache.kafka.clients.producer.Producer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Refund {
	private final static MyKafkaProducer myproducer = new MyKafkaProducer();
	private final static Producer<String, String> producer = myproducer.createProducer();


	public static void send(String line) {

		String[] words=line.split("\\|");

        long refundID = Integer.parseInt(words[0]);
        long transactionID = Integer.parseInt(words[1]);
        String customerID = words[2];
        String productID = words[3];
        String timeOfSale = words[4];
        String refundAmount = words[5];
        String refundQuantity = words[6];

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time1 = timestamp.toInstant().toString();

        String refund="{\"event\":{\"msg\":{\"type\":\"Refund\"}," + "\"refundID\":\""+refundID+"\"," + "\"transactionID\":\""+transactionID+"\","+"\"customerID\":\""+customerID+"\","
                + "\"productID\":\""+productID+"\",\"timeOfSale\":\""+timeOfSale+"\"," + "\"refundAmount\":\"" + refundAmount+"\"," + "\"refundQuantity\":\"" + refundQuantity+"\"," 
                + "\"msgGeneratedBy\":{\"msggeneratedDtm\":\""+time1+"\"}}}";

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonobj = (JSONObject) parser.parse(refund);
            try {
    			myproducer.runProducer(producer, refundID, jsonobj);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        } catch (org.json.simple.parser.ParseException e) {
        	System.out.println("JSON Parser went wrong");
        }
	}
	
	public static void main (String [] args) {

		SparkConf sparkConf = new SparkConf().setAppName("Refund").setMaster("local[2]");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		JavaRDD<String> lines = jsc.textFile("Refund.txt");
		
		System.out.println("----About to send to Kafka...----");
		lines.foreach(x -> send(x));
		producer.flush();
        producer.close();
        jsc.close();
		System.out.println("----Completed----");
		
	}
}
