package com.foxminded.school.models;

import java.util.Objects;

public class Student {

	private int id;
	private int groupId;
	private String firstName;
	private String lastName;

	public Student() {
	}

	public Student(int id, int groupId, String firstName, String lastName) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, groupId, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(firstName, other.firstName) && groupId == other.groupId && id == other.id
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", groupId=" + groupId + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}

}
