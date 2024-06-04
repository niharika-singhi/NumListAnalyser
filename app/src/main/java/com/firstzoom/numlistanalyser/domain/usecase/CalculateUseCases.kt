package com.firstzoom.numlistanalyser.domain.usecase

import javax.inject.Inject

class CalculateUseCases @Inject constructor() {
    fun getIntersection(lists: List<List<Int>>): List<Int> {
        return lists.reduce { acc, list -> acc.intersect(list).toList() }
    }

    fun getUnion(lists: List<List<Int>>): List<Int> {
        return lists.flatten().toSet().toList()
    }

    fun getHighestNumber(lists: List<List<Int>>): Int? {
        return lists.flatten().maxOrNull()
    }
}