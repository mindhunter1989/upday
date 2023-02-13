package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import upday.actions.CreateArticle
import upday.infrastructure.rest.utils.asText
import upday.infrastructure.rest.response.ArticleResponse
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredBodyParams
import upday.security.domain.port.incoming.CheckAuthorityPort

private const val CREATE_ARTICLE_AUTHORITY = "CREATE_ARTICLE"

class CreateArticleHandler(
  private val mapper: Json,
  private val createArticle: CreateArticle,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    create(event.toCreateArticleRequest())

  override fun getAuthority(): String = CREATE_ARTICLE_AUTHORITY

  private fun create(request: CreateArticle.Request): APIGatewayProxyResponseEvent  =
    createArticle(request)
      .let { article -> mapper.encodeToString(ArticleResponse.from(article)) }
      .let { payload -> ok(body = payload) }

  private fun APIGatewayProxyRequestEvent.toCreateArticleRequest(): CreateArticle.Request =
    mapper.parseToJsonElement(this.requiredBodyParams()).jsonObject
      .let { jsonObject ->
        CreateArticle.Request(
          header = jsonObject.asText("header"),
          shortDescription = jsonObject.asText("shortDescription"),
          text = jsonObject.asText("text"),
          authors = jsonObject.asText("authors").split(",").toSet(),
          keywords = jsonObject.asText("keywords").split(",").toSet(),
        )
      }

}
