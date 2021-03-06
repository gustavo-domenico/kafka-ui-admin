package kafka.rest.admin.infrastructure.payloads

import org.springframework.core.io.ClassPathResource

class Payloads {
	static def messagePayload() {
		new ClassPathResource("payloads/message.json").getFile().text
	}

	static def messagesPayload() {
		new ClassPathResource("payloads/messages.json").getFile().text
	}

	static def consumerGroupOffsetsPayload() {
		new ClassPathResource("payloads/consumerGroupOffsets.json").getFile().text
	}

	static def consumerGroupsPayload() {
		new ClassPathResource("payloads/consumerGroups.json").getFile().text
	}

	static def consumerGroupDetailPayload() {
		new ClassPathResource("payloads/consumerGroupDetail.json").getFile().text
	}

	static def invalidConsumerGroupDetailPayload() {
		new ClassPathResource("payloads/invalidConsumerGroupDetail.json").getFile().text
	}

	static def topicsPayload() {
		new ClassPathResource("payloads/topics.json").getFile().text
	}

	static def topicDetailPayload() {
		new ClassPathResource("payloads/topicDetail.json").getFile().text
	}

	static def clusterPayload() {
		new ClassPathResource("payloads/cluster.json").getFile().text
	}

	static def messageRequestPayload() {
		new ClassPathResource("payloads/messageRequest.json").getFile().text
	}

	static def newMessagePayload() {
		new ClassPathResource("payloads/newMessage.json").getFile().text
	}

	static def newMessageWithPartitionPayload() {
		new ClassPathResource("payloads/newMessageWithPartition.json").getFile().text
	}

	static def topicRequestPayload() {
		new ClassPathResource("payloads/topicRequest.json").getFile().text
	}

	static def newTopicDetailPayload() {
		new ClassPathResource("payloads/newTopicDetail.json").getFile().text
	}
}


