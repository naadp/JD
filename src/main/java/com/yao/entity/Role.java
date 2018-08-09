package com.yao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_role")
public class Role {
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@Column(length=50)
	private String name; // 角色名称
	
	
	@Column(length=1000)
	private String remarks; // 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remarks=" + remarks + "]";
	}
	
	
	
	
}
