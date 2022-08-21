package hr.dpilipovic.common.action;

public interface Record {

  void startRecording(Integer channel);

  void pauseRecording();

  void resumeRecording();

  void stopRecording();

}
