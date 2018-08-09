package com.yao.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="t_ip")
public class IP {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length=31)
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "id:   " + id + "   ip:   " + ip;
    }
}
