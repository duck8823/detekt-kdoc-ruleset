package com.github.duck8823.kdoc_ruleset

import com.github.duck8823.kdoc_ruleset.rules.MismatchParamTagPublicFunction
import com.github.duck8823.kdoc_ruleset.rules.MismatchReturnTagPublicFunction
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import io.gitlab.arturbosch.detekt.rules.isPublicNotOverridden
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.containingClass
import org.jetbrains.kotlin.psi.psiUtil.isPublic

/**
 * KDoc-formatted document ruleset provider
 *
 */
class KDocFormattedDocumentRuleSet : RuleSetProvider {
    override val ruleSetId: String
        get() = "kdoc-formatted-document"

    override fun instance(config: Config): RuleSet = RuleSet(
        id = this.ruleSetId,
        rules = listOf(
            MismatchReturnTagPublicFunction(),
            MismatchParamTagPublicFunction()
        )
    )
}

internal fun KtNamedFunction.shouldBeDocumented() =
    isLocal || ((isTopLevel || containingClass()!!.isPublic == true) && isPublicNotOverridden())
