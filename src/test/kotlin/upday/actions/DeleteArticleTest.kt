package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import upday.domain.exceptions.ArticleNotFound
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.DeleteArticleRequestMother

internal class DeleteArticleTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var deleteArticle: DeleteArticle

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    deleteArticle = DeleteArticle(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can delete an article`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = DeleteArticleRequestMother.simple()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = deleteArticle(request)

    // then
    val expectedArticleId = dbArticle.id
    Assertions.assertEquals(expectedArticleId, response)
  }

  @Test
  fun `can not delete an article if not exist in database`() {
    // given
    val request = DeleteArticleRequestMother.simple()

    // then
    assertThrows<ArticleNotFound> {
      // when
      deleteArticle(request)
    }

  }

}
