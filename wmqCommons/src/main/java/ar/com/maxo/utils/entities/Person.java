package ar.com.maxo.utils.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="person")
public class Person extends Event {

	private String firstName;
	
	private String surName;
	
	private Long dni;
	
	private Integer age;

	public Person() { };
	
	public Person( String firstName, String surName, Long dni, Integer age) {
		this.firstName = firstName;
		this.surName = surName;
		this.dni = dni;
		this.age = age;
	}
	
	public String getFirstName() {
		return firstName;
	}
/*
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}*/

	public String getSurName() {
		return surName;
	}
/*
	public void setSurName(String surName) {
		this.surName = surName;
	}*/

	public Long getDni() {
		return dni;
	}
/*
	public void setDni(Long dni) {
		this.dni = dni;
	}*/

	public Integer getAge() {
		return age;
	}
/*
	public void setAge(Integer age) {
		this.age = age;
	}*/
	
}
