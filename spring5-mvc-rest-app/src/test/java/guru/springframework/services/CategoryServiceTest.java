package guru.springframework.services;

import guru.springframework.api.v1.mappers.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-04-08 18:23
 */
class CategoryServiceTest {

  private static final Long ID = 2L;
  private static final String NAME = "Jimmy";
  private CategoryService categoryService;
  @Mock
  private CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
  }

  @Test
  void getAllCategories() {

    //given
    final List<Category> categories = Arrays.asList(Category.builder().build(),
        Category.builder().build(), Category.builder().build());

    when(categoryRepository.findAll()).thenReturn(categories);

    //when
    final List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

    //then
    assertEquals(3, categoryDTOS.size());
  }

  @Test
  void getCategoryByName() {

    //given
    final Category category = Category.builder().id(ID).name(NAME).build();

    when(categoryRepository.findByName(anyString())).thenReturn(category);

    //when
    final CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

    //then
    assertEquals(ID, categoryDTO.getId());
    assertEquals(NAME, categoryDTO.getName());
  }
}
