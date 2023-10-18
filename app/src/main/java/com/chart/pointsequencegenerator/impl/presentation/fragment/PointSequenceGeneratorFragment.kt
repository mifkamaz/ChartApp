package com.chart.pointsequencegenerator.impl.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chart.databinding.FragmnentPointSequenceGeneratorBinding
import com.chart.pointsequencegenerator.impl.presentation.model.mvi.PointSequenceGenerator
import com.chart.pointsequencegenerator.impl.presentation.viewmodel.PointSequenceGeneratorViewModel
import com.chart.pointsequencegenerator.impl.presentation.viewmodelfactory.PointSequenceGeneratorViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PointSequenceGeneratorFragment : Fragment() {

    private val binding by lazy {
        FragmnentPointSequenceGeneratorBinding.inflate(layoutInflater)
    }

    private val viewModel: PointSequenceGeneratorViewModel
            by viewModels { PointSequenceGeneratorViewModelFactory(findNavController()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.onEach { renderState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.uiSideEffect.onEach { renderEffect(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        setupListeners()
    }

    private fun renderEffect(it: PointSequenceGenerator.Effect) {
        val message = when (it) {
            is PointSequenceGenerator.Effect.BusinessError -> it.message
            is PointSequenceGenerator.Effect.InputError -> "Введено неверное количество"
            is PointSequenceGenerator.Effect.NetworkError -> "Проблемы с подключением к интернету"
        }
        val retryAction = when (it) {
            is PointSequenceGenerator.Effect.BusinessError -> it.retryAction
            is PointSequenceGenerator.Effect.InputError -> null
            is PointSequenceGenerator.Effect.NetworkError -> it.retryAction
        }
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_SHORT)
            .apply {
                if (retryAction != null) {
                    setAction("Повторить") {
                        viewModel.onAction(retryAction)
                    }
                }
            }
            .show()
    }

    private fun setupListeners() = binding.run {
        //TODO: Вынести в функцию расширению
        countEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submit()
            }
            return@setOnEditorActionListener false
        }
        submitButton.setOnClickListener {
            //TODO: Костыль что-бы скрыть клавиу и одновременно выполнить действие
            countEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
    }

    private fun submit() {
        viewModel.onAction(
            PointSequenceGenerator.Action.Submit(
                binding.countEditText.text.toString().toIntOrNull() ?: 0
            )
        )
    }

    private fun renderState(state: PointSequenceGenerator.State) = binding.run {
        progressBar.isVisible = state.loading
        submitButton.isEnabled = !state.loading
    }
}