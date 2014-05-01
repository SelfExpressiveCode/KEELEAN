package com.sec.framework.text.json;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import com.avaje.ebean.common.BeanList;

public class PlainJsonSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if (value.getClass().equals(BeanList.class)) {
			BeanList<?> beans = (BeanList<?>) value;
			jgen.writeStartArray();
			if (CollectionUtils.isNotEmpty(beans.getActualList())) {
				for (int i = 0; i < beans.size(); i++) {
					serializeBean(beans.get(i), jgen);
				}
			}
			jgen.writeEndArray();
		} else {
			serializeBean(value, jgen);
		}
	}

	/**
	 * 序列化bean
	 * 
	 * @param bean
	 * @param jgen
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private void serializeBean(Object bean, JsonGenerator jgen) throws IOException, JsonProcessingException {
		Object beanLoader = null;
		try {
			Field intetceptField = bean.getClass().getDeclaredField("_ebean_intercept");
			intetceptField.setAccessible(true);
			Object ebeanIntercept = intetceptField.get(bean);
			Field beanLoaderField = ebeanIntercept.getClass().getDeclaredField("beanLoader");
			beanLoaderField.setAccessible(true);
			beanLoader = beanLoaderField.get(ebeanIntercept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (beanLoader == null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(jgen, bean);
		} else {
			jgen.writeStartObject();
			jgen.writeEndObject();
		}
	}
}
