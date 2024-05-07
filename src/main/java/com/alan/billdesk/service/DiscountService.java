package com.alan.billdesk.service;

import com.alan.billdesk.entity.Discount;
import com.alan.billdesk.repository.DiscountRepository;
import com.alan.billdesk.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private CommonUtils commonUtils;

  public List<Discount> findAllByOrderByIdAsc() {
    return discountRepository.findAllByOrderByIdAsc();
  }

  public Discount findById(int id) {
    Optional<Discount> discountOptional = discountRepository.findById(id);
    if (discountOptional.isPresent()) {
      return discountOptional.get();
    }
    return null;
  }

  public List<Discount> findByDiscountName(String description) {
    return discountRepository.findByDiscountName(description);
  }

  public Discount save(Discount discount) {
    discount.setDiscountName(discount.getDiscountName().toUpperCase());
    return discountRepository.save(discount);
  }

  public Discount update(Discount existingDiscount) {
    Discount discountEntity = findById(existingDiscount.getId());
    update(existingDiscount, discountEntity);
    return save(discountEntity);
  }

  public void deleteById(int id) {
    discountRepository.deleteById(id);
  }

  public void deleteAll(int id) {
    discountRepository.deleteAll();
  }

  private void update(Discount existingDiscount, Discount discountEntity) {
    if (!commonUtils.isNullOrEmpty(existingDiscount.getDiscountName())
      && !commonUtils.equals(discountEntity.getDiscountName(), existingDiscount.getDiscountName())) {
      discountEntity.setDiscountName(existingDiscount.getDiscountName());
    }
    if (null != existingDiscount.getDiscount() && existingDiscount.getDiscount() != discountEntity.getDiscount()) {
      discountEntity.setDiscount(existingDiscount.getDiscount());
    }
    if (!commonUtils.isNullOrEmpty(existingDiscount.getDiscountType())
      && !commonUtils.equals(existingDiscount.getDiscountType(), discountEntity.getDiscountType())) {
      discountEntity.setDiscountType(existingDiscount.getDiscountType());
    }
    if (null != existingDiscount.getDiscountExpiry()
      && !existingDiscount.getDiscountExpiry().equals(discountEntity.getDiscountExpiry())) {
      discountEntity.setDiscountExpiry(existingDiscount.getDiscountExpiry());
    }
    if (!commonUtils.equals(existingDiscount.getStatus(), discountEntity.getStatus())
      && !commonUtils.equals(existingDiscount.getStatus(), discountEntity.getStatus())) {
      discountEntity.setStatus(existingDiscount.getStatus());
    }
    if (!commonUtils.equals(existingDiscount.getDiscountType(), discountEntity.getDiscountType())
      && !commonUtils.equals(existingDiscount.getDiscountType(), discountEntity.getDiscountType())) {
      discountEntity.setDiscountType(existingDiscount.getDiscountType());
    }
  }


}
