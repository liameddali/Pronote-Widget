package com.papillon.widget

import java.time.LocalDateTime

/**
 * Modèles de données natifs Papillon
 */
data class Lesson(
    val id: String,
    val subject: String,
    val teacher: String,
    val room: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val color: Int, // Color as ARGB
    val isCancelled: Boolean = false
)

data class UserProfile(
    val name: String,
    val school: String,
    val className: String,
    val avatarUrl: String? = null
)

data class Grade(
    val id: String,
    val subject: String,
    val value: Float,
    val outOf: Float,
    val coefficient: Float,
    val date: LocalDateTime,
    val average: Float? = null
)

data class Task(
    val id: String,
    val subject: String,
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime,
    val isCompleted: Boolean
)
