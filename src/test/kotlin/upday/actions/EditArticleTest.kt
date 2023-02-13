package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import upday.domain.exceptions.ArticleNotFound
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.EditArticleRequestMother

internal class EditArticleTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var editArticle: EditArticle

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    editArticle = EditArticle(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can edit an article`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = EditArticleRequestMother.simpleWithUpdatedText()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = editArticle(request)

    // then
    val expectedArticle = dbArticle.copy(text = request.text)
    Assertions.assertEquals(expectedArticle, response)
  }

  @Test
  fun `can not edit an article if not exist in database`() {
    // given
    val request = EditArticleRequestMother.simpleWithUpdatedText()

    // then
    assertThrows<ArticleNotFound> {
      // when
      editArticle(request)
    }

  }

}
