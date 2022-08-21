package hr.dpilipovic.common.model;

import hr.dpilipovic.common.action.Record;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.RecordingStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "smart_tv")
@EqualsAndHashCode(callSuper = true)
public class SmartTV extends Device implements Record {

  @Column(name = "channel", nullable = false)
  private Integer channel;

  @Enumerated(EnumType.STRING)
  @Column(name = "recording_status", nullable = false)
  private RecordingStatus recordingStatus;

  @Override
  public void startRecording(final Integer channel) {
    if (!RecordingStatus.NOT_RECORDING.equals(this.recordingStatus)) {
      throw new UnsupportedOperationException("Please stop current recording before starting a new one");
    }

    this.deviceStatus = DeviceStatus.ON;
    this.channel = channel;
    this.recordingStatus = RecordingStatus.RECORDING;
  }

  @Override
  public void pauseRecording() {
    if (!RecordingStatus.RECORDING.equals(this.recordingStatus)) {
      throw new UnsupportedOperationException("Can not pause recording if nothing is being recorded");
    }

    this.recordingStatus = RecordingStatus.PAUSED;
  }

  @Override
  public void resumeRecording() {
    if (!RecordingStatus.PAUSED.equals(this.recordingStatus)) {
      throw new UnsupportedOperationException("Can not resume recording if recording is not paused");
    }

    this.recordingStatus = RecordingStatus.RECORDING;
  }

  @Override
  public void stopRecording() {
    if (RecordingStatus.NOT_RECORDING.equals(this.recordingStatus)) {
      throw new UnsupportedOperationException("Can not stop recording if nothing is being recorded");
    }

    this.recordingStatus = RecordingStatus.NOT_RECORDING;
  }

}
