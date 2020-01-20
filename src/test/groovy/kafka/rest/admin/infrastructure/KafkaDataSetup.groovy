package kafka.rest.admin.infrastructure


import org.testcontainers.containers.KafkaContainer

import static java.util.Map.of
import static kafka.rest.admin.infrastructure.factories.TopicModelFactories.anotherTopic
import static kafka.rest.admin.infrastructure.factories.TopicModelFactories.oneTopic
import static org.apache.kafka.clients.admin.AdminClient.create

class KafkaDataSetup {
	static def loadData(KafkaContainer kafkaContainer) {
		def newTopics = [oneTopic().toNewTopic(), anotherTopic().toNewTopic()]
		def admin = create(of("bootstrap.servers", kafkaContainer.getBootstrapServers()))
		admin.createTopics(newTopics)
	}
}