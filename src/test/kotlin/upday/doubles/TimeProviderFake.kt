package upday.doubles

import upday.domain.TimeProvider
import java.time.Instant

class TimeProviderFake(
  private val fixedNow: Instant = Instant.now(),
) : TimeProvider {

  override fun now(): Instant = fixedNow

}
