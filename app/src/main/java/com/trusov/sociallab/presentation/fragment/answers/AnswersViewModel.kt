package com.trusov.sociallab.presentation.fragment.answers

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.use_case.survey.GetQuestionUseCase
import javax.inject.Inject

class AnswersViewModel @Inject constructor(
    private val getQuestionUseCase: GetQuestionUseCase
) : ViewModel() {

    fun loadQuestion() {
         getQuestionUseCase()
    }

}