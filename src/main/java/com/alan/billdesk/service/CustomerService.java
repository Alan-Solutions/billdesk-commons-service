package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.constants.DataStatusEnum;
import com.alan.billdesk.constants.ErrorConstants;
import com.alan.billdesk.constants.StatusCode;
import com.alan.billdesk.entity.Customer;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.CustomerRepository;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private CustomerRepository customerRepository;

    public BillDeskResponse<Customer> saveCustomer(Customer customer) {
        Customer customerResponse = null;
        try {
            customerResponse = customerRepository.save(customer);
            logger.info("Customer Creation - {}", Constants.SUCCESS);
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.saveCustomer() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.saveCustomer() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
        return commonUtils.createResponse(customerResponse, dataNotAvailableJson());
    }

    public BillDeskResponse<Customer> updateCustomer(Customer customer) {
        Customer customerResponse = null;
        try {
            if (customerRepository.findById(customer.getId()).isPresent()) {
                customerResponse = customerRepository.save(customer);
                logger.info("Customer Update - {}", Constants.SUCCESS);
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.updateCustomer() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.updateCustomer() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
        return commonUtils.createResponse(customerResponse, dataNotAvailableJson());
    }

    public BillDeskResponse<Customer> getCustomerById(Long id) {
        Customer customer = null;
        try {
            Optional<Customer> customerResponse = customerRepository.findById(id);
            if (customerResponse.isPresent()) {
                customer = customerResponse.get();
                logger.info("Customer Get - {}", Constants.SUCCESS);
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.getCustomerById() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.getCustomerById() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
        return commonUtils.createResponse(customer, dataNotAvailableJson());
    }

    public BillDeskResponse<List<Customer>> getAllCustomers() {
        List<Customer> allCustomers = null;
        try {
            allCustomers = customerRepository.findAll(Sort.by("id").ascending());
            if (allCustomers.isEmpty()) {
                logger.info("Get All Customers - {}", Constants.EMPTY);
                return new BillDeskResponse<List<Customer>>(Constants.FAILED, null, dataNotAvailableJson());
            } else {
                logger.info("Get All Customers - {}", Constants.SUCCESS);
                return new BillDeskResponse<List<Customer>>(Constants.SUCCESS, allCustomers, commonUtils.emptyJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.getAllCustomers() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.getAllCustomers() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Customer> deleteCustomerById(Long id) {
        try {
            if (customerRepository.findById(id).isPresent()) {
                customerRepository.deleteById(id);
                logger.info("Delete customer by ID - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
            } else {
                logger.info("Delete customer by ID - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.deleteCustomerById() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.deleteCustomerById() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Customer> deleteCustomer(Customer customer) {
        try {
            if (customerRepository.findById(customer.getId()).isPresent()) {
                customerRepository.delete(customer);
                logger.info("Delete customer - {}", Constants.SUCCESS);
                return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
            } else {
                logger.info("Delete customer - {}", Constants.FAILED);
                return new BillDeskResponse<>(Constants.FAILED, null, dataNotAvailableJson());
            }
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.deleteCustomer() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.deleteCustomer() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public BillDeskResponse<Customer> deleteAllCustomers() {
        try {
            customerRepository.deleteAll();
            logger.info("Delete all customers - {}", Constants.SUCCESS);
            return new BillDeskResponse<>(Constants.SUCCESS, null, commonUtils.emptyJson());
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.deleteAllCustomers() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.deleteAllCustomers() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    private JSONObject dataNotAvailableJson() {
        return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
    }

}