package upday.infrastructure.rest.exceptions

data class RequiredParamException(val param: String) : RuntimeException("$param is required")
