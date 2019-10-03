package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;
import java.util.List;

/**
 * Created by carlosmartinez on 2019-04-08 20:13
 */
public interface CustomerService {

  List<CustomerDTO> getAllCustomers();

  CustomerDTO getCustomerById(Long id);

  CustomerDTO createCustomer(CustomerDTO customerDTO);

  CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

  CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

  void deleteCustomerById(Long id);
}
