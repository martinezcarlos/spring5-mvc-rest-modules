package guru.springframework.controllers.v1;

import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.exceptions.RestResponseEntityExceptionHandler;
import guru.springframework.model.CustomerDTO;
import guru.springframework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by carlosmartinez on 2019-04-08 20:25
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest {

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerController customerController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
//    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
        .build();
  }

  @Test
  void testListCustomers() throws Exception {

    //given
    final CustomerDTO customer1 = new CustomerDTO();
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");
    customer1.setCustomerUrl(String.format("%s/1", CustomerController.BASE_URL));

    final CustomerDTO customer2 = new CustomerDTO();
    customer2.setFirstname("Sam");
    customer2.setLastname("Axe");
    customer2.setCustomerUrl(String.format("%s/2", CustomerController.BASE_URL));

    when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

    mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customers", hasSize(2)));
  }

  @Test
  void testGetCustomerById() throws Exception {

    //given
    final CustomerDTO customer1 = new CustomerDTO();
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");
    customer1.setCustomerUrl(String.format("%s/1", CustomerController.BASE_URL));

    when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

    //when
    mockMvc.perform(get(String.format("%s/1", CustomerController.BASE_URL))
        .accept(MediaType.APPLICATION_JSON).contentType(
            MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo("Michale")));
  }

  @Test
  void createCustomer() throws Exception {
    //given
    final CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");
    customer.setLastname("Flintstone");

    final CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname(customer.getLastname());
    returnDTO.setCustomerUrl(String.format("%s/1", CustomerController.BASE_URL));

    given(customerService.createCustomer(any(CustomerDTO.class))).willReturn(returnDTO);

    //when/then
    mockMvc.perform(post(CustomerController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerUrl",
            equalTo(String.format("%s/1", CustomerController.BASE_URL))));
  }

  @Test
  void testUpdateCustomer() throws Exception {
    //given
    final CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");
    customer.setLastname("Flintstone");

    final CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname(customer.getLastname());
    returnDTO.setCustomerUrl(String.format("%s/1", CustomerController.BASE_URL));

    when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(
        returnDTO);

    //when/then
    mockMvc.perform(put(String.format("%s/1", CustomerController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerUrl",
            equalTo(String.format("%s/1", CustomerController.BASE_URL))));
  }

  @Test
  void testPatchCustomer() throws Exception {

    //given
    final CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");

    final CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname("Flintstone");
    returnDTO.setCustomerUrl(String.format("%s/1", CustomerController.BASE_URL));

    when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

    mockMvc.perform(patch(String.format("%s/1", CustomerController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerUrl",
            equalTo(String.format("%s/1", CustomerController.BASE_URL))));
  }

  @Test
  void testDeleteCustomer() throws Exception {
    mockMvc.perform(delete(String.format("%s/1", CustomerController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    verify(customerService).deleteCustomerById(anyLong());
  }

  @Test
  public void testNotFoundException() throws Exception {
    when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
    mockMvc.perform(get(String.format("%s/222", CustomerController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }
}
