package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.ProductDetails;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.ProductDetailsRepository;
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
public class ProductDetailsService {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private ProductDetailsRepository pdRepository;

  public BillDeskResponse<List<ProductDetails>> findAllByOrderByIdAsc() {
    try {
      List<ProductDetails> productDetails = pdRepository.findAllByOrderByIdAsc();
      return commonUtils.createResponse(productDetails, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.findAllByOrderByIdAsc() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.findAllByOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<ProductDetails> findProductDetailsById(int id) {
    try {
      return commonUtils.createResponse(findById(id), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.findProductDetailsById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.findProductDetailsById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<List<ProductDetails>> findProductDetailsByTax(double tax) {
    try {
      return commonUtils.createResponse(findByTax(tax), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.findProductDetailsByTax() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.findProductDetailsByTax() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<ProductDetails> saveProductDetails(ProductDetails productDetails) {
    try {
      return commonUtils.createResponse(save(productDetails), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.saveProductDetails() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.saveProductDetails() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public void deleteById(int id) {
    try {
      pdRepository.deleteById(id);
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.deleteById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.deleteById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public void deleteAll() {
    try {
      pdRepository.deleteAll();
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductDetailsService.deleteAll() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductDetailsService.deleteAll() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  private Optional<ProductDetails> findById(int id) {
    return pdRepository.findById(id);
  }

  private List<ProductDetails> findByPrice(double price) {
    return pdRepository.findByPrice(price);
  }

  private List<ProductDetails> findByTax(double tax) {
    return pdRepository.findByTax(tax);
  }

  private ProductDetails save(ProductDetails productDetails) {
    return pdRepository.save(productDetails);
  }

  private JSONObject dataNotAvailableJson() {
    return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
  }

}
