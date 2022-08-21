package hr.dpilipovic.api.controller.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.api.service.action.AirConditionerActionInitiatorService;
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
@WebMvcTest(controllers = AirConditionerActionController.class)
@ContextConfiguration(classes = AirConditionerActionController.class)
class AirConditionerActionControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AirConditionerActionInitiatorService airConditionerActionInitiatorService;

  @Test
  void turnAirConditionerOn() throws Exception {
    final var airConditionerId = 1L;

    mockMvc.perform(post("/api/device/air-conditioner/action/on/{airConditionerId}", airConditionerId))
        .andExpect(status().isAccepted());
  }

  @Test
  void turnAirConditionerOff() throws Exception {
    final var airConditionerId = 1L;

    mockMvc.perform(post("/api/device/air-conditioner/action/off/{airConditionerId}", airConditionerId))
        .andExpect(status().isAccepted());
  }

  @Test
  void changeAirConditionerTemperature() throws Exception {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();

    mockMvc.perform(post("/api/device/air-conditioner/action/temperature")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(changeTemperatureRequestDto)))
        .andExpectAll(status().isAccepted());
  }

}