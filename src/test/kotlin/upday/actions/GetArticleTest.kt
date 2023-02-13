package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import upday.domain.exceptions.ArticleNotFound
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.GetArticleRequestMother

internal class GetArticleTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var getArticle: GetArticle

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    getArticle = GetArticle(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can get an article founded by its id`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = GetArticleRequestMother.simple()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = getArticle(request)

    // then
    Assertions.assertEquals(dbArticle, response)
  }

  @Test
  fun `can not get an article if not exist in database`() {
    // given
    val request = GetArticleRequestMother.simple()

    // then
    assertThrows<ArticleNotFound> {
      // when
      getArticle(request)
    }

  }

}
