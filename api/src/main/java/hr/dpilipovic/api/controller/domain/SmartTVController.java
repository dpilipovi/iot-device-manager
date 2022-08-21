package hr.dpilipovic.api.controller.domain;

import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.service.SmartTVService;
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
@RequestMapping("/api/device/smart-tv")
public class SmartTVController {

  private final SmartTVService smartTVService;

  @GetMapping("/{smartTVId}")
  public ResponseEntity<SmartTVDto> getSmartTVById(@PathVariable final Long smartTVId) {
    return new ResponseEntity<>(smartTVService.getSmartTVById(smartTVId), HttpStatus.OK);
  }

  @PatchMapping
  @Validated(OnUpdate.class)
  public ResponseEntity<SmartTVDto> updateSmartTV(@RequestBody @Valid final SmartTVDto smartTVDto) {
    return new ResponseEntity<>(smartTVService.updateSmartTV(smartTVDto), HttpStatus.OK);
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<SmartTVDto> createSmartTV(@RequestBody @Valid final SmartTVDto smartTVDto) {
    return new ResponseEntity<>(smartTVService.createSmartTV(smartTVDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{smartTVId}")
  public ResponseEntity<Void> deleteSmartTVById(@PathVariable final Long smartTVId) {
    smartTVService.deleteSmartTVById(smartTVId);

    return ResponseEntity.noContent().build();
  }

}
