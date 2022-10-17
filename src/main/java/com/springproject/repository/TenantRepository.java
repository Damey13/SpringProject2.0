package com.springproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springproject.dto.TenantDto;
import com.springproject.model.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long>{

	public List<Tenant> findByTowerId(Long towerId);
}
