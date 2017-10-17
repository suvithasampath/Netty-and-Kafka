package com.person.details.proto.java;


import org.apache.kafka.common.serialization.Deserializer;
import com.google.protobuf.InvalidProtocolBufferException;
import com.person.details.proto.java.PersonDetailsBean.PersonDetails;

public class PersonDetailsBeanDeserializer extends Adapter implements Deserializer<PersonDetails> {

	@Override
	public PersonDetails deserialize(String topic, byte[] detailsArray) {
		PersonDetails message = null;
		try{
		message =  PersonDetails.parseFrom(detailsArray);	
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}finally{
			detailsArray=null;
			topic=null;
		}
		return message;
	}

	
}