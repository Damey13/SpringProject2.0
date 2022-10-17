package com.springproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.springproject.dto.TowerDto;
import com.springproject.exception.RNFException;
import com.springproject.model.Tower;
import com.springproject.repository.TowerRepository;

@Service
public class TowerService {

	@Autowired
	private TowerRepository towerRepository;

	private TowerDto EntityToDto(Tower tower) {
		TowerDto towerDto = new TowerDto();
		towerDto.setId(tower.getId());
		towerDto.setName(tower.getName());
		return towerDto;
	}

	private Tower DtoToEntity(TowerDto towerDto) {
		Tower tower = new Tower();
		tower.setId(towerDto.getId());
		tower.setName(towerDto.getName());
		return tower;

	}

	public List<TowerDto> getAlltower() {
		return this.towerRepository.findAll().stream().map(this::EntityToDto).collect(Collectors.toList());
	}

	public ResponseEntity<TowerDto> getTowerById(Long towerId) throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id -" + towerId));
		TowerDto towerDto = new TowerDto();
		towerDto = EntityToDto(tower);
		return ResponseEntity.ok().body(towerDto);
	}

	public TowerDto createTower(TowerDto towerDto) {
		Tower tower = DtoToEntity(towerDto);
		this.towerRepository.save(tower);
		return towerDto;
	}

	public ResponseEntity<TowerDto> updateTower(Long towerId, TowerDto towerDetails) throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id - " + towerId));

		tower.setName(towerDetails.getName());
		this.towerRepository.save(tower);
		return ResponseEntity.ok(towerDetails);
	}

	@DeleteMapping("towers/{id}")
	public Map<String, Boolean> deleteTower(Long towerId) throws RNFException {
		Tower tower = towerRepository.findById(towerId)
				.orElseThrow(() -> new RNFException("Tower not found for this id -:" + towerId));
		this.towerRepository.delete(tower);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
