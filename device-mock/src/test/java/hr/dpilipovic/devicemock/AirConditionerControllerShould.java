package hr.dpilipovic.devicemock;

import hr.dpilipovic.devicemock.controller.AirConditionerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AirConditionerController.class)
@ContextConfiguration(classes = AirConditionerController.class)
class AirConditionerControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void receiveActionCommand() throws Exception {
    final var airConditionerId = 1L;

    mockMvc.perform(get("/mock/device/air-conditioner/{airConditionerId}", airConditionerId))
        .andExpect(status().isAccepted());
  }

}
