package kafka.rest.admin.configuration


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(initializers = [Initializer.class])
@DirtiesContext
@Testcontainers
abstract class IntegrationTest extends Specification {
	@Autowired
	protected MockMvc mockMvc

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		void initialize(ConfigurableApplicationContext context) {
			def kafkaContainer = new KafkaContainer()
			kafkaContainer.start()

			TestPropertyValues.of(
					"spring.kafka.consumer.bootstrap-servers:" + kafkaContainer.getBootstrapServers(),
					"spring.kafka.producer.bootstrap-servers" + kafkaContainer.getBootstrapServers())
					.applyTo(context)
		}
	}
}