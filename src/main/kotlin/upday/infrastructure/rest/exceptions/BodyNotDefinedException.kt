package upday.infrastructure.rest.exceptions

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

data class BodyNotDefinedException(val event: APIGatewayProxyRequestEvent)
  : IllegalArgumentException("Event body is not defined. Event: $event")
