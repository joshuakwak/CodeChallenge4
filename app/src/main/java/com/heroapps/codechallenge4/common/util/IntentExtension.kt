package com.heroapps.codechallenge4.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.createIntent(): Intent = Intent(this, T::class.java)