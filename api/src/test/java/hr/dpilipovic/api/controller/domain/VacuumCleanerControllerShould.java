package hr.dpilipovic.api.controller.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.service.VacuumCleanerService;
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
@WebMvcTest(controllers = VacuumCleanerController.class)
@ContextConfiguration(classes = VacuumCleanerController.class)
class VacuumCleanerControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private VacuumCleanerService vacuumCleanerService;

  @Test
  void returnVacuumCleanerById_whenVacuumCleanerExists() throws Exception {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerService.getVacuumCleanerById(vacuumCleanerDto.getId())).thenReturn(vacuumCleanerDto);

    mockMvc.perform(get("/api/device/vacuum-cleaner/{vacuumCleanerId}", vacuumCleanerDto.getId()))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(vacuumCleanerDto)));
  }

  @Test
  void getNotFound_whenVacuumCleanerDoesNotExist() throws Exception {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerService.getVacuumCleanerById(vacuumCleanerDto.getId())).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/api/device/vacuum-cleaner/{vacuumCleanerId}", vacuumCleanerDto.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateVacuumCleaner() throws Exception {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerService.updateVacuumCleaner(vacuumCleanerDto)).thenReturn(vacuumCleanerDto);

    mockMvc.perform(patch("/api/device/vacuum-cleaner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vacuumCleanerDto)))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(vacuumCleanerDto)));
  }

  @Test
  void returnNotFound_whenUpdatingVacuumCleanerThatDoesNotExist() throws Exception {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerService.updateVacuumCleaner(vacuumCleanerDto)).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(patch("/api/device/vacuum-cleaner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vacuumCleanerDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  void createVacuumCleaner() throws Exception {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerService.createVacuumCleaner(vacuumCleanerDto)).thenReturn(vacuumCleanerDto);

    mockMvc.perform(post("/api/device/vacuum-cleaner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vacuumCleanerDto)))
        .andExpectAll(status().isCreated(), content().json(objectMapper.writeValueAsString(vacuumCleanerDto)));
  }

  @Test
  void deleteVacuumCleanerById() throws Exception {
    final var vacuumCleanerId = 1L;

    mockMvc.perform(delete("/api/device/vacuum-cleaner/{vacuumCleanerId}", vacuumCleanerId))
        .andExpect(status().isNoContent());
  }

  @Test
  void returnNotFound_whenDeletingVacuumCleanerByIdThatDoesNotExist() throws Exception {
    final var vacuumCleanerId = 1L;

    doThrow(EntityNotFoundException.class).when(vacuumCleanerService).deleteVacuumCleanerById(vacuumCleanerId);

    mockMvc.perform(delete("/api/device/vacuum-cleaner/{VacuumCleanerId}", vacuumCleanerId))
        .andExpect(status().isNotFound());
  }

  private VacuumCleanerDto buildVacuumCleanerDto() {
    return VacuumCleanerDto.builder().id(1L).name("Vacuum cleaner").deviceType(DeviceType.VACUUM_CLEANER).deviceStatus(DeviceStatus.OFF).build();
  }

}