package org.yousuowei.test.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//允许子类继承父类中的注解
@Inherited
// 导出javadoc时是否会显示该注解
@Documented
// SOURCE： 编译器处理完Annotation信息后就没有事了,
// CLASS： 编译器将Annotation存储于class文件中，默认,
// RUNTIME: 编译器将Annotation存储于class文件中，可由VM读入
@Retention(RetentionPolicy.RUNTIME)
// ElementType.ANNOTATION_TYPE 应用于其他注释的元注释
// ElementType.CONSTRUCTOR 构造函数
// ElementType.FIELD 字段
// ElementType.LOCAL_VARIABLE 方法中的本地变量
// ElementType.METHOD 方法
// ElementType.PACKAGE 包
// ElementType.PARAMETER 方法的参数
// ElementType.TYPE 类，接口或者枚举声明
@Target({ ElementType.FIELD })
/**
 * 设计一个注解给属性设置默认值
 * @author jie
 *
 */
public @interface AnnotationSetFiled {

	public enum DataType {
		STRING, INT
	};

	String value();

	DataType dataType();
}
