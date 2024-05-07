package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Unit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnitRepository extends CrudRepository<Unit, Integer> {

  List<Unit> findAllByOrderByIdAsc();

  List<Unit> findByDescription(String name);

  List<Unit> findBySize(double size);

}
