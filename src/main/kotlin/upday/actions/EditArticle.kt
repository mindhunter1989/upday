package upday.actions

import upday.domain.Article
import upday.domain.Articles
import upday.domain.exceptions.ArticleNotFound
import java.time.Instant

class EditArticle(
  private val articles: Articles,
) {

  operator fun invoke(request: Request) =
    articles.findById(request.id)
      ?.let { dbArticle ->
        editArticle(dbArticle, request)
          .also { updatedArticle ->
            articles.upsert(article = updatedArticle)
          }
      } ?: throw ArticleNotFound(request.id)

  private fun editArticle(dbArticle: Article, request: Request): Article =
    request.toArticle(dbArticle.id, dbArticle.publishDate)

  private fun Request.toArticle(id: String, publishDate: Instant): Article =
    Article(
      id = id,
      header = this.header,
      shortDescription = this.shortDescription,
      text = this.text,
      publishDate = publishDate,
      authors = this.authors,
      keywords = this.keywords
    )

  data class Request(
    val id: String,
    val header: String,
    val shortDescription: String,
    val text: String,
    val authors: Set<String>,
    val keywords: Set<String>,
  )

}
