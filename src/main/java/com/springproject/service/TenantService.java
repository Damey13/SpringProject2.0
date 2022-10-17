package com.springproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springproject.dto.TenantDto;
import com.springproject.dto.TowerDto;
import com.springproject.exception.RNFException;
import com.springproject.model.Tenant;
import com.springproject.model.Tower;
import com.springproject.model.Tenant;
import com.springproject.repository.TenantRepository;
import com.springproject.repository.TowerRepository;

@Service
public class TenantService {

	@Autowired
	private TowerRepository towerRepository;

	@Autowired
	private TenantRepository tenantRepository;

	private TenantDto TenantEntityToDto(Tenant tenant) {
		TenantDto tenantDto = new TenantDto();
		tenantDto.setId(tenant.getId());
		tenantDto.setName(tenant.getName());
		tenantDto.setRent(tenant.getRent());
		tenantDto.setTower(tenant.getTower());
		return tenantDto;
	}

//	private Tenant TenantDtoToEntity(TenantDto tenantDto) {
//		Tenant tenant = new Tenant();
//		tenant.setId(tenantDto.getId());
//		tenant.setName(tenantDto.getName());
//		return tenant;
//
//	}

	public List<TenantDto> getAlltenant() {
		return this.tenantRepository.findAll().stream().map(this::TenantEntityToDto).collect(Collectors.toList());
	}

	public ResponseEntity<TenantDto> getTenantById(Long tenantId) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id -" + tenantId));

		return ResponseEntity.ok().body(TenantEntityToDto(tenant));
	}

	public List<TenantDto> getAllTenants(Long towerId) throws RNFException {
		List<TenantDto> tenants = new ArrayList<>();
		towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id - " + towerId));
		tenants = tenantRepository.findByTowerId(towerId).stream().map(this::TenantEntityToDto).collect(Collectors.toList());

		return tenants;
	}

	public Tenant createTenant(TenantDto tenant, Long towerId) throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id :" + towerId));
		Tenant tenantt = new Tenant();
		tenantt.setTower(tower);
		tenantt.setName(tenant.getName());
		tenantt.setRent(tenant.getRent());
		return this.tenantRepository.save(tenantt);
	}

	public ResponseEntity<TenantDto> updateTenant(Long tenantId, TenantDto tenantDetails) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id - " + tenantId));
		tenant.setRent(tenantDetails.getRent());
		tenant.setName(tenantDetails.getName());
		return ResponseEntity.ok(tenantDetails);
	}

	public Map<String, Boolean> deleteTenant(Long tenantId) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id -" + tenantId));
		this.tenantRepository.delete(tenant);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
