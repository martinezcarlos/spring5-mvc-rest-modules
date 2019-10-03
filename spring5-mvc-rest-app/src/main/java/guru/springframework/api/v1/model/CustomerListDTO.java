package guru.springframework.api.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by carlosmartinez on 2019-04-08 20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDTO {
  private List<CustomerDTO> customers;
}
