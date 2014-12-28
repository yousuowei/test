package org.yousuowei.test.java.annotation;

import org.yousuowei.test.java.annotation.AnnotationSetFiled.DataType;


public class UserEntity extends BaseEntity {

	@AnnotationSetFiled(dataType = DataType.STRING, value = "jie")
	private String userName;

	@AnnotationSetFiled(dataType = DataType.INT, value = "100")
	private int userAge;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

}
