package com.tidal.sdk.common

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * @return a logger with the name of the class that calls this extension function Loggers are stored
 *   in a map and reused
 */
val Any.logger: KLogger
    get() = getLoggerByName(this.javaClass.name)

private val LOGGER_MAP = hashMapOf<String, KLogger>()

fun getLoggerByName(name: String): KLogger {
    return LOGGER_MAP.getOrPut(name) { KotlinLogging.logger(name) }
}

/** Convenience function to call KLogger.debug() */
fun KLogger.d(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { debug(it, message) } ?: debug(message)
}

/** Convenience function to call KLogger.info() */
fun KLogger.i(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { info(it, message) } ?: info(message)
}

/** Convenience function to call KLogger.warn() */
fun KLogger.w(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { warn(it, message) } ?: warn(message)
}

/** Convenience function to call KLogger.error() */
fun KLogger.e(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { error(it, message) } ?: error(message)
}

/** Convenience function to call KLogger.verbose() */
fun KLogger.v(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { trace(it, message) } ?: trace(message)
}

/**
 * verbose is generally considered a synonym of trace, so to stay in line with typical logging
 * frameworks on Android, we add this convenience function
 */
fun KLogger.verbose(throwable: Throwable? = null, message: () -> Any?) {
    throwable?.let { trace(it, message) } ?: trace(message)
}
