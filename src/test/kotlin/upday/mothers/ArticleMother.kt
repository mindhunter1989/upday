package upday.mothers

import upday.domain.Article

object ArticleMother {

  const val id = "articleId"

  fun simple() =
    Article(
      id = id,
      header = "header",
      shortDescription = "shortDescription",
      text = "text",
      publishDate = InstantMother.february_6th_2023(),
      authors = setOf("author"),
      keywords = setOf("keyword"),
    )

  fun emptyId() =
    Article(
      id = "",
      header = "header",
      shortDescription = "shortDescription",
      text = "text",
      publishDate = InstantMother.february_6th_2023(),
      authors = setOf("author"),
      keywords = setOf("keyword"),
    )

}
