import kotlincommon.test.shouldEqual
import org.junit.jupiter.api.Test

class RegexMatcherTests {
    @Test fun `some examples`() {
        "".matchesRegex("") shouldEqual true
        "a".matchesRegex("a") shouldEqual true
        "".matchesRegex("a") shouldEqual false

        "a".matchesRegex(".") shouldEqual true
        ".".matchesRegex("a") shouldEqual false

        "a".matchesRegex("a?") shouldEqual true
        "".matchesRegex("a?") shouldEqual true
        "b".matchesRegex("a?") shouldEqual false
        "b".matchesRegex(".?") shouldEqual true
    }
}

interface RegexMatcher : (String) -> Set<String>

class CharMatcher(private val char: Char) : RegexMatcher {
    override fun invoke(input: String) =
        if (input.startsWith(char)) setOf(input.drop(1))
        else emptySet()
}

object AnyCharMatcher : RegexMatcher {
    override fun invoke(input: String) =
        if (input.isNotEmpty()) setOf(input.drop(1))
        else emptySet()
}

class OptionalMatcher(private val matcher: RegexMatcher) : RegexMatcher {
    override fun invoke(input: String) =
        setOf(input) + matcher(input)
}


private fun String.matchesRegex(regex: String): Boolean {
    return regex
        .fold(emptyList<RegexMatcher>()) { matchers, it ->
            when (it) {
                '.'  -> matchers + AnyCharMatcher
                '?'  -> matchers.dropLast(1) + OptionalMatcher(matchers.last())
                else -> matchers + CharMatcher(it)
            }
        }
        .fold(setOf(this)) { inputs, nextMatcher ->
            inputs.flatMap(nextMatcher).toSet()
        }
        .any { input -> input == "" }
}
