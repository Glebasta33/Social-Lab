package com.trusov.sociallab.presentation.fragment.answers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.entity.AnswerExtended
import com.trusov.sociallab.domain.use_case.answers.GetListOfAnsweredQuestionsUseCase
import com.trusov.sociallab.domain.use_case.survey.GetQuestionUseCase
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
        getQuestionUseCase()
        getAnswers()
    }

    private val _answers = MutableLiveData<List<AnswerExtended>>()
    val answers: LiveData<List<AnswerExtended>> = _answers

    private fun getAnswers() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = getListOfAnsweredQuestionsUseCase()
            withContext(Dispatchers.Main) {
                _answers.value = list
            }
        }

    }

}