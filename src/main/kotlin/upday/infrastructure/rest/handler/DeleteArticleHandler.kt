package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import upday.actions.DeleteArticle
import upday.infrastructure.rest.response.ok
import upday.infrastructure.rest.utils.requiredPathParam
import upday.security.domain.port.incoming.CheckAuthorityPort

private const val DELETE_ARTICLE_AUTHORITY = "DELETE_ARTICLE"

class DeleteArticleHandler(
  private val mapper: Json,
  private val deleteArticle: DeleteArticle,
  checkAuthority: CheckAuthorityPort,
) : UpdayRestHandlerAbstract(checkAuthority) {

  override fun handleEvent(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent =
    deleteArticle(DeleteArticle.Request(id = event.requiredPathParam("id")))
      .let { deletedArticleId -> mapper.encodeToString(deletedArticleId) }
      .let { payload -> ok(body = payload) }

  override fun getAuthority(): String = DELETE_ARTICLE_AUTHORITY

}
