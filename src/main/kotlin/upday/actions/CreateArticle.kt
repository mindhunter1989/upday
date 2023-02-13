package upday.actions

import upday.domain.Article
import upday.domain.Articles
import upday.domain.IdGenerator
import upday.domain.TimeProvider
import java.time.Instant

class CreateArticle(
  private val articles: Articles,
  private val idGenerator: IdGenerator,
  private val timeProvider: TimeProvider,
) {

  operator fun invoke(request: Request) =
    createArticle(request)
      .also { newArticle ->
        articles.upsert(article = newArticle)
      }

  private fun createArticle(request: Request): Article =
    request.toArticle(idGenerator.id(), timeProvider.now())

  private fun Request.toArticle(id: String, at: Instant): Article =
    Article(
      id = id,
      header = this.header,
      shortDescription = this.shortDescription,
      text = this.text,
      publishDate = at,
      authors = this.authors,
      keywords = this.keywords
    )

  data class Request(
    val header: String,
    val shortDescription: String,
    val text: String,
    val authors: Set<String>,
    val keywords: Set<String>,
  )

}
