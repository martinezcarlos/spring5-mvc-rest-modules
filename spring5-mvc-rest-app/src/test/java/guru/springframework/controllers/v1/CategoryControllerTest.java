package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.exceptions.RestResponseEntityExceptionHandler;
import guru.springframework.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by carlosmartinez on 2019-04-08 18:41
 */
class CategoryControllerTest {

  private static final String NAME = "Jim";

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CategoryController categoryController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
        .build();
  }

  @Test
  void testListCategories() throws Exception {
    final CategoryDTO category1 = new CategoryDTO();
    category1.setId(1L);
    category1.setName(NAME);

    final CategoryDTO category2 = new CategoryDTO();
    category2.setId(2L);
    category2.setName("Bob");

    final List<CategoryDTO> categories = Arrays.asList(category1, category2);

    when(categoryService.getAllCategories()).thenReturn(categories);

    mockMvc.perform(get(CategoryController.BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categories", hasSize(2)));
  }

  @Test
  void testGetByNameCategories() throws Exception {
    final CategoryDTO category1 = new CategoryDTO();
    category1.setId(1L);
    category1.setName(NAME);

    when(categoryService.getCategoryByName(anyString())).thenReturn(category1);

    mockMvc.perform(get(String.format("%s/Jim", CategoryController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(NAME)));
  }

  @Test
  public void testGetByNameNotFound() throws Exception {
    when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);
    mockMvc.perform(get(String.format("%s/Foo", CategoryController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }
}
