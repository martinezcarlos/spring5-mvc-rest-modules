package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.exceptions.RestResponseEntityExceptionHandler;
import guru.springframework.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by carlosmartinez on 2019-04-09 16:30
 */
class VendorControllerTest extends AbstractRestControllerTest {

  private static final Long ID_1 = 1L;
  private static final Long ID_2 = 2L;
  private static final String NAME_1 = "Mark Twang";
  private static final String NAME_2 = "Jana Mesh ";
  private static final String VENDOR_URL_1 = String.format("%s/%d", VendorController.BASE_URL,
      ID_1);

  @Mock
  private VendorService vendorService;

  @InjectMocks
  private VendorController vendorController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
        .build();
  }

  @Test
  void getAllVendors() throws Exception {
    // Given
    final VendorDTO v1 = new VendorDTO();
    final VendorDTO v2 = new VendorDTO();
    when(vendorService.getAllVendors()).thenReturn(Arrays.asList(v1, v2));
    // When
    // Then
    mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.vendors", hasSize(2)));
  }

  @Test
  void getVendorById() throws Exception {
    // Given
    final VendorDTO v1 = new VendorDTO();
    v1.setId(ID_1);
    v1.setName(NAME_1);
    when(vendorService.getVendorById(anyLong())).thenReturn(v1);
    // When
    // Then
    mockMvc.perform(get(VENDOR_URL_1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(v1.getName())));
  }

  @Test
  void createVendor() throws Exception {
    // Given
    final VendorDTO v1 = new VendorDTO();
    v1.setId(ID_1);
    v1.setName(NAME_1);

    final VendorDTO returnDTO = new VendorDTO();
    returnDTO.setId(ID_1);
    returnDTO.setName(NAME_1);
    returnDTO.setVendorUrl(VENDOR_URL_1);
    when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(returnDTO);

    //when //then
    mockMvc.perform(post(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(v1)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", equalTo(NAME_1)))
        .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_1)));
  }

  @Test
  void deleteVendorById() throws Exception {
    // Given
    //when //then
    mockMvc.perform(delete(VENDOR_URL_1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    verify(vendorService, times(1)).deleteVendorById(anyLong());
  }

  @Test
  void updateVendor() throws Exception {
    // Given
    final VendorDTO v1 = new VendorDTO();
    v1.setId(ID_1);
    v1.setName(NAME_1);

    final VendorDTO returnDTO = new VendorDTO();
    returnDTO.setId(ID_1);
    returnDTO.setName(NAME_1);
    returnDTO.setVendorUrl(VENDOR_URL_1);

    when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

    //when/then
    mockMvc.perform(
        put(VENDOR_URL_1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(asJsonString(v1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(NAME_1)))
        .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_1)));
  }

  @Test
  void patchVendor() throws Exception {
    // Given
    final VendorDTO v1 = new VendorDTO();
    v1.setId(ID_1);
    v1.setName(NAME_1);

    final VendorDTO returnDTO = new VendorDTO();
    returnDTO.setId(ID_1);
    returnDTO.setName(NAME_1);
    returnDTO.setVendorUrl(VENDOR_URL_1);

    when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

    mockMvc.perform(
        patch(VENDOR_URL_1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(asJsonString(v1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(NAME_1)))
        .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_1)));
  }

  @Test
  void testNotFoundException() throws Exception {
    when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
    mockMvc.perform(get(String.format("%s/222", VendorController.BASE_URL)).contentType(
        MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }
}
