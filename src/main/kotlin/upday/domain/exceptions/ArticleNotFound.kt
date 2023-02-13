package upday.domain.exceptions

class ArticleNotFound(id: String) : RuntimeException("Article with id $id not exists")
