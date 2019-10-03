package guru.springframework.api.v1.mappers;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by carlosmartinez on 2019-04-08 15:55
 */
@Mapper
public interface CategoryMapper {
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  CategoryDTO entityToDTO(Category category);
}
