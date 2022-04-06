package com.trusov.sociallab.feature_survey.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended
import com.trusov.sociallab.feature_survey.domain.use_case.GetListOfAnsweredQuestionsUseCase
import com.trusov.sociallab.feature_survey.domain.use_case.GetQuestionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnswersViewModel @Inject constructor(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val getListOfAnsweredQuestionsUseCase: GetListOfAnsweredQuestionsUseCase
) : ViewModel() {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getQuestionUseCase()
        }
        getAnswers()
    }

    private val _answers = MutableLiveData<List<AnswerExtended>>()
    val answers: LiveData<List<AnswerExtended>> = _answers

    private fun getAnswers() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = getListOfAnsweredQuestionsUseCase()
            val sortedList = list
                .sortedWith(compareBy({it.createdDate}, {it.createdTime})).reversed()
            withContext(Dispatchers.Main) {
                _answers.value = sortedList
            }
        }

    }

}