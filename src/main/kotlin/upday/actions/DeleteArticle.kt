package upday.actions

import upday.domain.Articles
import upday.domain.exceptions.ArticleNotFound

class DeleteArticle(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findById(request.id)
      ?.let { dbArticle ->
        articles.delete(id = dbArticle.id)
      } ?: throw ArticleNotFound(request.id)

  data class Request(
    val id: String,
  )

}
