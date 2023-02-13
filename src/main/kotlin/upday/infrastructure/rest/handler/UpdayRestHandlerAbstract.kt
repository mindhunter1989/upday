package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.slf4j.LoggerFactory
import upday.domain.exceptions.ArticleNotFound
import upday.domain.exceptions.Forbidden
import upday.infrastructure.rest.exceptions.BodyNotDefinedException
import upday.infrastructure.rest.exceptions.RequiredFieldException
import upday.infrastructure.rest.exceptions.RequiredParamException
import upday.infrastructure.rest.response.badRequest
import upday.infrastructure.rest.response.forbidden
import upday.infrastructure.rest.response.internalServerError
import upday.infrastructure.rest.response.notFound
import upday.security.domain.port.incoming.CheckAuthorityPort
import upday.security.domain.port.incoming.`in`.CheckAuthorityRequest

abstract class UpdayRestHandlerAbstract(
  private val checkAuthority: CheckAuthorityPort
) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  companion object {
    private val log = LoggerFactory.getLogger(UpdayRestHandlerAbstract::class.java)
  }

  abstract fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent

  abstract fun getAuthority(): String

  override fun handleRequest(event: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent =
    try {
      log.debug("Request received: $event")
        .also { checkAuthority(event) }
        .let { handleEvent(event) }
    } catch (exception: Exception) {
      log.error("An error occurred handling $event >>> ${exception.message}")
        .let { handleError(exception) }
    }

  private fun checkAuthority(event: APIGatewayProxyRequestEvent) =
    checkAuthority(
      CheckAuthorityRequest(
        event = event,
        authority = getAuthority()
      )
    )

  private fun handleError(e: Throwable): APIGatewayProxyResponseEvent =
    when (e) {
      is RequiredFieldException -> badRequest(errorMessage("Missing field: ${e.field}"))
      is BodyNotDefinedException -> badRequest(errorMessage("Missing body params"))
      is RequiredParamException -> badRequest(errorMessage("Missing parameter: ${e.param}"))
      is Forbidden -> forbidden(e.message)
      is ArticleNotFound -> notFound(e.message)
      else -> internalServerError()
    }
      .also { log.trace("Exception caught") }

  private fun errorMessage(message: String, errorCode: Int? = null) =
    buildJsonObject {
      errorCode?.let { put("error_code", it) }
      put("message", message)
    }.toString()

}
