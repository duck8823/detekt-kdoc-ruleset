package com.github.duck8823.kdoc_ruleset.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MismatchReturnTagPublicFunctionTest {

    @Test
    fun shouldReportWithoutDocument() {
        // given
        val sut = MismatchReturnTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                fun hoge(): Int {
                    return 1
                }
                
                // private fun is not reported
                private fun fuga(): Int {
                    return 1
                }
            }
        """.trimIndent())

        // then
        assertThat(actual).hasSize(1)
    }

    @Test
    fun shouldReportWithoutReturnTag() {
        // given
        val sut = MismatchReturnTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                /**
                 * hoge is a function.
                 *
                 */
                fun hoge(): Int {
                    return 1
                }
                
                /**
                 * hoge is a function.
                 *
                 * private fun is not reported
                 */
                private fun hoge(): Int {
                    return 1
                }
            }
                        
            interface Fuga {
                fun fuga(): String
            }
        """.trimIndent())

        // then
        assertThat(actual).hasSize(2)
    }

    @Test
    fun shouldReportWithRedundantReturnTag() {
        // given
        val sut = MismatchReturnTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                /**
                 * hoge is a function.
                 *
                 * @return Nothing
                 */
                fun hoge() {
                    // nothing to do.
                }
                
                /**
                 * hoge is a function.
                 *
                 * @return Nothing
                 */
                private fun hoge() {
                    // nothing to do
                }
            }

            interface Fuga {
                /**
                 * fuga is a function.
                 * 
                 * @return Nothing
                 */
                fun fuga()
            }
        """.trimIndent())

        // then
        assertThat(actual).hasSize(2)
    }

    @Test
    fun shouldNotReportWithCorrectReturnTag() {
        // given
        val sut = MismatchReturnTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                /**
                 * hoge is a function.
                 *
                 * @return 1
                 */
                fun hoge(): Int {
                    return 1
                }
               
                /**
                 * hoge is a function
                 *
                 * @param foo It is foo.
                 */
                fun hoge(foo: String) {
                    // nothing to do.
                }
            }

            interface Fuga {
                /**
                 * fuga is a function.
                 * 
                 * @return Empty String
                 */
                fun fuga(): String
            }
        """.trimIndent())

        // then
        assertThat(actual).isEmpty()
    }
}
