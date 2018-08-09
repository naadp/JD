package com.yao.repository;

import com.yao.entity.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 电影Repository接口
 */
public interface IPRepository extends JpaRepository<IP, Integer>,JpaSpecificationExecutor<IP>{

}
