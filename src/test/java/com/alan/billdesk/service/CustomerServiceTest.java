package com.alan.billdesk.service;

import com.alan.billdesk.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = getCustomer(1L, "Customer");
    }

    private Customer getCustomer(long id, String name) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhone(Arrays.asList("2947291478", "1274898798"));
        customer.setDob(new Date(1652064000000L));
        customer.setCreateTs("2023-05-09 12:00:00");
        customer.setAddress("Madurai");
        return customer;
    }

    @Test
    void testSaveCustomer() {
        Customer savedCustomer1 = customerService.saveCustomer(customer);
        assertNotNull(savedCustomer1);
        assertEquals("Customer", savedCustomer1.getName());
        Customer savedCustomer2 = customerService.saveCustomer(getCustomer(2L,"Customer 2"));
        assertNotNull(savedCustomer2);
        assertEquals("Customer2", savedCustomer2.getName());
        Customer savedCustomer3 = customerService.saveCustomer(getCustomer(3L,"Customer 3"));
        assertNotNull(savedCustomer3);
        assertEquals("Customer2", savedCustomer3.getName());
    }

    @Test
    void testUpdateCustomer() {
        customer.setId(1L);
        customer.setName("Customer 1");
        Customer updatedCustomer = customerService.updateCustomer(customer);
        assertNotNull(updatedCustomer);
        assertEquals("Customer 1", updatedCustomer.getName());
    }

    @Test
    void testGetCustomerById() {
        customer.setId(1L);
        Optional<Customer> retrievedCustomer = customerService.getCustomerById(1L);
        assertTrue(retrievedCustomer.isPresent());
        assertEquals("Customer 1", retrievedCustomer.get().getName());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> retrievedCustomers = customerService.getAllCustomers();
        assertNotNull(retrievedCustomers);
        assertEquals(3, retrievedCustomers.size());
        assertEquals("Customer 1", retrievedCustomers.get(0).getName());
        assertEquals("Customer 2", retrievedCustomers.get(1).getName());
        assertEquals("Customer 3", retrievedCustomers.get(1).getName());
    }

    @Test
    void testDeleteCustomerById() {
        customerService.deleteCustomerById(1L);
        assertFalse(customerService.getCustomerById(1L).isPresent());
    }

    @Test
    void testDeleteCustomer() {
        customerService.deleteCustomer(getCustomer(2L,"Customer 2"));
        assertFalse(customerService.getCustomerById(2L).isPresent());
    }

    @Test
    void testDeleteAllCustomers() {
        customerService.deleteAllCustomers();
        assertEquals(0,customerService.getAllCustomers().size());
    }
}