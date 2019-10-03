package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by carlosmartinez on 2019-04-09 14:17
 */
@Data
public class VendorDTO {

  @ApiModelProperty(value = "Identificator of the vendor")
  private Long id;
  @ApiModelProperty(value = "Name of the vendor", required = true)
  private String name;
  @JsonProperty("vendor_url")
  private String vendorUrl;
}
