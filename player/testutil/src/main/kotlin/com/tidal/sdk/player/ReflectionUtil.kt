package com.tidal.sdk.player

import java.lang.reflect.Field
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KCallable
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

fun <T> Any.reflectionGetInstanceMemberProperty(propertyName: CharSequence): T? =
    reflectionGetMemberProperty(propertyName, this)

fun <T> Any.reflectionSetInstanceMemberProperty(propertyName: CharSequence, newValue: T?) =
    reflectionSetMemberProperty(propertyName, newValue, this)

fun <T> Any.reflectionSetInstanceFinalField(propertyName: CharSequence, newValue: T?) =
    reflectionSetFinalField(propertyName, newValue, this)

fun <T> CharSequence.reflectionGetTopLevelProperty(propertyName: String): T? =
    Class.forName("${this}Kt").getDeclaredField(propertyName).run {
        val wasAccessible = canAccess(null)
        isAccessible = true
        @Suppress("UNCHECKED_CAST") val value = get(null) as T?
        isAccessible = wasAccessible
        value
    }

fun <T> Any.reflectionSetDelegatedInstanceMemberProperty(propertyName: CharSequence, newValue: T?) =
    reflectionSetDelegatedMemberProperty(propertyName, newValue, this)

private fun <T> Any.reflectionGetMemberProperty(
    propertyName: CharSequence,
    receiver: Any? = null,
): T? =
    memberProperty<KProperty<T?>>(propertyName).getter.runAllowingAccess {
        if (receiver != null) call(receiver) else call()
    }

private fun <T> Any.reflectionSetMemberProperty(
    propertyName: CharSequence,
    newValue: T?,
    receiver: Any? = null,
) =
    memberProperty<KMutableProperty<T?>>(propertyName).setter.runAllowingAccess {
        if (receiver != null) call(receiver, newValue) else call(newValue)
    } ?: Unit

private fun <T> Any.reflectionSetFinalField(
    propertyName: CharSequence,
    newValue: T?,
    receiver: Any,
) = memberField(propertyName).runAllowingAccess { set(receiver, newValue) }

@Suppress("UNCHECKED_CAST") // We want the exception if the type is not correct
private fun <T, V> Any.reflectionSetDelegatedMemberProperty(
    propertyName: CharSequence,
    newValue: V?,
    receiver: T,
) =
    memberProperty<KMutableProperty1<T, V?>>(propertyName).runAllowingAccess {
        (getDelegate(receiver) as ReadWriteProperty<T, V?>).setValue(receiver, this, newValue)
    }

private fun <T, V : KCallable<T?>, R> V.runAllowingAccess(block: V.() -> R?) = run {
    val wasAccessible = isAccessible
    isAccessible = true
    val ret = block(this)
    isAccessible = wasAccessible
    ret
}

private fun Field.runAllowingAccess(block: Field.() -> Unit) = run {
    val wasAccessible = isAccessible
    isAccessible = true
    block(this)
    isAccessible = wasAccessible
}

@Suppress("UNCHECKED_CAST") // We want the exception if the type is not correct
private inline fun <reified T : KCallable<*>> Any.memberProperty(propertyName: CharSequence): T =
    (this::class.superclasses + this::class)
        .flatMap { it.memberProperties }
        .find { it.name.contentEquals(propertyName) && it is T } as T

private fun Any.memberField(propertyName: CharSequence): Field =
    this::class.java.getField(propertyName.toString())
