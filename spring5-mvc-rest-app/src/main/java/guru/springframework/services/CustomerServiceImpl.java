package guru.springframework.services;

import guru.springframework.api.v1.mappers.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by carlosmartinez on 2019-04-08 20:14
 */
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerMapper customerMapper;
  private final CustomerRepository customerRepository;

  @Override
  public List<CustomerDTO> getAllCustomers() {
    return customerRepository.findAll().stream().map(customer -> {
      CustomerDTO customerDTO = customerMapper.entityToDTO(customer);
      customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
      return customerDTO;
    }).collect(Collectors.toList());
  }

  @Override
  public CustomerDTO getCustomerById(final Long id) {

    return customerRepository.findById(id).map(c -> {
      CustomerDTO dto = customerMapper.entityToDTO(c);
      dto.setCustomerUrl(getCustomerUrl(id));
      return dto;
    }).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public CustomerDTO createCustomer(final CustomerDTO customerDTO) {
    return saveAndReturnDTO(customerMapper.dtoToEntity(customerDTO));
  }

  @Override
  public CustomerDTO saveCustomerByDTO(final Long id, final CustomerDTO customerDTO) {
    final Customer customer = customerMapper.dtoToEntity(customerDTO);
    customer.setId(id);
    return saveAndReturnDTO(customer);
  }

  @Override
  public CustomerDTO patchCustomer(final Long id, final CustomerDTO customerDTO) {
    return customerRepository.findById(id).map(customer -> {

      if (customerDTO.getFirstname() != null) {
        customer.setFirstname(customerDTO.getFirstname());
      }

      if (customerDTO.getLastname() != null) {
        customer.setLastname(customerDTO.getLastname());
      }

      return saveAndReturnDTO(customer);
    }).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public void deleteCustomerById(final Long id) {
    if (!customerRepository.existsById(id)) {
      throw new ResourceNotFoundException();
    }
    customerRepository.deleteById(id);
  }

  private CustomerDTO saveAndReturnDTO(final Customer customer) {
    final Customer savedCustomer = customerRepository.save(customer);
    final CustomerDTO returningDTO = customerMapper.entityToDTO(savedCustomer);
    returningDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
    return returningDTO;
  }

  private String getCustomerUrl(final Long id) {
    return String.format("%s/%d", CustomerController.BASE_URL, id);
  }
}
