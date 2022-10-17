package com.springproject.controller;

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

import com.springproject.dto.TenantDto;
import com.springproject.exception.RNFException;
import com.springproject.model.Tenant;
import com.springproject.service.TenantService;

@RestController
@RequestMapping()
public class TenantController {

	@Autowired
	private TenantService tenantService;

	@GetMapping("tenants")
	public List<TenantDto> getAlltenant() {
		return this.tenantService.getAlltenant();
	}

	@GetMapping("/towers/{towerId}/tenants/{id}")
	public ResponseEntity<TenantDto> getTenantById(@PathVariable(value = "id") Long tenantId) throws RNFException {
		return this.tenantService.getTenantById(tenantId);

	}

	@GetMapping("towers/{towerId}/tenants")
	public List<TenantDto> getAllTenants(@PathVariable Long towerId) throws RNFException { 
		return this.tenantService.getAllTenants(towerId);

	}

	@PostMapping("/towers/{towerId}/tenants")
	public Tenant createTenant(@RequestBody TenantDto tenant, @PathVariable(value = "towerId") Long towerId)
			throws RNFException {
		return this.tenantService.createTenant(tenant, towerId);

	}

	@PutMapping("tenants/{id}")
	public ResponseEntity<TenantDto> updateTenant(@PathVariable(value = "id") Long tenantId,
			@Validated @RequestBody TenantDto tenantDetails) throws RNFException {
		return this.tenantService.updateTenant(tenantId, tenantDetails);

	}

	@DeleteMapping("tenants/{id}")
	public Map<String, Boolean> deleteTenant(@PathVariable(value = "id") Long tenantId) throws RNFException {
		return this.tenantService.deleteTenant(tenantId);

	}
}
