package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import upday.actions.GetArticlesInPeriod
import upday.infrastructure.rest.response.ArticleResponse
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredQueryParam
import upday.security.domain.port.incoming.CheckAuthorityPort
import java.time.Instant

private const val GET_ARTICLES_IN_PERIOD_AUTHORITY = "GET_ARTICLES_IN_PERIOD"

class GetArticlesInPeriodHandler(
  private val mapper: Json,
  private val getArticlesInPeriod: GetArticlesInPeriod,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    getArticlesInPeriod(
      GetArticlesInPeriod.Request(
        from = Instant.parse(event.requiredQueryParam("from")),
        to = Instant.parse(event.requiredQueryParam("to"))
      )
    )
      .let { articles -> mapper.encodeToString(articles.map { article -> ArticleResponse.from(article) } ) }
      .let { payload -> ok(body = payload) }

  override fun getAuthority(): String = GET_ARTICLES_IN_PERIOD_AUTHORITY

}
