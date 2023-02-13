package upday.infrastructure.persistence

import upday.domain.Article
import upday.domain.Articles
import java.time.Instant

class InMemoryArticles(
  private val articles: MutableMap<String, Article> = mutableMapOf(),
) : Articles {

  override fun findById(id: String): Article? = articles[id]

  override fun findByAuthor(author: String): List<Article> =
    articles.values.filter { it.authors.contains(author) }

  override fun findInPeriod(from: Instant, to: Instant): List<Article> =
    articles.values.filter { from.isBefore(it.publishDate) && to.isAfter(it.publishDate) }

  override fun findByKeyword(keyword: String): List<Article> =
    articles.values.filter { it.keywords.contains(keyword) }

  override fun upsert(article: Article): Article = article.also { articles[article.id] = article }

  override fun delete(id: String) = articles.remove(id)?.id

}
