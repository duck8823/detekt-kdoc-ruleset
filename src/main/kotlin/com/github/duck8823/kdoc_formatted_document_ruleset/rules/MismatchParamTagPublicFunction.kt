package com.github.duck8823.kdoc_formatted_document_ruleset.rules

import com.github.duck8823.kdoc_formatted_document_ruleset.shouldBeDocumented
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * This rule will report any public function which mismatch @param block in documentation.
 *
 */
class MismatchParamTagPublicFunction : Rule() {
    companion object {
        private val paramTagPattern = Regex("@param\\s(.+?)\\s.+")
    }

    override val issue: Issue
        get() = Issue(
            id = this.javaClass.simpleName,
            severity = Severity.Maintainability,
            description = """
                | Public functions having return value require @param block in documentation.
            """.trimIndent(),
            debt = Debt.FIVE_MINS
        )

    override fun visitNamedFunction(function: KtNamedFunction) {
        if (!function.shouldBeDocumented()) return

        val params = function.valueParameters.map { it.nameAsSafeName.toString() }

        if (params.isEmpty() && (function.docComment != null && !function.docComment!!.text.contains("@param"))) {
            report(CodeSmell(
                issue,
                Entity.atName(function),
                "The function ${function.nameAsSafeName} has redundant @param block."
            ))
        }

        if (params != function.docComment.line.mapNotNull(paramTagPattern::find).map { it.groupValues[1] }) {
            report(CodeSmell(
                issue,
                Entity.atName(function),
                "The function ${function.nameAsSafeName} is required correct @param blocks."
            ))
        }

    }
}

private val PsiElement?.line: List<String>
    get() = this?.text?.split("\n") ?: listOf()
