package com.gotov.getmeapp

data class Plan(val Title: String, val Progress: Int, val Skills: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Plan

        if (Title != other.Title) return false
        if (Progress != other.Progress) return false
        if (!Skills.contentEquals(other.Skills)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Title.hashCode()
        result = 31 * result + Progress.hashCode()
        result = 31 * result + Skills.contentHashCode()
        return result
    }
}
