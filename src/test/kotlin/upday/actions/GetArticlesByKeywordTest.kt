package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.GetArticleByKeywordRequestMother

internal class GetArticlesByKeywordTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var getArticlesByKeyword: GetArticlesByKeyword

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    getArticlesByKeyword = GetArticlesByKeyword(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can get all articles for a specific keyword`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = GetArticleByKeywordRequestMother.simple()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = getArticlesByKeyword(request)

    // then
    Assertions.assertEquals(listOf(dbArticle), response)
  }

  @Test
  fun `should return an empty list if specific keyword is not related to any article`() {
    // given
    val request = GetArticleByKeywordRequestMother.simple()

    // when
    val response = getArticlesByKeyword(request)

    // then
    Assertions.assertTrue(response.isEmpty())
  }

}
