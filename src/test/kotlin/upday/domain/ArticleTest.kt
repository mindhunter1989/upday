package upday.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import upday.mothers.ArticleMother

internal class ArticleTest {

  @Test
  fun `can not create an article with an empty string as id`() {
    val e = assertThrows<IllegalStateException> {
      ArticleMother.emptyId()
    }

    Assertions.assertEquals("Article id should not be empty", e.message)
  }

}
