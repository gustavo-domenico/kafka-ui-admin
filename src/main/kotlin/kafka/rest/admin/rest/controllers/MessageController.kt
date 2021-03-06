package kafka.rest.admin.rest.controllers

import kafka.rest.admin.domain.services.MessageService
import kafka.rest.admin.infrastructure.annotations.RestGetMapping
import kafka.rest.admin.infrastructure.annotations.RestPostMapping
import kafka.rest.admin.rest.requests.MessageRequest
import kafka.rest.admin.rest.resources.MessageListResource
import kafka.rest.admin.rest.resources.MessageResource
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController(val messageService: MessageService) {
    @RestGetMapping(value = ["/topic/{topic}/partition/{partition}/offset/{offset}/raw"])
    fun raw(@PathVariable topic: String, @PathVariable partition: Int, @PathVariable offset: Long): ResponseEntity<String> =
            ok(messageService.offset(topic, partition, offset).content)

    @RestGetMapping(value = ["/topic/{topic}/partition/{partition}/offset/{offset}"])
    fun offset(@PathVariable topic: String, @PathVariable partition: Int, @PathVariable offset: Long): MessageResource =
            MessageResource(topic, partition, messageService.offset(topic, partition, offset))

    @RestGetMapping(value = ["/topic/{topic}/partition/{partition}/from/{offset}"])
    fun from(@PathVariable topic: String, @PathVariable partition: Int, @PathVariable offset: Long): MessageListResource =
            MessageListResource(messageService
                    .from(topic, partition, offset)
                    .map { m -> MessageResource(topic, partition, m) })

    @RestGetMapping(value = ["/topic/{topic}/partition/{partition}/last/{messages}"])
    fun last(@PathVariable topic: String, @PathVariable partition: Int, @PathVariable messages: Long): MessageListResource =
            MessageListResource(messageService.last(topic, partition, messages)
                    .map { m -> MessageResource(topic, partition, m) })

    @RestPostMapping(value = ["/topic/{topic}"])
    fun send(@PathVariable topic: String, @RequestBody request: MessageRequest): MessageResource {
        val (message, partition) = messageService.send(topic, request.key, request.content)
        return MessageResource(topic, partition, message)
    }

    @RestPostMapping(value = ["/topic/{topic}/partition/{partition}"])
    fun send(@PathVariable topic: String, @PathVariable partition: Int, @RequestBody request: MessageRequest): MessageResource =
         MessageResource(topic, partition, messageService.send(topic, partition, request.key, request.content))
}