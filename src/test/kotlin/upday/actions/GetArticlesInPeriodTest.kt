package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.GetArticlesInPeriodRequestMother

internal class GetArticlesInPeriodTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var getArticlesInPeriod: GetArticlesInPeriod

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()

    getArticlesInPeriod = GetArticlesInPeriod(
      articles = inMemoryArticles,
    )
  }

  @Test
  fun `can get all articles for a given period`() {
    // given
    val dbArticle = ArticleMother.simple()
    val request = GetArticlesInPeriodRequestMother.simpleAllFebruary()
    inMemoryArticles.upsert(dbArticle)

    // when
    val response = getArticlesInPeriod(request)

    // then
    Assertions.assertEquals(listOf(dbArticle), response)
  }

  @Test
  fun `should return an empty list if any article was published in a given period`() {
    // given
    val request = GetArticlesInPeriodRequestMother.firstFiveDaysOfFebruary()

    // when
    val response = getArticlesInPeriod(request)

    // then
    Assertions.assertTrue(response.isEmpty())
  }

}
