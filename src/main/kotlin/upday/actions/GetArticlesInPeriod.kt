package upday.actions

import upday.domain.Articles
import java.time.Instant

class GetArticlesInPeriod(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findInPeriod(from = request.from, to = request.to)

  data class Request(
    val from: Instant,
    val to: Instant,
  )

}
