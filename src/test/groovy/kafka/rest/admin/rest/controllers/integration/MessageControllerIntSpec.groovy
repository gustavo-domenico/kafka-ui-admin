package kafka.rest.admin.rest.controllers.integration

import kafka.rest.admin.infrastructure.IntegrationSpec

import static kafka.rest.admin.infrastructure.factories.MessageModelFactories.messageContent
import static kafka.rest.admin.infrastructure.factories.TopicModelFactories.anotherTopic
import static kafka.rest.admin.infrastructure.factories.TopicModelFactories.oneTopic
import static kafka.rest.admin.infrastructure.payloads.Payloads.messagePayload
import static kafka.rest.admin.infrastructure.payloads.Payloads.messageRequestPayload
import static kafka.rest.admin.infrastructure.payloads.Payloads.messagesPayload
import static kafka.rest.admin.infrastructure.payloads.Payloads.newMessagePayload
import static kafka.rest.admin.infrastructure.payloads.Payloads.newMessageWithPartitionPayload
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessageControllerIntSpec extends IntegrationSpec {
	def "raw should return message content from topic/partition/offset"() {
		when:
			def mvcResult = mockMvc.perform(
					get("/messages/topic/{topic}/partition/{partition}/offset/{offset}/raw", oneTopic().name, 0, 3))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().string(messageContent()))
	}

	def "offset should return message from topic/partition/offset"() {
		when:
			def mvcResult = mockMvc.perform(
					get("/messages/topic/{topic}/partition/{partition}/offset/{offset}", oneTopic().name, 0, 3))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().json(messagePayload()))
	}

	def "from should return all messages from topic/partition/offset"() {
		when:
			def mvcResult = mockMvc.perform(
					get("/messages/topic/{topic}/partition/{partition}/from/{offset}", oneTopic().name, 0, 2))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().json(messagesPayload()))
	}

	def "last should return last messages from topic/partition"() {
		when:
			def mvcResult = mockMvc.perform(
					get("/messages/topic/{topic}/partition/{partition}/last/{messages}", oneTopic().name, 0, 3))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().json(messagesPayload()))
	}

	def "send should send new message to topic and return it"() {
		when:
			def mvcResult = mockMvc.perform(
					post("/messages/topic/{topic}", oneTopic().name)
							.contentType(APPLICATION_JSON)
							.content(messageRequestPayload()))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().json(newMessagePayload()))
	}

	def "send should send new message to topic/partition and return it"() {
		when:
			def mvcResult = mockMvc.perform(
					post("/messages/topic/{topic}/partition/{partition}", anotherTopic().name, 0)
							.contentType(APPLICATION_JSON)
							.content(messageRequestPayload()))
		then:
			mvcResult.andExpect(status().isOk())
					.andExpect(content().json(newMessageWithPartitionPayload()))
	}
}
