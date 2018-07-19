package com.keda.minidao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体定义规则
 * 字段不允许存在基本类型，必须都是包装类型(因为基本类型有默认值)
 * 基本数据类型  包装类 byte Byte boolean Boolean short Short char Character int Integer long Long float Float double  Double 
 * @author Administrator
 *
 */
public class Employee implements Serializable {

	/**
	 *主键
	 */
	private String id;
	/**
	 *雇员编号
	 */
	private String empno;
	/**
	 *雇员名
	 */
	private String name;
	/**
	 *年龄
	 */
	private Integer age;
	/**
	 *生日
	 */
	private Date birthday;
	/**
	 *工资
	 */
	private BigDecimal salary;
	/**
	 *create_by
	 */
	private String createBy;
	/**
	 *create_date
	 */
	private Date createDate;
	/**
	 *update_by
	 */
	private String updateBy;
	/**
	 *update_date
	 */
	private Date updateDate;
	
	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getEmpno() {
	    return this.empno;
	}
	public void setEmpno(String empno) {
	    this.empno=empno;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name=name;
	}
	public Integer getAge() {
	    return this.age;
	}
	public void setAge(Integer age) {
	    this.age=age;
	}
	public Date getBirthday() {
	    return this.birthday;
	}
	public void setBirthday(Date birthday) {
	    this.birthday=birthday;
	}
	public BigDecimal getSalary() {
	    return this.salary;
	}
	public void setSalary(BigDecimal salary) {
	    this.salary=salary;
	}
	public String getCreateBy() {
	    return this.createBy;
	}
	public void setCreateBy(String createBy) {
	    this.createBy=createBy;
	}
	public Date getCreateDate() {
	    return this.createDate;
	}
	public void setCreateDate(Date createDate) {
	    this.createDate=createDate;
	}
	public String getUpdateBy() {
	    return this.updateBy;
	}
	public void setUpdateBy(String updateBy) {
	    this.updateBy=updateBy;
	}
	public Date getUpdateDate() {
	    return this.updateDate;
	}
	public void setUpdateDate(Date updateDate) {
	    this.updateDate=updateDate;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empno=" + empno + ", name=" + name + ", age=" + age + ", birthday=" + birthday + ", salary=" + salary + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + "]";
	}
	
}