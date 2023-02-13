package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.GetArticlesByAuthorRequestMother

internal class GetArticlesByAuthorTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var getArticlesByAuthor: GetArticlesByAuthor

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    getArticlesByAuthor = GetArticlesByAuthor(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can get all articles for a given author`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = GetArticlesByAuthorRequestMother.simple()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = getArticlesByAuthor(request)

    // then
    Assertions.assertEquals(listOf(dbArticle), response)
  }

  @Test
  fun `should return an empty list if given author has not any article`() {
    // given
    val request = GetArticlesByAuthorRequestMother.simple()

    // when
    val response = getArticlesByAuthor(request)

    // then
    Assertions.assertTrue(response.isEmpty())
  }

}
