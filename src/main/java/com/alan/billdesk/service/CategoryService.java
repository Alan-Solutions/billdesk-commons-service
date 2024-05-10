package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Category;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.CategoryRepository;
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
public class CategoryService {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private CategoryRepository categoryRepository;

  public BillDeskResponse<List<Category>> findAllByOrderByIdAsc() {
    List<Category> categories = null;
    try {
      categories = categoryRepository.findAllByOrderByIdAsc();
      if (null != categories) {
        return new BillDeskResponse<>(Constants.SUCCESS, categories, commonUtils.emptyJson());
      } else {
        return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
      }
    } catch (DataAccessException dae) {
      logger.error("Error while making call to CategoryService.findAllByOrderByIdAsc() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to CategoryService.findAllByOrderByIdAsc() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Category> saveCategory(Category category) {
    try {
      category = save(category);
      return new BillDeskResponse<>(Constants.SUCCESS, category, commonUtils.emptyJson());
    } catch (DataAccessException dae) {
      logger.error("Error while making call to CategoryService.saveCategory() ", dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to CategoryService.saveCategory() :: Exception", e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
  }

  public BillDeskResponse<Category> update(Category category) {
    Optional<Category> oCategory = findById(category.getId());
    if (oCategory.isPresent()) {
      Category updateCategory = oCategory.get();
      updateCategory.setName(category.getName());
      saveCategory(updateCategory);
      return new BillDeskResponse<>(Constants.SUCCESS, category, commonUtils.emptyJson());
    }
    return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
  }

  public BillDeskResponse<Category> findByName(String name) {
    Category category = categoryRepository.findByName(name);
    if (null != category)
      return new BillDeskResponse<>(Constants.SUCCESS, category, commonUtils.emptyJson());
    return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
  }

  public BillDeskResponse<Category> findCategoryById(int id) {
    Optional<Category> oCategory = null;
    try {
      oCategory = findById(id);
      if (oCategory.isPresent()) {
        return new BillDeskResponse<>(Constants.SUCCESS, oCategory.get(), commonUtils.emptyJson());
      }
    } catch (DataAccessException dae) {
      logger.error("Error while making call to CategoryService.findCategoryById() {}", id, dae);
      throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
    } catch (Exception e) {
      logger.error("Error while making call to CategoryService.findCategoryById() {} :: Exception", id, e);
      throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
    }
    return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
  }

  public void delete(int id) {
    categoryRepository.deleteById(id);
  }

  public void delete(Category category) {
    categoryRepository.delete(category);
  }

  public void deleteAll() {
    categoryRepository.deleteAll();
  }

  private Optional<Category> findById(int id) {
    return categoryRepository.findById(id);
  }

  private Category save(Category category) {
    return categoryRepository.save(category);
  }

  private JSONObject dataNotAvailableJson() {
    return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
  }

}
