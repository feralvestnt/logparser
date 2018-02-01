package com.ef.logparser.comum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter
public class DateTimeConverter implements AttributeConverter<LocalDateTime, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date databaseData) {
		Instant instant = Instant.ofEpochMilli(databaseData.getTime());
	    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

}
