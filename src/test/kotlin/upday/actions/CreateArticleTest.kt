package upday.actions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import upday.doubles.IdGeneratorFake
import upday.doubles.TimeProviderFake
import upday.infrastructure.persistence.InMemoryArticles
import upday.mothers.ArticleMother
import upday.mothers.CreateArticleRequestMother
import upday.mothers.InstantMother

internal class CreateArticleTest {

  private lateinit var inMemoryArticles: InMemoryArticles
  private lateinit var idGeneratorFake: IdGeneratorFake
  private lateinit var timeProviderFake: TimeProviderFake
  private lateinit var createArticle: CreateArticle

  @BeforeEach
  fun setUp() {
    inMemoryArticles = InMemoryArticles()
    idGeneratorFake = IdGeneratorFake()
    timeProviderFake = TimeProviderFake(fixedNow = InstantMother.february_6th_2023())

    createArticle = CreateArticle(
      articles = inMemoryArticles,
      idGenerator = idGeneratorFake,
      timeProvider = timeProviderFake
    )
  }

  @Test
  fun `can create an article`() {
    // given
    val request = CreateArticleRequestMother.simple()
    idGeneratorFake.nextIdToGenerate(ArticleMother.id)

    // when
    val response = createArticle(request)

    // then
    val expectedArticle = ArticleMother.simple()
    Assertions.assertEquals(expectedArticle, response)
  }

}
