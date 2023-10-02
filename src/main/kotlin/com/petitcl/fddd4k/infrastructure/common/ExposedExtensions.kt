package com.petitcl.fddd4k.infrastructure.common

import arrow.core.raise.Raise
import com.petitcl.fddd4k.domain.common.ApplicationError
import com.petitcl.fddd4k.domain.common.GenericError
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.upsert

context(Raise<ApplicationError>)
fun <Id : Comparable<Id>> IdTable<Id>.upsertOneV1(
    body: IdTable<Id>.(UpdateBuilder<*>) -> Unit
): ResultRow = upsert(body = body).resultedValues?.firstOrNull()
    ?: raise(GenericError("Unexpected row count: 0"))
