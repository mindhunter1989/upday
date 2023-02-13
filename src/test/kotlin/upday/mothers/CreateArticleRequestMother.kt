package upday.mothers

import upday.actions.CreateArticle

object CreateArticleRequestMother {

  fun simple() =
    CreateArticle.Request(
      header = ArticleMother.simple().header,
      shortDescription = ArticleMother.simple().shortDescription,
      text = ArticleMother.simple().text,
      authors = ArticleMother.simple().authors,
      keywords = ArticleMother.simple().keywords,
    )

}
