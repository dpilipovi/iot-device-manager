package hr.dpilipovic.common.rabbit.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitQueueProperties.class)
public class RabbitQueueConfiguration {

  private final RabbitQueueProperties rabbitQueueProperties;

  @Bean
  public Queue airConditionerPowerOnQueue() {
    return new Queue(rabbitQueueProperties.airConditionerPowerOnQueue());
  }

  @Bean
  public Queue airConditionerPowerOffQueue() {
    return new Queue(rabbitQueueProperties.airConditionerPowerOffQueue());
  }

  @Bean
  public Queue airConditionerChangeTemperatureQueue() {
    return new Queue(rabbitQueueProperties.airConditionerChangeTemperatureQueue());
  }

  @Bean
  public Queue fridgePowerOnQueue() {
    return new Queue(rabbitQueueProperties.fridgePowerOnQueue());
  }

  @Bean
  public Queue fridgePowerOffQueue() {
    return new Queue(rabbitQueueProperties.fridgePowerOffQueue());
  }

  @Bean
  public Queue fridgeChangeTemperatureQueue() {
    return new Queue(rabbitQueueProperties.fridgeChangeTemperatureQueue());
  }

  @Bean
  public Queue smartTVPowerOnQueue() {
    return new Queue(rabbitQueueProperties.smartTVPowerOnQueue());
  }

  @Bean
  public Queue smartTVPowerOffQueue() {
    return new Queue(rabbitQueueProperties.smartTVPowerOffQueue());
  }

  @Bean
  public Queue smartTVStartRecordingQueue() {
    return new Queue(rabbitQueueProperties.smartTVStartRecordingQueue());
  }

  @Bean
  public Queue smartTVPauseRecordingQueue() {
    return new Queue(rabbitQueueProperties.smartTVPauseRecordingQueue());
  }

  @Bean
  public Queue smartTVResumeRecordingQueue() {
    return new Queue(rabbitQueueProperties.smartTVResumeRecordingQueue());
  }

  @Bean
  public Queue smartTVStopRecordingQueue() {
    return new Queue(rabbitQueueProperties.smartTVStopRecordingQueue());
  }

  @Bean
  public Queue vacuumCleanerPowerOnQueue() {
    return new Queue(rabbitQueueProperties.vacuumCleanerPowerOnQueue());
  }

  @Bean
  public Queue vacuumCleanerPowerOffQueue() {
    return new Queue(rabbitQueueProperties.vacuumCleanerPowerOffQueue());
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
