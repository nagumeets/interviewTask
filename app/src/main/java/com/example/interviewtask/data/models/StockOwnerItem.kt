package com.example.interviewtask.data.models

data class StockOwnerItem(
        val is_accepted: Boolean,
        val score: Int,
        val last_activity_date: Int,
        val creation_date:Int,
        val answer_id:Int,
        val question_id:Int,
        val owner: StockOwner,
        val content_license: String,
)