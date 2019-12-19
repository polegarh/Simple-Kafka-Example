package producers;

import java.sql.Timestamp;
import org.apache.kafka.clients.producer.Producer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Sales {
	private final static MyKafkaProducer myproducer = new MyKafkaProducer();
	private final static Producer<String, String> producer = myproducer.createProducer();

	public static void send(String line) {

		String[] words=line.split("\\|");

        long transactionID = Integer.parseInt(words[0]);
        String customerID = words[1];
        String productID = words[2];
        String timeOfSale = words[3];
        String totalAmount = words[4];
        String totalQuantity = words[5];

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time1 = timestamp.toInstant().toString();

        String sale="{\"event\":{\"msg\":{\"type\":\"Sales\"}," + "\"transactionID\":\""+transactionID+"\","+"\"customerID\":\""+customerID+"\","
                + "\"productID\":\""+productID+"\",\"timeOfSale\":\""+timeOfSale+"\"," + "\"totalAmount\":\"" + totalAmount+"\"," + "\"totalQuantity\":\"" + totalQuantity+"\"," 
                + "\"msgGeneratedBy\":{\"msggeneratedDtm\":\""+time1+"\"}}}";

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonobj = (JSONObject) parser.parse(sale);
            try {
    			myproducer.runProducer(producer, transactionID, jsonobj);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        } catch (org.json.simple.parser.ParseException e) {
        	System.out.println("Something went wrong");
        }
	}
	
	public static void main (String [] args) {

		SparkConf sparkConf = new SparkConf().setAppName("Sales").setMaster("local[2]");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		JavaRDD<String> lines = jsc.textFile("Sales.txt");
		
		System.out.println("About to send to Kafka...");
		lines.foreach(x -> send(x));
		producer.flush();
        producer.close();
        jsc.close();
		System.out.println("Completed");
	
	}
}
