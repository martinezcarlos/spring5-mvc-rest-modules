package guru.springframework.api.v1.mappers;

import guru.springframework.api.v1.mappers.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by carlosmartinez on 2019-04-09 14:35
 */
class VendorMapperTest {

  private static final Long ID = 1L;
  private static final String NAME = "Dummy name";

  private final VendorMapper mapper = VendorMapper.INSTANCE.INSTANCE;

  @Test
  void entityToDTO() {
    // Given
    final Vendor vendor = new Vendor();
    vendor.setId(ID);
    vendor.setName(NAME);
    // When
    final VendorDTO dto = mapper.entityToDTO(vendor);
    // Then
    assertEquals(ID, dto.getId());
    assertEquals(NAME, dto.getName());
  }

  @Test
  void dtoToEntity() {
    // Given
    final VendorDTO dto = new VendorDTO();
    dto.setId(ID);
    dto.setName(NAME);
    // When
    final Vendor entity = mapper.dtoToEntity(dto);
    // Then
    assertEquals(ID, entity.getId());
    assertEquals(NAME, entity.getName());
  }
}
