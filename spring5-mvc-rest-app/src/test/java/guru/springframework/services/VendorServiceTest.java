package guru.springframework.services;

import guru.springframework.api.v1.mappers.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-04-09 15:01
 */
class VendorServiceTest {

  private static final String BASE_URL = VendorController.BASE_URL;

  private static final Long ID = 2L;
  private static final String NAME = "Jimmy";
  @Mock
  private VendorRepository vendorRepository;
  private VendorService vendorService;

  private Vendor vendor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    vendor = new Vendor();
    vendor.setId(ID);
    vendor.setName(NAME);
  }

  @Test
  void getAllVendors() {
    // Given
    when(vendorRepository.findAll()).thenReturn(Collections.singletonList(vendor));
    // When
    final List<VendorDTO> allVendors = vendorService.getAllVendors();
    // Then
    assertEquals(1, allVendors.size());
    assertEquals(ID, allVendors.get(0).getId());
    assertEquals(NAME, allVendors.get(0).getName());
    assertEquals(String.format("%s/%d", BASE_URL, ID), allVendors.get(0).getVendorUrl());
  }

  @Test
  void getVendorById() {
    // Given
    given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
    // When
    final VendorDTO dto = vendorService.getVendorById(ID);
    // Then
    then(vendorRepository).should(times(1)).findById(anyLong());
    assertNotNull(dto);
    assertEquals(ID, dto.getId());
    assertEquals(NAME, dto.getName());
    assertThat(dto.getName(), is(equalTo(NAME)));
    assertEquals(String.format("%s/%d", BASE_URL, ID), dto.getVendorUrl());
  }

  @Test
  void createVendor() {
    // Given
    when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
    // When
    final VendorDTO savedDTO = vendorService.createVendor(new VendorDTO());
    // Then
    assertEquals(ID, savedDTO.getId());
    assertEquals(NAME, savedDTO.getName());
    assertEquals(String.format("%s/%d", BASE_URL, ID), savedDTO.getVendorUrl());
  }

  @Test
  void saveVendorByDTO() {
    // Given
    final VendorDTO dto = new VendorDTO();
    dto.setName(NAME);
    when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
    // When
    final VendorDTO savedDTO = vendorService.saveVendorByDTO(ID, dto);
    // Then
    assertEquals(dto.getName(), savedDTO.getName());
    assertEquals(String.format("%s/%d", BASE_URL, ID), savedDTO.getVendorUrl());
  }

  @Test
  void patchVendor() {
    // Given
    final String new_name = "New name";
    final Vendor savedVendor = new Vendor();
    savedVendor.setId(ID);
    savedVendor.setName(new_name);
    when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
    when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
    // When
    final VendorDTO patchedDTO = vendorService.patchVendor(ID, new VendorDTO());
    // Then
    assertEquals(ID, patchedDTO.getId());
    assertEquals(new_name, patchedDTO.getName());
  }

  @Test
  void deleteVendorById() {
    // Given
    given(vendorRepository.existsById(anyLong())).willReturn(true);
    given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());
    // When
    vendorService.deleteVendorById(ID);
    // Then
    verify(vendorRepository, times(1)).deleteById(anyLong());
  }
}
