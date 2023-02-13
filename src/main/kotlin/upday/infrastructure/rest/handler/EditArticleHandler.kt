package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import upday.actions.EditArticle
import upday.infrastructure.rest.utils.asText
import upday.infrastructure.rest.response.ArticleResponse
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredBodyParams
import upday.security.domain.port.incoming.CheckAuthorityPort

private const val EDIT_ARTICLE_AUTHORITY = "EDIT_ARTICLE"

class EditArticleHandler(
  private val mapper: Json,
  private val editArticle: EditArticle,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    edit(event.toEditArticleRequest())

  override fun getAuthority(): String = EDIT_ARTICLE_AUTHORITY

  private fun edit(request: EditArticle.Request): APIGatewayProxyResponseEvent =
    editArticle(request)
      .let { article -> mapper.encodeToString(ArticleResponse.from(article)) }
      .let { payload -> ok(body = payload) }

  private fun APIGatewayProxyRequestEvent.toEditArticleRequest(): EditArticle.Request =
    mapper.parseToJsonElement(this.requiredBodyParams()).jsonObject
      .let { jsonObject ->
        EditArticle.Request(
          id = jsonObject.asText("id"),
          header = jsonObject.asText("header"),
          shortDescription = jsonObject.asText("shortDescription"),
          text = jsonObject.asText("text"),
          authors = jsonObject.asText("authors").split(",").toSet(),
          keywords = jsonObject.asText("keywords").split(",").toSet(),
        )
      }

}
