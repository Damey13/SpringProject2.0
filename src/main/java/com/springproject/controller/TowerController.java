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

import com.springproject.dto.TowerDto;
import com.springproject.exception.RNFException;
import com.springproject.service.TowerService;

@RestController
@RequestMapping()
public class TowerController {

	
	@Autowired
	private TowerService towerService;

	@GetMapping("/towers")
	public List<TowerDto> getAlltower() {
		return this.towerService.getAlltower();
	}

	@GetMapping("/towers/{id}")
	public ResponseEntity<TowerDto> getTowerById(@PathVariable(value = "id") Long towerId)
			throws RNFException {
		return this.towerService.getTowerById(towerId);
	}

	@PostMapping("/towers")
	public TowerDto createTower(@RequestBody TowerDto tower) {
		return this.towerService.createTower(tower);
	}

	@PutMapping("towers/{id}")
	public ResponseEntity<TowerDto> updateTower(@PathVariable(value = "id") Long towerId,
			@Validated @RequestBody TowerDto towerDetails) throws RNFException {
		return this.towerService.updateTower(towerId,towerDetails);
		
	}

	@DeleteMapping ("towers/{id}")
	public Map<String,Boolean> deleteTower(@PathVariable(value = "id") Long towerId) throws RNFException
	{
		return this.towerService.deleteTower(towerId);

	}
}
