package upday.domain

import java.time.Instant

interface TimeProvider {
  fun now(): Instant
}
