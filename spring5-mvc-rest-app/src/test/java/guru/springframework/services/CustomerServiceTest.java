package guru.springframework.services;

import guru.springframework.api.v1.mappers.CustomerMapper;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-04-08 20:28
 */
class CustomerServiceTest {

  private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
  @Mock
  private CustomerRepository customerRepository;
  private CustomerService customerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    customerService = new CustomerServiceImpl(customerMapper, customerRepository);
  }

  @Test
  void getAllCustomers() {
    //given
    final Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");

    final Customer customer2 = new Customer();
    customer2.setId(2L);
    customer2.setFirstname("Sam");
    customer2.setLastname("Axe");

    when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

    //when
    final List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

    //then
    assertEquals(2, customerDTOS.size());
  }

  @Test
  void getCustomerById() {
    //given
    final Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");

    when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer1));

    //when
    final CustomerDTO customerDTO = customerService.getCustomerById(1L);

    assertEquals("Michale", customerDTO.getFirstname());
  }

  @Test
  void createCustomer() {
    //given
    final CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Jim");

    final Customer savedCustomer = new Customer();
    savedCustomer.setFirstname(customerDTO.getFirstname());
    savedCustomer.setLastname(customerDTO.getLastname());
    savedCustomer.setId(1L);

    when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

    //when
    final CustomerDTO savedDto = customerService.createCustomer(customerDTO);

    //then
    assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
    assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
  }

  @Test
  void saveCustomerByDTO() {

    //given
    final CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Jim");

    final Customer savedCustomer = new Customer();
    savedCustomer.setFirstname(customerDTO.getFirstname());
    savedCustomer.setLastname(customerDTO.getLastname());
    savedCustomer.setId(1L);

    when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

    //when
    final CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

    //then
    assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
    assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
  }

  @Test
  void deleteCustomerById() {
    final Long id = 1L;
    given(customerRepository.existsById(anyLong())).willReturn(true);
    given(customerRepository.findById(anyLong())).willReturn(Optional.empty());
    customerService.deleteCustomerById(id);
    verify(customerRepository, times(1)).deleteById(anyLong());
  }
}
