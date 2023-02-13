package upday.actions

import upday.domain.Articles
import upday.domain.exceptions.ArticleNotFound

class GetArticle(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findById(id = request.id)
      ?: throw ArticleNotFound(request.id)

  data class Request(
    val id: String,
  )

}
