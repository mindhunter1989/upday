package upday.mothers

import upday.actions.GetArticlesByKeyword

object GetArticleByKeywordRequestMother {

  fun simple() =
    GetArticlesByKeyword.Request(
      keyword = ArticleMother.simple().keywords.first(),
    )

}
