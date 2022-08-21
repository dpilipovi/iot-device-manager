package hr.dpilipovic.api.controller.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.service.AirConditionerService;
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
@WebMvcTest(controllers = AirConditionerController.class)
@ContextConfiguration(classes = AirConditionerController.class)
class AirConditionerControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AirConditionerService airConditionerService;

  @Test
  void returnAirConditionerById_whenAirConditionerExists() throws Exception {
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerService.getAirConditionerById(airConditionerDto.getId())).thenReturn(airConditionerDto);

    mockMvc.perform(get("/api/device/air-conditioner/{airConditionerId}", airConditionerDto.getId()))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(airConditionerDto)));
  }

  @Test
  void getNotFound_whenAirConditionerDoesNotExist() throws Exception {
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerService.getAirConditionerById(airConditionerDto.getId())).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/api/device/air-conditioner/{airConditionerId}", airConditionerDto.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateAirConditioner() throws Exception {
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerService.updateAirConditioner(airConditionerDto)).thenReturn(airConditionerDto);

    mockMvc.perform(patch("/api/device/air-conditioner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(airConditionerDto)))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(airConditionerDto)));
  }

  @Test
  void returnNotFound_whenUpdatingAirConditionerThatDoesNotExist() throws Exception {
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerService.updateAirConditioner(airConditionerDto)).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(patch("/api/device/air-conditioner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(airConditionerDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  void createAirConditioner() throws Exception {
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerService.createAirConditioner(airConditionerDto)).thenReturn(airConditionerDto);

    mockMvc.perform(post("/api/device/air-conditioner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(airConditionerDto)))
        .andExpectAll(status().isCreated(), content().json(objectMapper.writeValueAsString(airConditionerDto)));
  }

  @Test
  void deleteAirConditionerById() throws Exception {
    final var airConditionerId = 1L;

    mockMvc.perform(delete("/api/device/air-conditioner/{airConditionerId}", airConditionerId))
        .andExpect(status().isNoContent());
  }

  @Test
  void returnNotFound_whenDeletingAirConditionerByIdThatDoesNotExist() throws Exception {
    final var airConditionerId = 1L;

    doThrow(EntityNotFoundException.class).when(airConditionerService).deleteAirConditionerById(airConditionerId);

    mockMvc.perform(delete("/api/device/air-conditioner/{airConditionerId}", airConditionerId))
        .andExpect(status().isNotFound());
  }

  private AirConditionerDto buildAirConditionerDto() {
    return AirConditionerDto.builder().id(1L).name("Air conditioner").deviceType(DeviceType.AIR_CONDITIONER).deviceStatus(DeviceStatus.OFF).build();
  }

}