package com.springproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springproject.exception.RNFException;
import com.springproject.model.Tenant;
import com.springproject.model.Tower;
import com.springproject.repository.TenantRepository;
import com.springproject.repository.TowerRepository;

@RestController
@RequestMapping()
public class TenantController {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private TowerRepository towerRepository;

	@GetMapping("tenants")
	public List<Tenant> getAlltenant() {
		return this.tenantRepository.findAll();
	}

	@GetMapping("/towers/{towerId}/tenants/{id}")
	public ResponseEntity<Tenant> getTenantById(@PathVariable(value = "id") Long tenantId) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id -" + tenantId));
		return ResponseEntity.ok().body(tenant);
	}

	@GetMapping("owners/{ownerId}/vehicles")
	public List<Tenant> getAllTenants(@PathVariable Long towerId) {
		List<Tenant> tenants = new ArrayList<>();
		this.tenantRepository.findByTowerId(towerId).forEach(tenants::add);
		return tenants;
	}

	@PostMapping("/towers/{towerId}/tenants")
	public Tenant createTenant(@RequestBody Tenant tenant, @PathVariable(value = "towerId") Long towerId)
			throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id :" + towerId));
		Tenant tenantt = new Tenant();
		tenantt.setTower(tower);
		tenantt.setName(tenant.getName());
		tenantt.setRent(tenant.getRent());
		return this.tenantRepository.save(tenantt);
	}

	@PutMapping("tenants/{id}")
	public ResponseEntity<Tenant> updateTenant(@PathVariable(value = "id") Long tenantId,
			@Validated @RequestBody Tenant tenantDetails) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id - " + tenantId));
		tenant.setRent(tenantDetails.getRent());
		tenant.setName(tenantDetails.getName());
		return ResponseEntity.ok(this.tenantRepository.save(tenant));
	}

	@DeleteMapping("tenants/{id}")
	public Map<String, Boolean> deleteTenant(@PathVariable(value = "id") Long tenantId) throws RNFException {
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new RNFException("Tenant not found for this id -" + tenantId));
		this.tenantRepository.delete(tenant);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
