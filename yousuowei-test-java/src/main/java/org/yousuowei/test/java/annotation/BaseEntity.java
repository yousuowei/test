package org.yousuowei.test.java.annotation;

import java.lang.reflect.Field;
import org.yousuowei.test.java.annotation.AnnotationSetFiled.DataType;

public class BaseEntity {

	/**
	 * 处理@MyAnnotation注解
	 */
	public BaseEntity() {
		annotationSetFiledProess();
		// 可遍历并处理注解
		// Annotation[] annotations = method.getAnnotations();
		// for (Annotation annotation : annotations) {
		// System.out.println("Annotation名称:"
		// + annotation.annotationType().getName());
		// }
	}

	private void annotationSetFiledProess() {
		Class<AnnotationSetFiled> annClass = AnnotationSetFiled.class;
		@SuppressWarnings("unchecked")
		Class<BaseEntity> entityClass = (Class<BaseEntity>) this.getClass();

		Field[] fields = entityClass.getDeclaredFields();
		for (Field demp : fields) {
			if (demp.isAnnotationPresent(annClass)) {
				AnnotationSetFiled ann = demp.getAnnotation(annClass);
				fillValue(demp, ann.dataType(), ann.value());
			}
		}

	}

	private void fillValue(Field filed, DataType dataType, String value) {
		try {
			filed.setAccessible(true);
			if (dataType.equals(DataType.INT)) {
				filed.setInt(this, new Integer(value));
			} else if (dataType.equals(DataType.STRING)) {
				filed.set(this, value);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
