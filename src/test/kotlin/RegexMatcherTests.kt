import kotlincommon.test.shouldEqual
import org.junit.jupiter.api.Test

class RegexMatcherTests {
    @Test fun `some examples`() {
        "".matchesRegex("") shouldEqual true
        "a".matchesRegex("a") shouldEqual true
        "".matchesRegex("a") shouldEqual false

        "a".matchesRegex(".") shouldEqual true
        ".".matchesRegex("a") shouldEqual false

//        "a".matchesRegex("a?") shouldEqual true
//        "".matchesRegex("a?") shouldEqual true
//        "b".matchesRegex("a?") shouldEqual false
    }
}

private fun String.matchesRegex(regex: String): Boolean {
    if (this.length != regex.length) return false
    return zip(regex)
        .all { it.second == '.' || it.first == it.second }
}
