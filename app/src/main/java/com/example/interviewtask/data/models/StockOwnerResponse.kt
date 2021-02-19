package com.example.interviewtask.data.models

data class StockOwnerResponse(
        val `items`: List<StockOwnerItem>,
        val has_more: Boolean,
        val backoff: Int,
        val quota_max: Int,
        val quota_remaining: Int
)