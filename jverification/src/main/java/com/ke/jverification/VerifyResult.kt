package com.ke.jverification

data class VerifyResult(
    val result: Boolean,
    val content: String,
    val operator: String?
)