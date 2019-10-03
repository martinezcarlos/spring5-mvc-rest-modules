package guru.springframework.api.v1.model;

import java.util.List;
import lombok.Data;

/**
 * Created by carlosmartinez on 2019-04-09 14:19
 */
@Data
public class VendorListDTO {

  private List<VendorDTO> vendors;
}
