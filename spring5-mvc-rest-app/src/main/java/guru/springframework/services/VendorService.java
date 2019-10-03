package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;
import java.util.List;

/**
 * Created by carlosmartinez on 2019-04-09 14:59
 */
public interface VendorService {

  List<VendorDTO> getAllVendors();

  VendorDTO getVendorById(Long id);

  VendorDTO createVendor(VendorDTO vendorDTO);

  VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

  VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

  void deleteVendorById(Long id);
}
