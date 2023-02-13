package upday.infrastructure.rest.utils

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import upday.infrastructure.rest.exceptions.RequiredFieldException

fun JsonObject.field(fieldName: String) = this[fieldName]?.jsonPrimitive?.content

fun JsonObject.asText(fieldName: String) = this.field(fieldName) ?: throw RequiredFieldException(fieldName)
