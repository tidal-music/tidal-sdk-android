package com.tidal.sdk.flo.core.internal

internal class InvokeFunction1Runnable<T>(private val function: (T) -> Unit, private val param: T) :
    Runnable {

    override fun run() = function(param)

    class Factory<T>(private val function: (T) -> Unit) : (T) -> InvokeFunction1Runnable<T> {

        override operator fun invoke(param: T) = InvokeFunction1Runnable(function, param)
    }
}
