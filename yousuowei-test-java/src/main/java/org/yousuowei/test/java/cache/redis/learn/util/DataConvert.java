package org.yousuowei.test.java.cache.redis.learn.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yousuowei.test.java.cache.redis.learn.entity.inteface.RedisField;

public class DataConvert {

	private static final String OBJECT_DATA_CONVERT_TAG = "_";

	/**
	 * 将object数据装换成redis存储的数据 如：User<id,name,age>id 为唯一值
	 * 转换为key为：User_name_id,User_age_id value为属性值 new
	 * 
	 * @param obj
	 * @param tag
	 * @return
	 */
	public static Map<String, String> convertObject(Object obj, String tag) {
		HashMap<String, String> result = new HashMap<String, String>();

		Class<? extends Object> objClass = obj.getClass();
		Field[] fields = objClass.getDeclaredFields();

		Object demp = null;
		String fieldValue = null;
		String fieldName = null;
		for (Field field : fields) {
			try {
				demp = field.get(obj);
			} catch (IllegalArgumentException e) {
				ExceptionHandler.handleExceptions(e);
				continue;
			} catch (IllegalAccessException e) {
				ExceptionHandler.handleExceptions(e);
				continue;
			}
			if (null != demp && !demp.toString().trim().equals("")) {
				fieldValue = demp.toString().trim();
				fieldName = field.getName();

				// result.put(createFieldKey(obj, field, tag), value);

				RedisField fieldAnnotation = field
						.getAnnotation(RedisField.class);

//				if (null != fieldAnnotation) {
//					RedisData.DataType dataType = fieldAnnotation.dataType();
//					String value = fieldAnnotation.value();
//					if (dataType.equals(RedisData.DataType.STRING)) {
//
//					} else if (dataType.equals(RedisData.DataType.LIST)) {
//
//					} else if (dataType.equals(RedisData.DataType.SET)) {
//
//					} else if (dataType.equals(RedisData.DataType.SORTSET)) {
//
//					} else if (dataType.equals(RedisData.DataType.HASH)) {
//
//					}
//				}
			}
		}
		return result;
	}

	public static List<Map<String, String>> convertList(List<Object> list,
			String tagName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		String tag = null;
		if (null != list && list.size() > 0) {
			Field field = null;
			Object demoObj = list.get(1);
			try {
				field = demoObj.getClass().getDeclaredField(tagName);
			} catch (SecurityException e) {
				ExceptionHandler.handleExceptions(e);
			} catch (NoSuchFieldException e) {
				ExceptionHandler.handleExceptions(e);
			}
			if (null != field) {
				try {
					tag = field.get(demoObj).toString();
				} catch (IllegalArgumentException e) {
					ExceptionHandler.handleExceptions(e);
				} catch (IllegalAccessException e) {
					ExceptionHandler.handleExceptions(e);
				}
			}
			if (null != tag) {
				for (Object obj : list) {
					result.add(convertObject(obj, tag));
				}
			}
		}
		return result;
	}

	protected static String createFieldKey(Object obj, Field field, String tag) {
		StringBuilder key = new StringBuilder();
		key.append(obj.getClass().getName());
		key.append(OBJECT_DATA_CONVERT_TAG);
		key.append(field.getName());
		key.append(OBJECT_DATA_CONVERT_TAG);
		key.append(tag);
		return key.toString();
	}
}
