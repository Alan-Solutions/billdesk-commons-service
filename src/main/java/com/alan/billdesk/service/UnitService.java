package com.alan.billdesk.service;

import com.alan.billdesk.entity.Unit;
import com.alan.billdesk.repository.UnitRepository;
import com.alan.billdesk.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

  @Autowired
  private UnitRepository unitRepository;

  @Autowired
  private CommonUtils commonUtils;

  public List<Unit> findAllByOrderByIdAsc() {
    return unitRepository.findAllByOrderByIdAsc();
  }

  public Unit save(Unit unit) {
    return unitRepository.save(unit);
  }

  public Unit findById(int id) {
    Optional<Unit> unitOptional = unitRepository.findById(id);
    if (unitOptional.isPresent()) {
      return unitOptional.get();
    }
    return null;
  }

  public Unit update(Unit unit) {
    Unit toUpdate = findById(unit.getId());
    setUpdate(unit, toUpdate);
    return save(toUpdate);
  }

  public void deleteById(int id) {
    unitRepository.deleteById(id);
  }

  public void deleteAll() {
    unitRepository.deleteAll();
  }

  private void setUpdate(Unit existingUnit, Unit unitEntity) {
    if (!commonUtils.isNullOrEmpty(existingUnit.getDescription())
      && !unitEntity.getDescription().equals(existingUnit.getDescription())) {
      unitEntity.setDescription(existingUnit.getDescription());
    }
    if (existingUnit.getSize() > 0 && existingUnit.getSize() != unitEntity.getSize()) {
      unitEntity.setSize(existingUnit.getSize());
    }
  }

}
