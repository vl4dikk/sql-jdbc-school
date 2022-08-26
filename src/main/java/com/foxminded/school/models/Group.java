package com.foxminded.school.models;

import java.util.Objects;

public class Group {

	private int id;
	private String name;
	private int studentsCount;

	public Group() {
	}

	public Group(int id, String name, int studentsCount) {
		super();
		this.id = id;
		this.name = name;
		this.studentsCount = studentsCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStudentsCount() {
		return studentsCount;
	}

	public void setStudentsCount(int studentsCount) {
		this.studentsCount = studentsCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, studentsCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return id == other.id && Objects.equals(name, other.name) && studentsCount == other.studentsCount;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", studentsCount=" + studentsCount + "]";
	}

}
