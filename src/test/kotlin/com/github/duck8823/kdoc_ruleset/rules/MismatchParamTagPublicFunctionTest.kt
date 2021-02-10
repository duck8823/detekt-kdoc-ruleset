package com.github.duck8823.kdoc_ruleset.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MismatchParamTagPublicFunctionTest {

    @Test
    fun shouldReportWithoutDocument() {
        // given
        val sut = MismatchParamTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                fun hoge(foo: String, bar: String): Int {
                    return 1
                }
                
                // private fun is not reported
                private fun fuga() Int {
                    return 1
                }
            }
        """.trimIndent())

        // then
        assertThat(actual).hasSize(1)
    }

    @Test
    fun shouldReportWithMismatchedParamTag() {
        // given
        val sut = MismatchParamTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                /**
                 * hoge is a function.
                 *
                 */
                fun hoge(foo: String, bar: String): Int {
                    return 1
                }
                
                /**
                 * hoge is a function.
                 *
                 * @param bar It is bar.
                 * @param foo It is foo.
                 */
                fun hoge(foo: String, bar: String): Int {
                    return 1
                }                
            
                /**
                 * hoge is a function.
                 *
                 * @param bar It is a wrong name.
                 */
                fun hoge(foo: String): Int {
                    return 1
                }
                
                /**
                 * hoge is a function.
                 *
                 * private fun is not reported
                 */
                private fun hoge(foo: String. bar: String): Int {
                    return 1
                }
            }
        """.trimIndent())

        // then
        assertThat(actual).hasSize(3)
    }

    @Test
    fun shouldNotReportWithCorrectReturnTag() {
        // given
        val sut = MismatchParamTagPublicFunction()

        // when
        val actual = sut.lint("""
            class Hoge {
            
                /**
                 * hoge is a function.
                 *
                 * @param foo It is foo.
                 * @param bar It is bar.
                 * @return 1
                 */
                fun hoge(foo: String, bar: String): Int {
                    return 1
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
