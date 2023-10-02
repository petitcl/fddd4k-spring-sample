package com.petitcl.fddd4k.domain.common

interface DomainEvent

typealias DomainOperationResult<S> = Pair<S, List<DomainEvent>>
