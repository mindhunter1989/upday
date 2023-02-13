package upday.security.domain.port.incoming.`in`

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

data class CheckAuthorityRequest(
  val event: APIGatewayProxyRequestEvent,
  val authority: String,
)
