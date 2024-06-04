package com.firstzoom.numlistanalyser.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.firstzoom.numlistanalyser.domain.usecase.CalculateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NumberListAnalyserViewModel @Inject constructor(private val calculateUseCases: CalculateUseCases) : ViewModel() {
    var intersectionResult = mutableStateOf("")
    var unionResult = mutableStateOf("")
    var highestNumberResult = mutableStateOf("")

    fun calculateResults(lists: List<List<Int>>) {
        val intersection = calculateUseCases.getIntersection(lists)
        val union = calculateUseCases.getUnion(lists)
        val highestNumber = calculateUseCases.getHighestNumber(lists)
        println("Debug $highestNumber $union")
        intersectionResult.value = "Intersection: ${intersection.joinToString(", ")}"
        unionResult.value = "Union: ${union.joinToString(", ")}"
        highestNumberResult.value = "Highest Number: ${highestNumber ?: "" }"
    }
}