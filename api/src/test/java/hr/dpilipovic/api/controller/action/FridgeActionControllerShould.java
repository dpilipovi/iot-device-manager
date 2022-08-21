package hr.dpilipovic.api.controller.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.api.service.action.FridgeActionInitiatorService;
import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = FridgeActionController.class)
@ContextConfiguration(classes = FridgeActionController.class)
class FridgeActionControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private FridgeActionInitiatorService fridgeActionInitiatorService;

  @Test
  void turnFridgeOn() throws Exception {
    final var fridgeId = 1L;

    mockMvc.perform(post("/api/device/fridge/action/on/{fridgeId}", fridgeId))
        .andExpect(status().isAccepted());
  }

  @Test
  void turnFridgeOff() throws Exception {
    final var fridgeId = 1L;

    mockMvc.perform(post("/api/device/fridge/action/off/{fridgeId}", fridgeId))
        .andExpect(status().isAccepted());
  }

  @Test
  void changeFridgeTemperature() throws Exception {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();

    mockMvc.perform(post("/api/device/fridge/action/temperature")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(changeTemperatureRequestDto)))
        .andExpectAll(status().isAccepted());
  }

}