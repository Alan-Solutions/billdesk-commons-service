package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Unit;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.UnitRepository;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UnitService {

  @Autowired
  private UnitRepository unitRepository;

  @Autowired
  private CommonUtils commonUtils;

  public BillDeskResponse<List<Unit>> findAllByOrderByIdAsc() {
    try {
      List<Unit> units = unitRepository.findAllByOrderByIdAsc();
      return commonUtils.createResponse(units, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.findAllByOrderByIdAsc() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.findAllByOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Unit> saveUnit(Unit unit) {
    try {
      unit = save(unit);
      return commonUtils.createResponse(unit, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.saveUnit() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.saveUnit() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Unit> findUnitById(int id) {
    Optional<Unit> unitOptional = null;
    try {
      unitOptional = unitRepository.findById(id);
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.findUnitById() {} ", id, dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.findUnitById() {} :: Exception", id, e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
    return commonUtils.createResponse(unitOptional, dataNotAvailableJson());
  }

  public BillDeskResponse<Unit> update(Unit unit) {
    try {
      Optional<Unit> oUnit = findById(unit.getId());
      Unit toUpdate = oUnit.get();
      setUpdate(unit, toUpdate);
      unit = save(unit);
      return commonUtils.createResponse(unit, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.findUnitById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.findUnitById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }

  }

  public void deleteById(int id) {
    try {
      unitRepository.deleteById(id);
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.deleteById() {} ", id, dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.deleteById() {} :: Exception", id, e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public void deleteAll() {
    try {
      unitRepository.deleteAll();
    } catch (DataAccessException dae) {
      logger.error("Error while making call to UnitService.deleteAll() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to UnitService.deleteAll() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  private void setUpdate(Unit existingUnit, Unit unitEntity) {
    if (!commonUtils.equals(existingUnit.getDescription(), unitEntity.getDescription())) {
      unitEntity.setDescription(existingUnit.getDescription());
    }
    if (!commonUtils.equals(existingUnit.getSize(), unitEntity.getSize())) {
      unitEntity.setSize(existingUnit.getSize());
    }
  }

  private Optional<Unit> findById(int id) {
    return unitRepository.findById(id);
  }

  private Unit save(Unit unit) {
    return unitRepository.save(unit);
  }

  private JSONObject dataNotAvailableJson() {
    return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
  }

}
