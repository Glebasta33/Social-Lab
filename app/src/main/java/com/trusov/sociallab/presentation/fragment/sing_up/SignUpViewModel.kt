package com.trusov.sociallab.presentation.fragment.sing_up

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.domain.use_case.enter.SignUpUseCase
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val singUpUseCase: SignUpUseCase
) : ViewModel() {
}