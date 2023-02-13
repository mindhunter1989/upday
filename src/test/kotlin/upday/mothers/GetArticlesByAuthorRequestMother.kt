package upday.mothers

import upday.actions.GetArticlesByAuthor

object GetArticlesByAuthorRequestMother {

  fun simple() =
    GetArticlesByAuthor.Request(
      author = ArticleMother.simple().authors.first(),
    )

}
