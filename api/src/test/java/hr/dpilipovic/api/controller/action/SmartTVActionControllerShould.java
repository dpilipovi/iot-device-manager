package hr.dpilipovic.api.controller.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.api.service.action.SmartTVActionInitiatorService;
import hr.dpilipovic.common.dto.RecordingRequestDto;
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
@WebMvcTest(controllers = SmartTVActionController.class)
@ContextConfiguration(classes = SmartTVActionController.class)
class SmartTVActionControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private SmartTVActionInitiatorService smartTVActionInitiatorService;

  @Test
  void turnSmartTVOn() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(post("/api/device/smart-tv/action/on/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

  @Test
  void turnSmartTVOff() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(post("/api/device/smart-tv/action/off/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

  @Test
  void startSmartTVRecording() throws Exception{
    final var recordingRequestDto = RecordingRequestDto.builder().id(1L).channel(1).build();

    mockMvc.perform(post("/api/device/smart-tv/action/recording/start")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(recordingRequestDto)))
        .andExpectAll(status().isAccepted());
  }

  @Test
  void pauseSmartTVRecording() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(post("/api/device/smart-tv/action/recording/pause/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

  @Test
  void resumeSmartTVRecording() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(post("/api/device/smart-tv/action/recording/resume/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

  @Test
  void stopSmartTVRecording() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(post("/api/device/smart-tv/action/recording/stop/{smartTVId}", smartTVId))
        .andExpect(status().isAccepted());
  }

}