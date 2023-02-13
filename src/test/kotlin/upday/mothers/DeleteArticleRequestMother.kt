package upday.mothers

import upday.actions.DeleteArticle

object DeleteArticleRequestMother {

  fun simple() =
    DeleteArticle.Request(
      id = ArticleMother.id,
    )

}
