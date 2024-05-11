package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Product;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.ProductRepository;
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
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CommonUtils commonUtils;

  public BillDeskResponse<List<Product>> findAllByOrderByIdAsc() {
    try {
      List<Product> products = productRepository.findAllByOrderByIdAsc();
      return commonUtils.createResponse(products, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.findAllByOrderByIdAsc() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.findAllByOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Product> saveProduct(Product product) {
    try {
      product = save(product);
      return commonUtils.createResponse(product, dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.saveItem() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.findAllBsaveItemyOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Product> findProductById(int id) {
    try {
      return commonUtils.createResponse(findById(id), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.findProductById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.findProductById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<List<Product>> findProductByName(String productName) {
    try {
      return commonUtils.createResponse(findByName(productName), dataNotAvailableJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.findProductById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.findProductById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Product> update(Product product) {
    try {
      Optional<Product> productOpt = findById(product.getId());
      if (productOpt.isPresent()) {
        Product pEntity = productOpt.get();
        update(product, pEntity);
        product = save(pEntity);
      }
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.update() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.update() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
    return commonUtils.createResponse(product, dataNotAvailableJson());
  }


  public void deleteById(int id) {
    try {
      productRepository.deleteById(id);
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.deleteById() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.deleteById() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public void deleteAll() {
    try {
      productRepository.deleteAll();
    } catch (DataAccessException dae) {
      logger.error("Error while making call to ProductService.deleteAll() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to ProductService.deleteAll() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  private Optional<Product> findById(int id) {
    return productRepository.findById(id);
  }

  private List<Product> findByName(String name) {
    return productRepository.findByProductName(name);
  }

  private Product save(Product item) {
    return productRepository.save(item);
  }

  private void update(Product existingProduct, Product productEntity) {
    if (!commonUtils.equals(existingProduct.getProductName(), productEntity.getProductName())) {
      productEntity.setProductName(existingProduct.getProductName());
    }
    if (!commonUtils.equals(existingProduct.getProductInvoiceName(), productEntity.getProductInvoiceName())) {
      productEntity.setProductInvoiceName(existingProduct.getProductInvoiceName());
    }
  }

  private JSONObject dataNotAvailableJson() {
    return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
  }
}
