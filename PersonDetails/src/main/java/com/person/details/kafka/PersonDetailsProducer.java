package com.person.details.kafka;

//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import com.person.details.proto.java.PersonDetailsBean;
import com.person.details.proto.java.PersonDetailsBean.PersonDetails;
import com.person.details.proto.java.PersonDetailsBeanSerializer;

//Create java class named “SimpleProducer”
public class PersonDetailsProducer {
 
 public void run(JSONObject obj) throws Exception{
    

    String topicName = "PersonDetails";
    Properties props = new Properties();
    //Assign localhost id
    props.put("bootstrap.servers", "localhost:9092");
    //Set acknowledgements for producer requests.      
    props.put("acks", "all");
    //If the request fails, the producer can automatically retry,
    props.put("retries", 0);
    //Specify buffer size in config
    props.put("batch.size", 16384);
    //Reduce the no of requests less than 0   
    props.put("linger.ms", 1);
    //The buffer.memory controls the total amount of memory available to the producer for buffering.   
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", 
       "com.person.details.proto.java.PersonDetailsBeanSerializer");
    props.put("value.serializer", 
       "com.person.details.proto.java.PersonDetailsBeanSerializer");
    PersonDetails detailsBean = null;
    try{
        detailsBean = PersonDetailsBean.PersonDetails.newBuilder()
				.setName(obj.getString("name"))
				.setEmail(obj.getString("e-mail"))		
				.setPhoneNumber(obj.getDouble("phoneNumber")).build();
    Producer<String, PersonDetails> producer = new KafkaProducer
       <String, PersonDetails>(props,new StringSerializer(), new PersonDetailsBeanSerializer());
       producer.send(new ProducerRecord<String, PersonDetails>(topicName,detailsBean ));
             System.out.println("Message sent successfully");
             producer.close();
 }catch(Exception e ){
 	e.printStackTrace();
 }
 finally{
 	detailsBean = null;
 }
}
}