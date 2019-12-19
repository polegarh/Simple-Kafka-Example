package producers;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;

import org.apache.kafka.clients.producer.Producer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Customer {
	
    @SuppressWarnings("resource")
	public static void main( String[] args ) throws IOException {
    	MyKafkaProducer myproducer = new MyKafkaProducer();
    	Producer<String, String> producer = myproducer.createProducer();

    	File f = new File("Customer.txt");

        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            String line=scanner.nextLine();

            String[] words=line.split("\\|");

            long custID=Integer.parseInt(words[0]);
            String firstName=words[1];
            String secondName=words[2];
            long phoneNumber= Long.parseLong(words[3]);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String time1=timestamp.toInstant().toString();

            String cust="{\"event\":{\"msg\":{\"type\":\"Customer\"},\"customerID\":\""+custID+"\","
                    + "\"customerName\":{\"firstName \":\""+firstName+"\",\"secondName\":\""+secondName+"\"},\"phoneNumber\":\""+phoneNumber+"\","
                    + "\"msgGeneratedBy\":{\"msggeneratedDtm\":\""+time1+"\"}}}";

            try {
	            JSONParser parser = new JSONParser();
	            JSONObject jsonobj = (JSONObject) parser.parse(cust);
	            try {
	    			myproducer.runProducer(producer, custID, jsonobj);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
            } catch (org.json.simple.parser.ParseException e) {
            }
        }
        producer.flush();
        producer.close();
    }
}