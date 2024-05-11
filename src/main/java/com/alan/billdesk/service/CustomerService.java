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
            if (getCustomerById(customer.getId()).isPresent()) {
                customerResponse = customerRepository.save(customer);
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

    public Optional<Customer> getCustomerById(Long id) {
        try {
            return customerRepository.findById(id);
        } catch (DataAccessException dae) {
            logger.error("Error while making call to CustomerService.updateCustomer() ", dae);
            throw new BillDeskException(dae, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.DATABASE_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error while making call to CustomerService.updateCustomer() :: Exception", e);
            throw new BillDeskException(e, StatusCode.INTERNAL_SERVER_ERROR.value(), ErrorConstants.GENERIC_ERROR);
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(Sort.by("id").ascending());
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    private JSONObject dataNotAvailableJson() {
        return commonUtils.createErrorJson(DataStatusEnum.DATA_NOT_AVAILABLE.getCode(), DataStatusEnum.DATA_NOT_AVAILABLE.getMessage(), Constants.FALSE);
    }

}