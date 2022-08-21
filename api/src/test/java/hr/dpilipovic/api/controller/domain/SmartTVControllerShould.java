package hr.dpilipovic.api.controller.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.service.SmartTVService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = SmartTVController.class)
@ContextConfiguration(classes = SmartTVController.class)
class SmartTVControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private SmartTVService smartTVService;

  @Test
  void returnSmartTVById_whenSmartTVExists() throws Exception {
    final var smartTVDto = buildSmartTVDto();

    when(smartTVService.getSmartTVById(smartTVDto.getId())).thenReturn(smartTVDto);

    mockMvc.perform(get("/api/device/smart-tv/{smartTVId}", smartTVDto.getId()))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(smartTVDto)));
  }

  @Test
  void getNotFound_whenSmartTVDoesNotExist() throws Exception {
    final var smartTVDto = buildSmartTVDto();

    when(smartTVService.getSmartTVById(smartTVDto.getId())).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/api/device/smart-tv/{smartTVId}", smartTVDto.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateSmartTV() throws Exception {
    final var smartTVDto = buildSmartTVDto();

    when(smartTVService.updateSmartTV(smartTVDto)).thenReturn(smartTVDto);

    mockMvc.perform(patch("/api/device/smart-tv")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(smartTVDto)))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(smartTVDto)));
  }

  @Test
  void returnNotFound_whenUpdatingSmartTVThatDoesNotExist() throws Exception {
    final var smartTVDto = buildSmartTVDto();

    when(smartTVService.updateSmartTV(smartTVDto)).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(patch("/api/device/smart-tv")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(smartTVDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  void createSmartTV() throws Exception {
    final var smartTVDto = buildSmartTVDto();

    when(smartTVService.createSmartTV(smartTVDto)).thenReturn(smartTVDto);

    mockMvc.perform(post("/api/device/smart-tv")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(smartTVDto)))
        .andExpectAll(status().isCreated(), content().json(objectMapper.writeValueAsString(smartTVDto)));
  }

  @Test
  void deleteSmartTVById() throws Exception {
    final var smartTVId = 1L;

    mockMvc.perform(delete("/api/device/smart-tv/{smartTVId}", smartTVId))
        .andExpect(status().isNoContent());
  }

  @Test
  void returnNotFound_whenDeletingSmartTVByIdThatDoesNotExist() throws Exception {
    final var smartTVId = 1L;

    doThrow(EntityNotFoundException.class).when(smartTVService).deleteSmartTVById(smartTVId);

    mockMvc.perform(delete("/api/device/smart-tv/{smartTVId}", smartTVId))
        .andExpect(status().isNotFound());
  }

  private SmartTVDto buildSmartTVDto() {
    return SmartTVDto.builder().id(1L).name("Smart TV").deviceType(DeviceType.SMART_TV).deviceStatus(DeviceStatus.OFF).build();
  }

}