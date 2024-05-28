package com.tidal.sdk.eventproducer.utils

class CoroutineScopeCanceledException :
    Exception("Coroutine scope used for sending events has been canceled")
