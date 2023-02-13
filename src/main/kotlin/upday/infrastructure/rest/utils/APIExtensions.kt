package upday.infrastructure.rest.utils

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import upday.infrastructure.rest.exceptions.BodyNotDefinedException
import upday.infrastructure.rest.exceptions.RequiredParamException

fun APIGatewayProxyRequestEvent.requiredPathParam(param: String): String =
  this.pathParameters.getOrElse(param) { throw RequiredParamException(param) }

fun APIGatewayProxyRequestEvent.requiredBodyParams(): String =
  this.body?: throw BodyNotDefinedException(this)

fun APIGatewayProxyRequestEvent.requiredQueryParam(param: String): String =
  this.queryStringParameters.getOrElse(param) { throw RequiredParamException(param) }
