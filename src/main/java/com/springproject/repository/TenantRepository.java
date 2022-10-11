package com.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springproject.model.Tower;

@Repository
public interface TenantRepository extends JpaRepository<Tower,Long>{

}
