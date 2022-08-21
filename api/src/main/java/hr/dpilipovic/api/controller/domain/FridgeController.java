package hr.dpilipovic.api.controller.domain;

import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.service.FridgeService;
import hr.dpilipovic.common.util.OnCreate;
import hr.dpilipovic.common.util.OnUpdate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/fridge")
public class FridgeController {

  private final FridgeService fridgeService;

  @GetMapping("/{fridgeId}")
  public ResponseEntity<FridgeDto> getFridgeById(@PathVariable final Long fridgeId) {
    return new ResponseEntity<>(fridgeService.getFridgeById(fridgeId), HttpStatus.OK);
  }

  @PatchMapping
  @Validated(OnUpdate.class)
  public ResponseEntity<FridgeDto> updateFridge(@RequestBody @Valid final FridgeDto fridgeDto) {
    return new ResponseEntity<>(fridgeService.updateFridge(fridgeDto), HttpStatus.OK);
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<FridgeDto> createFridge(@RequestBody @Valid final FridgeDto fridgeDto) {
    return new ResponseEntity<>(fridgeService.createFridge(fridgeDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{fridgeId}")
  public ResponseEntity<Void> deleteFridgeById(@PathVariable final Long fridgeId) {
    fridgeService.deleteFridgeById(fridgeId);

    return ResponseEntity.noContent().build();
  }

}