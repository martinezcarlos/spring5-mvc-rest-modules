package guru.springframework.api.v1.mappers;

import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by carlosmartinez on 2019-04-08 20:17
 */
@Mapper
public interface CustomerMapper {
  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  CustomerDTO entityToDTO(Customer customer);

  Customer dtoToEntity(CustomerDTO customerDTO);
}
