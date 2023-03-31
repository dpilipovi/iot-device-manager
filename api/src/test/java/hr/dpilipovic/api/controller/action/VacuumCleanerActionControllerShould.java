package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.service.action.VacuumCleanerPowerActionInitiatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = VacuumCleanerActionController.class)
@ContextConfiguration(classes = VacuumCleanerActionController.class)
class VacuumCleanerActionControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VacuumCleanerPowerActionInitiatorService vacuumCleanerPowerActionInitiatorService;

  @Test
  void turnVacuumCleanerOn() throws Exception {
    final var vacuumCleanerId = 1L;

    mockMvc.perform(post("/api/device/vacuum-cleaner/action/on/{vacuumCleanerId}", vacuumCleanerId))
        .andExpect(status().isAccepted());
  }

  @Test
  void turnVacuumCleanerOff() throws Exception {
    final var vacuumCleanerId = 1L;

    mockMvc.perform(post("/api/device/vacuum-cleaner/action/off/{vacuumCleanerId}", vacuumCleanerId))
        .andExpect(status().isAccepted());
  }

}
