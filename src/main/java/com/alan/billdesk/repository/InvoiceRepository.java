package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Invoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByCustomerId(Long customerId, Sort sort);
    List<Invoice> findByUserId(Long userId, Sort sort);

    List<Invoice> findAllByOrderByIdAsc();
}

