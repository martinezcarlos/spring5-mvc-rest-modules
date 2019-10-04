package guru.springframework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by carlosmartinez on 2019-04-09 00:15
 */
abstract class AbstractRestControllerTest {

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
