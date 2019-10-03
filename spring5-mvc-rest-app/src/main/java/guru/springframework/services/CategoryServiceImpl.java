package guru.springframework.services;

import guru.springframework.api.v1.mappers.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.repositories.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by carlosmartinez on 2019-04-08 17:24
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryMapper categoryMapper;
  private final CategoryRepository categoryRepository;

  @Override
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll()
        .stream()
        .map(categoryMapper::entityToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDTO getCategoryByName(final String name) {
    return categoryMapper.entityToDTO(categoryRepository.findByName(name));
  }
}
