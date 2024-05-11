package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Invoice;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.InvoiceRepository;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {

    private final CommonUtils commonUtils;

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(CommonUtils commonUtils, InvoiceRepository invoiceRepository) {
        this.commonUtils = commonUtils;
        this.invoiceRepository = invoiceRepository;
    }

    public BillDeskResponse<Invoice> saveInvoice(Invoice invoice) {
        try {
            Invoice invoiceResponse = invoiceRepository.save(invoice);
            logger.info("Invoice Creation - {}", Constants.SUCCESS);
            return new BillDeskResponse<>(Constants.SUCCESS, invoiceResponse, commonUtils.emptyJson());
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.saveInvoice() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.saveInvoice() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Invoice> updateInvoice(Invoice invoice) {
        try {
            if (invoiceRepository.findById(invoice.getId()).isPresent()) {
                Invoice invoiceResponse = invoiceRepository.save(invoice);
                logger.info("Invoice Update - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, invoiceResponse, commonUtils.emptyJson());
            } else {
                logger.info("Invoice Update - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, invoice, commonUtils.dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.updateInvoice() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.updateInvoice() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Invoice> getInvoiceById(Long id) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findById(id);
            if (invoice.isPresent()) {
                logger.info("Get invoice by ID - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, invoice.get(), commonUtils.emptyJson());
            } else {
                logger.info("Get invoice by ID - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.getInvoiceById() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.getInvoiceById() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<List<Invoice>> getInvoicesByCustomerId(Long customerId) {
        try {
            List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId, Sort.by("id").ascending());
            if (invoices.isEmpty()) {
                logger.info("Get invoices by customer ID - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            } else {
                logger.info("Get invoices by customer ID - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, invoices, commonUtils.emptyJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.getInvoicesByCustomerId() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.getInvoicesByCustomerId() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<List<Invoice>> getInvoicesByUserId(Long userId) {
        try {
            List<Invoice> invoices = invoiceRepository.findByUserId(userId, Sort.by("id").ascending());
            if (invoices.isEmpty()) {
                logger.info("Get invoices by user ID - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            } else {
                logger.info("Get invoices by user ID - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, invoices, commonUtils.emptyJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.getInvoicesByUserId() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.getInvoicesByUserId() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<List<Invoice>> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findAllByOrderByIdAsc();
            if (invoices.isEmpty()) {
                logger.info("Get all invoices - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            } else {
                logger.info("Get all invoices - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, invoices, commonUtils.emptyJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.getAllInvoices() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.getAllInvoices() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Invoice> deleteInvoiceById(Long id) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findById(id);
            if (invoice.isPresent()) {
                invoiceRepository.deleteById(id);
                logger.info("Delete invoice by ID - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
            } else {
                logger.info("Delete invoice by ID - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.deleteInvoiceById() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.deleteInvoiceById() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Invoice> deleteInvoice(Invoice invoice) {
        try {
            Optional<Invoice> deleteInvoice = invoiceRepository.findById(invoice.getId());
            if (deleteInvoice.isPresent()) {
                invoiceRepository.delete(invoice);
                logger.info("Delete invoice - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
            } else {
                logger.info("Delete invoice - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, commonUtils.dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.deleteInvoice() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.deleteInvoice() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Invoice> deleteAllInvoices() {
        try {
            invoiceRepository.deleteAll();
            logger.info("Delete all invoices - {}", Constants.SUCCESS);
            return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
        } catch (DataAccessException dae) {
            logger.error("Error while making call to InvoiceService.deleteAllInvoices() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to InvoiceService.deleteAllInvoices() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

}

