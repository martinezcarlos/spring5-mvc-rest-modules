package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by carlosmartinez on 2019-04-08 20:18
 */
@Api(tags = "Customers", description = "This is my test Customer Controller")
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_URL)
@RestController
public class CustomerController {

  public static final String BASE_URL = "/api/v1/customers";
  private final CustomerService customerService;

  @ApiOperation(value = "This will create a customer", notes = "These are some notes on the API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDTO createCustomer(@RequestBody final CustomerDTO customerDTO) {
    return customerService.createCustomer(customerDTO);
  }

  @ApiOperation(value = "This will get a list of customers", notes = "These are some notes on the API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CustomerListDTO getListofCustomers() {
    return new CustomerListDTO(customerService.getAllCustomers());
  }

  @GetMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO getCustomerById(@PathVariable final Long id) {
    return customerService.getCustomerById(id);
  }

  @PutMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO updateCustomer(@PathVariable final Long id,
      @RequestBody final CustomerDTO customerDTO) {
    return customerService.saveCustomerByDTO(id, customerDTO);
  }

  @PatchMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO patchCustomer(@PathVariable final Long id,
      @RequestBody final CustomerDTO customerDTO) {
    return customerService.patchCustomer(id, customerDTO);
  }

  @DeleteMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public void deleteCustomer(@PathVariable final Long id) {
    customerService.deleteCustomerById(id);
  }
}
