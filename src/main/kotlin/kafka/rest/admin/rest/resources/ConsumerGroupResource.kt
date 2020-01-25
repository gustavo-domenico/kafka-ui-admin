package kafka.rest.admin.rest.resources

import com.fasterxml.jackson.annotation.JsonUnwrapped
import kafka.rest.admin.domain.models.ConsumerGroup
import kafka.rest.admin.rest.controllers.ConsumerGroupController
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn

class ConsumerGroupResource(consumerGroup: ConsumerGroup) : RepresentationModel<ConsumerGroupResource>() {
    @JsonUnwrapped
    val content: ConsumerGroup = consumerGroup

    init {
        add(linkTo(methodOn(ConsumerGroupController::class.java).get(consumerGroup.id)).withSelfRel())
    }
}