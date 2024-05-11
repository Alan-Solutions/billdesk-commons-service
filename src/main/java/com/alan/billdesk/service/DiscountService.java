package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Discount;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.DiscountRepository;
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
public class DiscountService {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private CommonUtils commonUtils;

  public BillDeskResponse<List<Discount>> findAllByOrderByIdAsc() {

    try {
      List<Discount> discounts = discountRepository.findAllByOrderByIdAsc();
      return commonUtils.createResponse(discounts, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.findAllByOrderByIdAsc() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.findAllByOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Discount> findDiscountById(int id) {
    try {
      return commonUtils.createResponse(findById(id), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.findById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.findById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<List<Discount>> findByDiscountName(String name) {
    List<Discount> discounts = null;
    try {
      discounts = discountRepository.findByDiscountName(name.toUpperCase());
      return commonUtils.createResponse(discounts, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.findByDiscountName() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.findByDiscountName() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Discount> saveDiscount(Discount discount) {
    try {
      discount.setDiscountName(discount.getDiscountName().toUpperCase());
      return commonUtils.createResponse(save(discount), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.save() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.save() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Discount> update(Discount existingDiscount) {
    try {
      logger.debug("Discount from user :: {}", existingDiscount);
      Optional<Discount> optionalDiscount = findById(existingDiscount.getId());
      if (optionalDiscount.isPresent()) {
        Discount discountEntity = optionalDiscount.get();
        logger.debug("Discount from DB :: {}", discountEntity);
        update(existingDiscount, discountEntity);
        existingDiscount = save(discountEntity);
        logger.debug("Updated discount  :: {}", existingDiscount);
      }
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.update() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.update() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
    return commonUtils.createResponse(existingDiscount, dataNotAvailableJson());
  }

  public void deleteById(int id) {
    try {
      discountRepository.deleteById(id);
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.deleteById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.deleteById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public void deleteAll() {
    try {
      discountRepository.deleteAll();
    } catch (DataAccessException dae) {
      logger.error("Error while making call to DiscountService.deleteAll() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to DiscountService.deleteAll() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  private Optional<Discount> findById(int id) {
    return discountRepository.findById(id);
  }

  private Discount save(Discount discount) {
    return discountRepository.save(discount);
  }


  private void update(Discount existingDiscount, Discount discountEntity) {
    if (!commonUtils.equals(existingDiscount.getDiscountName(), discountEntity.getDiscountName())) {
      discountEntity.setDiscountName(existingDiscount.getDiscountName().toUpperCase());
    }
    if (!commonUtils.equals(existingDiscount.getDiscount(), discountEntity.getDiscount()) ) {
      discountEntity.setDiscount(existingDiscount.getDiscount());
    }
    if (!commonUtils.equals(existingDiscount.getDiscountType(), discountEntity.getDiscountType())) {
      discountEntity.setDiscountType(existingDiscount.getDiscountType());
    }
    if (!commonUtils.equals(existingDiscount.getDiscountExpiry(), discountEntity.getDiscountExpiry())) {
      discountEntity.setDiscountExpiry(existingDiscount.getDiscountExpiry());
    }
    if (!commonUtils.equals(existingDiscount.getStatus(), discountEntity.getStatus())) {
      discountEntity.setStatus(existingDiscount.getStatus());
    }
  }

  private JSONObject dataNotAvailableJson() {
    return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
  }

}
