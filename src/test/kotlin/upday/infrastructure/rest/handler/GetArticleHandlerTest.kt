package upday.infrastructure.rest.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import upday.actions.GetArticle
import upday.domain.exceptions.ArticleNotFound
import upday.mothers.ArticleMother
import upday.security.domain.port.incoming.CheckAuthorityPort
import upday.security.domain.port.incoming.`in`.CheckAuthorityRequest

private const val GET_ARTICLE_AUTHORITY = "GET_ARTICLE"

@ExtendWith(MockitoExtension::class)
internal class GetArticleHandlerTest {

  private val mapper = Json

  @Mock
  private lateinit var context: Context

  @Mock
  private lateinit var checkAuthority: CheckAuthorityPort

  @Mock
  private lateinit var getArticle: GetArticle

  private lateinit var getArticleHandler: GetArticleHandler

  @BeforeEach
  fun setUp() {
    getArticleHandler = GetArticleHandler(
      mapper = mapper,
      getArticle = getArticle,
      checkAuthority = checkAuthority,
    )
  }

  @Test
  fun `can handle a 200 OK get article request`() {
    val request =
      APIGatewayProxyRequestEvent()
        .apply {
          pathParameters = mutableMapOf("id" to ArticleMother.id)
        }

    whenever(getArticle(GetArticle.Request(id = ArticleMother.id))).thenReturn(ArticleMother.simple())

    val result = getArticleHandler.handleRequest(request, context)

    Assertions.assertEquals(200, result.statusCode)
    Assertions.assertEquals("application/json;charset=UTF-8", result.headers["Content-Type"])
    val articleResponse = mapper.parseToJsonElement(result.body)
    Assertions.assertEquals(ArticleMother.id, articleResponse.jsonObject["id"]?.jsonPrimitive?.content)
    Assertions.assertEquals(ArticleMother.simple().header, articleResponse.jsonObject["header"]?.jsonPrimitive?.content)

    verify(getArticle).invoke(GetArticle.Request(id = ArticleMother.id))
    verify(checkAuthority).invoke(CheckAuthorityRequest(event = request, authority = GET_ARTICLE_AUTHORITY))
  }

  @Test
  fun `can handle a 404 NOT FOUND get article request`() {
    val request =
      APIGatewayProxyRequestEvent()
        .apply {
          pathParameters = mutableMapOf("id" to ArticleMother.id)
        }

    whenever(getArticle(GetArticle.Request(id = ArticleMother.id))).thenThrow(ArticleNotFound(ArticleMother.id))

    val result = getArticleHandler.handleRequest(request, context)
    Assertions.assertEquals(404, result.statusCode)

    verify(getArticle).invoke(GetArticle.Request(id = ArticleMother.id))
    verify(checkAuthority).invoke(CheckAuthorityRequest(event = request, authority = GET_ARTICLE_AUTHORITY))
  }

}
