package com.person.details.proto.java;


import org.apache.kafka.common.serialization.Serializer;

import com.person.details.proto.java.PersonDetailsBean.PersonDetails;

public class PersonDetailsBeanSerializer extends Adapter implements Serializer<PersonDetails> {

	@Override
	public byte[] serialize(String topic, PersonDetails detailsBean) {
		return detailsBean.toByteArray();
	}
}