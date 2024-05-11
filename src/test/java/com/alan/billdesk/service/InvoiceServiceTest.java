package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.entity.Customer;
import com.alan.billdesk.entity.Invoice;
import com.alan.billdesk.exception.BillDeskException;
import com.alan.billdesk.repository.InvoiceRepository;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import com.alan.user.entity.Users;
import com.alan.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceServiceTest {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    private Customer customer;
    private Users users;
    private Invoice invoice;

    @BeforeEach
    void setUp(){
        customer = customerService.saveCustomer(getCustomer("c1")).getBody();
        users = userService.save(getUser("u1"));
        invoice = invoiceService.saveInvoice(getInvoice(users, customer)).getBody();
    }

    @AfterEach
    void clear(){
        invoiceService.deleteAllInvoices();
        customerService.deleteAllCustomers();
    }

    @Test
    @Order(1)
    public void testSaveInvoiceSuccess() {
        BillDeskResponse<Invoice> response = invoiceService.saveInvoice(invoice);
        invoice = response.getBody();
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    public void testUpdateInvoice() {
        invoice.setQuantity(5L);
        BillDeskResponse<Invoice> response = invoiceService.updateInvoice(invoice);
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertEquals(5L, response.getBody().getQuantity());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    public void testGetInvoiceById() {
        BillDeskResponse<Invoice> response = invoiceService.getInvoiceById(invoice.getId());
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertEquals(invoice.getId(), response.getBody().getId());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    public void testGetInvoicesByCustomerId() {
        BillDeskResponse<List<Invoice>> response = invoiceService.getInvoicesByCustomerId(invoice.getCustomer().getId());
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    public void testGetInvoicesByUserId() {
        BillDeskResponse<List<Invoice>> response = invoiceService.getInvoicesByUserId(invoice.getUser().getId());
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    public void testGetAllInvoices() {
        BillDeskResponse<List<Invoice>> response = invoiceService.getAllInvoices();
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(7)
    public void testDeleteInvoiceById() {
        BillDeskResponse<Invoice> response = invoiceService.deleteInvoiceById(invoice.getId());
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNull(response.getBody());
    }

    @Test
    @Order(8)
    public void testDeleteInvoice() {
        BillDeskResponse<Invoice> response = invoiceService.deleteInvoice(invoice);
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNull(response.getBody());
    }



    @Test
    @Order(9)
    public void testSaveInvoiceDataAccessException() {
        Invoice invoice = new Invoice();
        InvoiceRepository invoiceRepository = mock(InvoiceRepository.class);
        InvoiceService invoiceService1 = new InvoiceService(mock(CommonUtils.class), invoiceRepository);

        DataAccessException dae = new DataAccessException("Test Data Access Exception") {
            @Override
            public String getMessage() {
                return "Test Data Access Exception";
            }
        };
        when(invoiceRepository.save(invoice)).thenThrow(dae);
        assertThrows(BillDeskException.class, () -> invoiceService1.saveInvoice(invoice));
    }

    @Test
    @Order(10)
    public void testSaveInvoiceGenericException() {
        Invoice invoice = new Invoice();
        InvoiceRepository invoiceRepository = mock(InvoiceRepository.class);
        InvoiceService invoiceService1 = new InvoiceService(mock(CommonUtils.class), invoiceRepository);
        RuntimeException e = new RuntimeException("Test Generic Exception");
        when(invoiceRepository.save(invoice)).thenThrow(e);
        assertThrows(BillDeskException.class, () -> invoiceService1.saveInvoice(invoice));
    }

    @Test
    @Order(11)
    public void testDeleteAllInvoice() {
        BillDeskResponse<Invoice> response = invoiceService.deleteAllInvoices();
        assertEquals(Constants.SUCCESS, response.getStatus());
        assertNull(response.getBody());
    }

    public Customer getCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(Arrays.asList("2947291478", "1274898798"));
        customer.setDob(new Timestamp(System.currentTimeMillis()));
        customer.setCreateTs("2023-05-09 12:00:00");
        customer.setAddress("Madurai");
        return customer;
    }

    public Users getUser(String name) {
        Users user = new Users();
        user.setStatus("Active");
        user.setCreatedTs(LocalDateTime.of(2020, 1, 18, 0, 0));
        user.setFirstName(name);
        user.setPassword("Suriya");
        user.setLastName(name);
        user.setMobileNo("987248798");
        user.setRoles(List.of("aAdmin"));
        return user;
    }

    public Invoice getInvoice(Users user, Customer customer){
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setUser(user);
        invoice.setType("General");
        invoice.setQuantity(2L);
        invoice.setCreateTs(new Timestamp(System.currentTimeMillis()));
        return  invoice;
    }


}
