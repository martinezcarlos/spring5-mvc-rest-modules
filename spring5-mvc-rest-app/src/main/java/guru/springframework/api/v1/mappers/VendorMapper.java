package guru.springframework.api.v1.mappers;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by carlosmartinez on 2019-04-09 14:26
 */
@Mapper
public interface VendorMapper {
  VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

  @Mapping(target = "vendorUrl", ignore = true)
  VendorDTO entityToDTO(Vendor vendor);

  Vendor dtoToEntity(VendorDTO customerDTO);
}
