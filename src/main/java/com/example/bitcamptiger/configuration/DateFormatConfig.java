package com.example.bitcamptiger.configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class DateFormatConfig {
    private String dateFormat;
    private String datetimeFormat;

    @Autowired
    public DateFormatConfig(@Value("${format.date}") String dateFormat,
                            @Value("${format.date-time}") String datetimeFormat) {
        this.dateFormat=dateFormat;
        this.datetimeFormat=datetimeFormat;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("UTC"));
            jacksonObjectMapperBuilder.simpleDateFormat(datetimeFormat);
            jacksonObjectMapperBuilder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            jacksonObjectMapperBuilder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(datetimeFormat)));
        };
    }
}