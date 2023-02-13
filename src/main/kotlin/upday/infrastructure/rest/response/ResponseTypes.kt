package upday.infrastructure.rest.response

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val TEXT_PLAIN = "text/plain;charset=UTF-8"
const val CONTENT_TYPE = "Content-Type"
const val INTERNAL_SERVER_ERROR = "Internal Server Error"
const val APPLICATION_JSON = "application/json;charset=UTF-8"

fun ok(body: String? = null, contentType: String = APPLICATION_JSON) =
  APIGatewayProxyResponseEvent()
    .withStatusCode(200)
    .also { body?.apply {
      it
        .withHeaders(mapOf(CONTENT_TYPE to contentType))
        .withBody(this)!!
    }}!!


fun badRequest(body: String? = null, contentType: String = APPLICATION_JSON) =
  APIGatewayProxyResponseEvent()
    .withStatusCode(400)
    .also { body?.apply {
      it
        .withHeaders(mapOf(CONTENT_TYPE to contentType))
        .withBody(this)
    }}!!

fun forbidden(body: String? = null, contentType: String = TEXT_PLAIN) =
  APIGatewayProxyResponseEvent()
    .withStatusCode(403)
    .also { body?.apply {
      it
        .withHeaders(mapOf(CONTENT_TYPE to contentType))
        .withBody(this)
    }}!!

fun notFound(body: String? = null, contentType: String = TEXT_PLAIN) =
  APIGatewayProxyResponseEvent()
    .withStatusCode(404)
    .also { body?.apply {
      it
        .withHeaders(mapOf(CONTENT_TYPE to contentType))
        .withBody(this)
    }}!!

fun internalServerError(message: String? = INTERNAL_SERVER_ERROR, contentType: String = APPLICATION_JSON) =
  APIGatewayProxyResponseEvent()
    .withStatusCode(500)
    .withHeaders(mapOf(CONTENT_TYPE to contentType))
    .withBody(Json.encodeToString(message?: INTERNAL_SERVER_ERROR))!!
