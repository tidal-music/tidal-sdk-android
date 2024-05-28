package com.tidal.sdk.player.repeatableflakytest

import java.util.logging.Level
import java.util.logging.Logger
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Used alongside [RepeatableFlakyTest] to denote that a test method may be retried until it
 * succeeds.
 *
 * @see RepeatableFlakyTest
 */
class RepeatableFlakyTestRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        if (description.run { isTest && getAnnotation(RepeatableFlakyTest::class.java) != null }) {
            return CustomStatement(base, description)
        }
        return base
    }

    private class CustomStatement(
        private val base: Statement,
        private val description: Description,
    ) : Statement() {

        private val logger = Logger.getLogger(RepeatableFlakyTestRule::class.java.name)

        @Suppress("MaxLineLength", "TooGenericExceptionCaught", "UseCheckOrError")
        override fun evaluate() {
            val maxRuns = description.getAnnotation(RepeatableFlakyTest::class.java)!!.maxRuns
            val throwables = mutableListOf<Throwable>()
            repeat(maxRuns) {
                val oneIndexedRun = it + 1
                logger.warning(
                    "${description.displayName}: evaluating repeatable flaky test (run $oneIndexedRun/$maxRuns)", // ktlint-disable max-line-length
                )
                try {
                    base.evaluate()
                    logger.fine("${description.displayName}: passed (run $oneIndexedRun/$maxRuns)")
                    return
                } catch (throwable: Throwable) {
                    throwables.add(throwable)
                    logger.log(
                        Level.WARNING,
                        "${description.displayName}: failed (run $oneIndexedRun/$maxRuns)",
                        throwable,
                    )
                    if (oneIndexedRun == maxRuns) {
                        logger.severe(
                            "${description.displayName}: giving up after $oneIndexedRun runs",
                        )
                        throw IllegalStateException(
                            "Maxed out attempts. Failures were:\n${
                                throwables
                                    .foldRightIndexed(StringBuilder("")) { index, t, acc ->
                                        acc.appendLine(
                                            "Run ${index + 1}/${throwables.size}: ${t.message}",
                                        )
                                        acc
                                    }
                            }",
                        )
                    }
                }
            }
        }
    }
}
