package com.springproject.controller;

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
import com.springproject.model.Tower;
import com.springproject.repository.TowerRepository;

@RestController
@RequestMapping()
public class TowerController {

	@Autowired
	private TowerRepository towerRepository;

	@GetMapping("/towers")
	public List<Tower> getAlltower() {
		return this.towerRepository.findAll();
	}

	@GetMapping("/towers/{id}")
	public ResponseEntity<Tower> getTowerById(@PathVariable(value = "id") Long towerId)
			throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id -" + towerId));
		return ResponseEntity.ok().body(tower);
	}

	@PostMapping("/towers")
	public Tower createTower(@RequestBody Tower tower) {
		return this.towerRepository.save(tower);
	}

	@PutMapping("towers/{id}")
	public ResponseEntity<Tower> updateTower(@PathVariable(value = "id") Long towerId,
			@Validated @RequestBody Tower towerDetails) throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id - " + towerId));
		tower.setName(towerDetails.getName());
		return ResponseEntity.ok(this.towerRepository.save(tower));
	}

	@DeleteMapping ("towers/{id}")
	public Map<String,Boolean> deleteTower(@PathVariable(value = "id") Long towerId) throws RNFException
	{
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow (() -> new RNFException ("Tower not found for this id -:" + towerId));
	this.towerRepository.delete(tower);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return response;
	}
}
