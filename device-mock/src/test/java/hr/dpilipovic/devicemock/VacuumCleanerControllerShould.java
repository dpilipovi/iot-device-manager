package hr.dpilipovic.devicemock;

import hr.dpilipovic.devicemock.controller.VacuumCleanerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = VacuumCleanerController.class)
@ContextConfiguration(classes = VacuumCleanerController.class)
class VacuumCleanerControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void receiveActionCommand() throws Exception {
    final var vacuumCleanerId = 1L;

    mockMvc.perform(get("/mock/device/vacuum-cleaner/{vacuumCleanerId}", vacuumCleanerId))
        .andExpect(status().isAccepted());
  }

}
