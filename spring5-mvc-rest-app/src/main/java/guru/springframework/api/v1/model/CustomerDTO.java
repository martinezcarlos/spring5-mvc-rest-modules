package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by carlosmartinez on 2019-04-08 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @ApiModelProperty(value = "This is the FirstName", required = true)
  private String firstname;
  private String lastname;
  @JsonProperty("customer_url")
  private String customerUrl;
}
