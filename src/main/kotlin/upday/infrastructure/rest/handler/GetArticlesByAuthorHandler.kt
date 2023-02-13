package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import upday.actions.GetArticlesByAuthor
import upday.infrastructure.rest.response.ArticleResponse
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredPathParam
import upday.security.domain.port.incoming.CheckAuthorityPort

private const val GET_ARTICLES_BY_AUTHOR_AUTHORITY = "GET_ARTICLES_BY_AUTHOR"

class GetArticlesByAuthorHandler(
  private val mapper: Json,
  private val getArticlesByAuthor: GetArticlesByAuthor,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    getArticlesByAuthor(GetArticlesByAuthor.Request(author = event.requiredPathParam("author")))
      .let { articles -> mapper.encodeToString(articles.map { article -> ArticleResponse.from(article) } ) }
      .let { payload -> ok(body = payload) }

  override fun getAuthority(): String = GET_ARTICLES_BY_AUTHOR_AUTHORITY

}
