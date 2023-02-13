package upday.domain

import java.time.Instant

data class Article(
  val id: String,
  val header: String,
  val shortDescription: String,
  val text: String,
  val publishDate: Instant,
  val authors: Set<String>,
  val keywords: Set<String>,
) {

  init {
    check(id.isNotEmpty()) { "Article id should not be empty" }
  }

}
