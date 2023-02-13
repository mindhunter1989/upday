package upday.actions

import upday.domain.Articles

class GetArticlesByAuthor(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findByAuthor(author = request.author)

  data class Request(
    val author: String,
  )

}
