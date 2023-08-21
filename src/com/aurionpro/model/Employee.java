package com.aurionpro.model;

import java.util.Date;

public class Employee {

	protected int id;
	protected String name;
	protected String department;
	protected int managerId;
	protected Date date;
	protected double salary;
	protected double commission;
	protected int departmentNo;
	public Employee(int id, String name, String department, int managerId, Date date, double salary, double commission,
			int departmentNo) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.managerId = managerId;
		this.date = date;
		this.salary = salary;
		this.commission = commission;
		this.departmentNo = departmentNo;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(int commission) {
		this.commission = commission;
	}
	public int getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(int departmentNo) {
		this.departmentNo = departmentNo;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", managerId=" + managerId
				+ ", date=" + date + ", salary=" + salary + ", commission=" + commission + ", departmentNo="
				+ departmentNo + "]";
	}
	
	
}
