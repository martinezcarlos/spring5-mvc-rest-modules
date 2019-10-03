package guru.springframework.api.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by carlosmartinez on 2019-04-08 18:51
 */
@Data
@AllArgsConstructor
public class CatorgoryListDTO {

  private List<CategoryDTO> categories;
}
