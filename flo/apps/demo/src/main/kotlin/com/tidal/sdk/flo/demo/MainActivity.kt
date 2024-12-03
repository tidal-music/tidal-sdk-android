package com.tidal.sdk.flo.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

internal class MainActivity : Activity() {

    @Suppress("LongMethod")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "API: ${BuildConfig.FLAVOR}"
        val topicView = EditText(this).apply {
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        }
        val tailView = EditText(this).apply {
            gravity = Gravity.CENTER
            inputType = InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        }
        val subscriptionView = TextView(this).apply {
            gravity = Gravity.CENTER
        }
        val subscriptionManager = SelectedApiSubscriptionManager(
            (applicationContext as MainApplication).demoFloClient,
            "Demo FloClient",
        )
        val updateSubscriptionView = {
            subscriptionView.text = subscriptionManager.subscribedTopics
                .joinToString(separator = "\n", postfix = "\n") {
                    "topic=$it (${subscriptionManager.name})"
                }
        }
        setContentView(
            LinearLayout(this).apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                orientation = LinearLayout.VERTICAL
                addView(
                    LinearLayout(this@MainActivity).apply {
                        gravity = Gravity.CENTER
                        addView(
                            Button(this@MainActivity).apply {
                                text = "Subscribe"
                                setOnClickListener {
                                    val topic = topicView.text.toString()
                                    subscriptionManager.unsubscribe(topic)
                                    subscriptionManager
                                        .subscribe(
                                            topic,
                                            { msg: String ->
                                                Log.d(
                                                    "HOLA",
                                                    "topic $topic - MSG: $msg",
                                                )
                                            },
                                            { e: Throwable ->
                                                Log.d(
                                                    "HOLA",
                                                    "topic $topic - ERROR",
                                                    e,
                                                )
                                            },
                                            tailView.text.toString().ifBlank { "0" }.toInt(),
                                        )
                                    updateSubscriptionView()
                                }
                            },
                        )
                        addView(
                            Button(this@MainActivity).apply {
                                text = "Unsubscribe"
                                setOnClickListener {
                                    val topic = topicView.text.toString()
                                    subscriptionManager.unsubscribe(topic)
                                    updateSubscriptionView()
                                }
                            },
                        )
                    },
                )
                addView(
                    LinearLayout(this@MainActivity).apply {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.HORIZONTAL
                        addView(
                            TextView(this@MainActivity).apply {
                                text = "Topic:"
                            },
                        )
                        addView(topicView)
                    },
                )
                addView(
                    LinearLayout(this@MainActivity).apply {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.HORIZONTAL
                        addView(
                            TextView(this@MainActivity).apply {
                                text = "Tail (for subscribing only):"
                            },
                        )
                        addView(tailView)
                    },
                )
                addView(subscriptionView).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                    )
                }
            },
        )
    }
}
