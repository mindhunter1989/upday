package upday.doubles

import upday.domain.IdGenerator

class IdGeneratorFake(
  private val idsToGenerate: MutableList<String> = mutableListOf(),
) : IdGenerator {

  override fun id(): String {
    return idsToGenerate.removeLast()
  }

  fun nextIdToGenerate(id: String) {
    idsToGenerate.add(id)
  }

}
