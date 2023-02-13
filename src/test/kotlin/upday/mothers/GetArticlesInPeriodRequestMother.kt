package upday.mothers

import upday.actions.GetArticlesInPeriod

object GetArticlesInPeriodRequestMother {

  fun simpleAllFebruary() =
    GetArticlesInPeriod.Request(
      from = InstantMother.february_1st_2023(),
      to = InstantMother.february_28th_2023(),
    )

  fun firstFiveDaysOfFebruary() =
    GetArticlesInPeriod.Request(
      from = InstantMother.february_1st_2023(),
      to = InstantMother.february_5th_2023(),
    )

}
