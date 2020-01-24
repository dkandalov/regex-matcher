import kotlincommon.test.shouldEqual
import org.junit.jupiter.api.Test

class RegexMatcherTests {
    @Test fun `some examples`() {
        "".matchesRegex("") shouldEqual true
        "a".matchesRegex("a") shouldEqual true
    }
}

private fun String.matchesRegex(regex: String): Boolean {
    return true
}
