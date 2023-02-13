package upday.security.domain.port.incoming

import upday.security.domain.port.incoming.`in`.CheckAuthorityRequest

interface CheckAuthorityPort {
  operator fun invoke(request: CheckAuthorityRequest)
}
