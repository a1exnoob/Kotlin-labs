package com.example.appyaz

import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.appyaz.databinding.FragmentEditBinding
import kotlinx.coroutines.launch

class EditFragment : Fragment(), MenuProvider {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var modelId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("modelId")?.let {
            modelId = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupUI()
        updateTexts()
        loadModelData()
        showKeyboard()
    }

    override fun onResume() {
        super.onResume()
        updateTexts()
    }

    private fun setupUI() {
        binding.buttonSave.setOnClickListener { saveModel() }
        binding.buttonBackEdit.setOnClickListener { requireActivity().finish() }
    }

    private fun updateTexts() {
        with(binding) {
            modelNameTitleTextView.text = getString(R.string.model_edit_name)
            modelCostTitleTextView.text = getString(R.string.model_edit_cost)
            modelDescriptionTitleTextView.text = getString(R.string.model_edit_description)
            modelNameEditText.hint = getString(R.string.model_edit_hint_name)
            modelCostEditText.hint = getString(R.string.model_edit_hint_cost)
            modelDescriptionEditTextMultiLine.hint = getString(R.string.model_edit_hint_description)
            buttonSave.text = getString(R.string.action_save)
            buttonBackEdit.text = getString(R.string.action_back)
        }
    }

    private fun loadModelData() {
        if (modelId != -1) {
            lifecycleScope.launch {
                val model = viewModel.getModelById(modelId)
                model?.let {
                    with(binding) {
                        modelNameEditText.setText(it.name)
                        modelCostEditText.setText(it.cost)
                        modelDescriptionEditTextMultiLine.setText(it.description)
                    }
                }
            }
        }
    }

    private fun showKeyboard() {
        binding.modelNameEditText.requestFocus()
        binding.modelNameEditText.post {
            val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(binding.modelNameEditText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun saveModel() {
        val newModel = ItemModelUAZ(
            id = if (modelId != -1) modelId else 0,
            name = binding.modelNameEditText.text.toString(),
            cost = binding.modelCostEditText.text.toString(),
            description = binding.modelDescriptionEditTextMultiLine.text.toString()
        )

        if (modelId != -1) {
            viewModel.editModel(newModel)
        } else {
            viewModel.createModel(newModel)
        }

        Toast.makeText(requireContext(), getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
        requireActivity().finish()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.model_edit_menu, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.edit)?.isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.save -> {
                saveModel()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}