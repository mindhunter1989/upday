package upday.domain

import java.time.Instant

interface Articles {
  fun findById(id: String): Article?
  fun findByAuthor(author: String): List<Article>
  fun findInPeriod(from: Instant, to: Instant): List<Article>
  fun findByKeyword(keyword: String): List<Article>

  fun upsert(article: Article): Article
  fun delete(id: String): String?
}
