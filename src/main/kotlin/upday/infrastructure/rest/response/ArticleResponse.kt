package upday.infrastructure.rest.response

import kotlinx.serialization.Serializable
import upday.domain.Article

@Serializable
data class ArticleResponse(
  val id: String,
  val header: String,
  val shortDescription: String,
  val text: String,
  val publishDate: String,
  val authors: Set<String>,
  val keywords: Set<String>,
) {

  companion object {
    fun from(article: Article) =
      ArticleResponse(
        id = article.id,
        header = article.header,
        shortDescription = article.shortDescription,
        text = article.text,
        publishDate = article.publishDate.toString(),
        authors = article.authors,
        keywords = article.keywords,
      )
  }

}
