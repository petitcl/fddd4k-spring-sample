package com.petitcl.fddd4k.application.common

import com.petitcl.fddd4k.domain.common.DomainEvent
import org.springframework.context.ApplicationEventPublisher


fun ApplicationEventPublisher.publishAll(events: List<DomainEvent>)
    = events.forEach { publishEvent(it) }
