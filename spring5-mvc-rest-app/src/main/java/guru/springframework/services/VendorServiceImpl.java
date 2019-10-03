package guru.springframework.services;

import guru.springframework.api.v1.mappers.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by carlosmartinez on 2019-04-09 15:04
 */
@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {

  private static final String BASE_URL = VendorController.BASE_URL;

  private final VendorMapper vendorMapper;
  private final VendorRepository vendorRepository;

  @Override
  public List<VendorDTO> getAllVendors() {
    return vendorRepository.findAll().stream().map(v -> {
      VendorDTO dto = vendorMapper.entityToDTO(v);
      dto.setVendorUrl(getVendorUrl(v.getId()));
      return dto;
    }).collect(Collectors.toList());
  }

  @Override
  public VendorDTO getVendorById(final Long id) {
    return vendorRepository.findById(id).map(v -> {
      VendorDTO dto = vendorMapper.entityToDTO(v);
      dto.setVendorUrl(getVendorUrl(v.getId()));
      return dto;
    }).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public VendorDTO createVendor(final VendorDTO vendorDTO) {
    return saveAndReturnDTO(vendorDTO);
  }

  @Override
  public VendorDTO saveVendorByDTO(final Long id, final VendorDTO vendorDTO) {
    vendorDTO.setId(id);
    return saveAndReturnDTO(vendorDTO);
  }

  @Override
  public VendorDTO patchVendor(final Long id, final VendorDTO vendorDTO) {
    return vendorRepository.findById(id).map(v -> {
      if (!StringUtils.isEmpty(vendorDTO.getName())) {
        v.setName(vendorDTO.getName());
      }
      return saveAndReturnDTO(v);
    }).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public void deleteVendorById(final Long id) {
    if (!vendorRepository.existsById(id)) {
      throw new ResourceNotFoundException();
    }
    vendorRepository.deleteById(id);
  }

  private VendorDTO saveAndReturnDTO(final VendorDTO vendorDTO) {
    return saveAndReturnDTO(vendorMapper.dtoToEntity(vendorDTO));
  }

  private VendorDTO saveAndReturnDTO(final Vendor vendor) {
    final Vendor savedVendor = vendorRepository.save(vendor);
    final VendorDTO returningDTO = vendorMapper.entityToDTO(savedVendor);
    returningDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
    return returningDTO;
  }

  private static String getVendorUrl(final Long id) {
    return String.format("%s/%d", BASE_URL, id);
  }
}
