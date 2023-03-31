package hr.dpilipovic.devicemock;

import hr.dpilipovic.devicemock.controller.FridgeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = FridgeController.class)
@ContextConfiguration(classes = FridgeController.class)
class FridgeControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void receiveActionCommand() throws Exception {
    final var fridgeId = 1L;

    mockMvc.perform(get("/mock/device/fridge/{fridgeId}", fridgeId))
        .andExpect(status().isAccepted());
  }

}
