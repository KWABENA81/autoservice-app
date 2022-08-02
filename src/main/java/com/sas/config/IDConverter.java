package com.sas.config;

import java.math.BigInteger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IDConverter implements AttributeConverter<Integer, BigInteger> {

	@Override
	public BigInteger convertToDatabaseColumn(Integer attribute) {
		return (attribute != null) ? BigInteger.valueOf(attribute) : null;
	}

	@Override
	public Integer convertToEntityAttribute(BigInteger dbData) {
		return (dbData != null) ? dbData.intValue() : null;
	}

}
