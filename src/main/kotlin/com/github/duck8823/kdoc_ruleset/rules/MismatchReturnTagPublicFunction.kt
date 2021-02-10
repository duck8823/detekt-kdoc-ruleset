package com.github.duck8823.kdoc_ruleset.rules

import com.github.duck8823.kdoc_ruleset.shouldBeDocumented
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * This rule will report any public function which mismatch @return block in documentation.
 *
 */
class MismatchReturnTagPublicFunction : Rule() {
    override val issue: Issue
        get() = Issue(
            id = this.javaClass.simpleName,
            severity = Severity.Maintainability,
            description = """
                | Public functions having return value require @return block in documentation.
            """.trimIndent(),
            debt = Debt.FIVE_MINS
        )

    override fun visitNamedFunction(function: KtNamedFunction) {
        if (!function.shouldBeDocumented()) return

        if (function.typeReference != null && (function.docComment == null || !function.docComment!!.text.contains("@return"))) {
            report(CodeSmell(
                issue,
                Entity.atName(function),
                "The function ${function.nameAsSafeName} is required @return block."
            ))
        }

        if (function.typeReference == null && (function.docComment != null && function.docComment!!.text.contains("@return"))) {
            report(CodeSmell(
                issue,
                Entity.atName(function),
                "The function ${function.nameAsSafeName} has redundant @return block."
            ))
        }
    }

}
