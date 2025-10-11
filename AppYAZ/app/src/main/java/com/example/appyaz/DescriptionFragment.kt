package com.example.appyaz

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.appyaz.databinding.FragmentDescriptionBinding
import kotlinx.coroutines.launch

class DescriptionFragment : Fragment(), MenuProvider {
    private var _binding: FragmentDescriptionBinding? = null
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
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupUI()
        updateTexts()
        loadModelData()
    }

    override fun onResume() {
        super.onResume()
        updateTexts()
        loadModelData()
    }

    private fun setupUI() {
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditActivity::class.java).apply {
                putExtra("modelId", modelId)
            }
            startActivity(intent)
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun updateTexts() {
        with(binding) {
            ModelNameTitleTextView.text = getString(R.string.model_description_name)
            modelCostTitleTextView.text = getString(R.string.model_description_cost)
            modelDescriptionTitleTextView.text = getString(R.string.model_description_description)
            buttonEdit.text = getString(R.string.action_edit)
            buttonBack.text = getString(R.string.action_back)
        }
    }

    private fun loadModelData() {
        lifecycleScope.launch {
            val model = viewModel.getModelById(modelId)
            model?.let { updateUI(it) }
        }
    }

    private fun updateUI(item: ItemModelUAZ) {
        with(binding) {
            modelNameTextView.text = item.name
            modelCostTextView.text = item.cost
            modelDescriptionTextView.text = item.description
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.model_description_menu, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.add)?.isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.edit -> {
                val intent = Intent(requireContext(), EditActivity::class.java).apply {
                    putExtra("modelId", modelId)
                }
                startActivity(intent)
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