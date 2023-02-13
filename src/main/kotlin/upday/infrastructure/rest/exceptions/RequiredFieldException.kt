package upday.infrastructure.rest.exceptions

data class RequiredFieldException(val field: String) : RuntimeException("$field is required")
