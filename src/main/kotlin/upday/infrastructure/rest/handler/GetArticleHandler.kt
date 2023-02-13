package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import upday.actions.GetArticle
import upday.infrastructure.rest.response.ArticleResponse
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredPathParam
import upday.security.domain.port.incoming.CheckAuthorityPort

private const val GET_ARTICLE_AUTHORITY = "GET_ARTICLE"

class GetArticleHandler(
  private val mapper: Json,
  private val getArticle: GetArticle,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    getArticle(GetArticle.Request(id = event.requiredPathParam("id")))
      .let { article -> mapper.encodeToString(ArticleResponse.from(article)) }
      .let { payload -> ok(body = payload) }

  override fun getAuthority(): String = GET_ARTICLE_AUTHORITY

}
