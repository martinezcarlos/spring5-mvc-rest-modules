package guru.springframework.api.v1.mappers;

import guru.springframework.api.v1.mappers.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by carlosmartinez on 2019-04-08 20:23
 */
class CustomerMapperTest {

  private static final String FIRSTNAME = "Jimmy";
  private static final String LASTNAME = "Fallon";
  private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  @Test
  void entityToDTO() {
    //given
    final Customer customer = new Customer();
    customer.setFirstname(FIRSTNAME);
    customer.setLastname(LASTNAME);

    //when
    final CustomerDTO customerDTO = customerMapper.entityToDTO(customer);

    //then
    assertEquals(FIRSTNAME, customerDTO.getFirstname());
    assertEquals(LASTNAME, customerDTO.getLastname());
  }
}
