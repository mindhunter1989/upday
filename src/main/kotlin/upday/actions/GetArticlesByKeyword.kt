package upday.actions

import upday.domain.Articles

class GetArticlesByKeyword(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findByKeyword(keyword = request.keyword)

  data class Request(
    val keyword: String,
  )

}
