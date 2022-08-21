package hr.dpilipovic.api.controller.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.service.FridgeService;
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
@WebMvcTest(controllers = FridgeController.class)
@ContextConfiguration(classes = FridgeController.class)
class FridgeControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private FridgeService fridgeService;

  @Test
  void returnFridgeById_whenFridgeExists() throws Exception {
    final var fridgeDto = buildFridgeDto();

    when(fridgeService.getFridgeById(fridgeDto.getId())).thenReturn(fridgeDto);

    mockMvc.perform(get("/api/device/fridge/{fridgeId}", fridgeDto.getId()))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(fridgeDto)));
  }

  @Test
  void getNotFound_whenFridgeDoesNotExist() throws Exception {
    final var fridgeDto = buildFridgeDto();

    when(fridgeService.getFridgeById(fridgeDto.getId())).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/api/device/fridge/{fridgeId}", fridgeDto.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateFridge() throws Exception {
    final var fridgeDto = buildFridgeDto();

    when(fridgeService.updateFridge(fridgeDto)).thenReturn(fridgeDto);

    mockMvc.perform(patch("/api/device/fridge")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fridgeDto)))
        .andExpectAll(status().isOk(), content().json(objectMapper.writeValueAsString(fridgeDto)));
  }

  @Test
  void returnNotFound_whenUpdatingFridgeThatDoesNotExist() throws Exception {
    final var fridgeDto = buildFridgeDto();

    when(fridgeService.updateFridge(fridgeDto)).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(patch("/api/device/fridge")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fridgeDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  void createFridge() throws Exception {
    final var fridgeDto = buildFridgeDto();

    when(fridgeService.createFridge(fridgeDto)).thenReturn(fridgeDto);

    mockMvc.perform(post("/api/device/fridge")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fridgeDto)))
        .andExpectAll(status().isCreated(), content().json(objectMapper.writeValueAsString(fridgeDto)));
  }

  @Test
  void deleteFridgeById() throws Exception {
    final var fridgeId = 1L;

    mockMvc.perform(delete("/api/device/fridge/{fridgeId}", fridgeId))
        .andExpect(status().isNoContent());
  }

  @Test
  void returnNotFound_whenDeletingFridgeByIdThatDoesNotExist() throws Exception {
    final var fridgeId = 1L;

    doThrow(EntityNotFoundException.class).when(fridgeService).deleteFridgeById(fridgeId);

    mockMvc.perform(delete("/api/device/fridge/{fridgeId}", fridgeId))
        .andExpect(status().isNotFound());
  }

  private FridgeDto buildFridgeDto() {
    return FridgeDto.builder().id(1L).name("Fridge").deviceType(DeviceType.FRIDGE).deviceStatus(DeviceStatus.OFF).build();
  }

}