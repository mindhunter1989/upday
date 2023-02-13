package upday.mothers

import upday.actions.GetArticle

object GetArticleRequestMother {

  fun simple() =
    GetArticle.Request(
      id = ArticleMother.id,
    )

}
