package guru.springframework.api.v1.mappers;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by carlosmartinez on 2019-04-08 16:02
 */
class CategoryMapperTest {

  private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

  @Test
  void entityToDTO() {
    // Given
    final Category cat = Category.builder().id(1L).name("Dummy").build();
    // When
    final CategoryDTO dto = categoryMapper.entityToDTO(cat);
    // Then
    assertEquals(cat.getId(), dto.getId());
    assertEquals(cat.getName(), dto.getName());
  }
}
