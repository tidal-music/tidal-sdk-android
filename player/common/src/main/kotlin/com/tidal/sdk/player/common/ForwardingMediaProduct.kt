package com.tidal.sdk.player.common

import com.tidal.sdk.player.common.model.BaseMediaProduct

class ForwardingMediaProduct<T : BaseMediaProduct>(val delegate: T) : BaseMediaProduct by delegate
