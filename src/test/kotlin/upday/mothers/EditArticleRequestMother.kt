package upday.mothers

import upday.actions.EditArticle

object EditArticleRequestMother {

  fun simpleWithUpdatedText() =
    EditArticle.Request(
      id = ArticleMother.id,
      header = ArticleMother.simple().header,
      shortDescription = ArticleMother.simple().shortDescription,
      text = "updatedTest",
      authors = ArticleMother.simple().authors,
      keywords = ArticleMother.simple().keywords,
    )

}
