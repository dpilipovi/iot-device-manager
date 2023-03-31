package hr.dpilipovic.devicemock;

import hr.dpilipovic.devicemock.controller.SmartTVController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = SmartTVController.class)
@ContextConfiguration(classes = SmartTVController.class)
class SmartTVControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void receiveActionCommand() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(get("/mock/device/smart-tv/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

}
