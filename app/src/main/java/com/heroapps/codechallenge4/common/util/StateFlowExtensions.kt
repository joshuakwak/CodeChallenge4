package com.heroapps.codechallenge4.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> StateFlow<T>.collectLatestData(scope: CoroutineScope, action: (T) -> Unit): Job {
    return scope.launch {
        this@collectLatestData.collectLatest { data -> action(data) }
    }
}