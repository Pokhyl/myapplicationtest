package com.example.myapplicationtest.presentation.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationtest.R
import com.example.myapplicationtest.databinding.FragmentItemBinding
import com.example.myapplicationtest.domain.TappticEntity
import com.example.myapplicationtest.presentation.TappticState
import com.example.myapplicationtest.presentation.list.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFragment : Fragment() {
    lateinit var binding: FragmentItemBinding
    val itemFragmentViewModel: ItemFragmentViewModel by viewModels<ItemFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
val args = ItemFragmentArgs.fromBundle(requireArguments())

        var tappticEntity: TappticEntity = args.tappticEntity
itemFragmentViewModel.getItem(tappticEntity.name)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            itemFragmentViewModel.sharedFlow.collect{
                println("!!!!!!!!!!!"+it )
                when(it){
                    is TappticState.Loading -> {
//                        binding.recyclerView.isVisible = false
//                        binding.progressBar.isVisible = true
                    }
                    is TappticState.SuccessItem ->{
//                        binding.recyclerView.isVisible = true
//                        binding.progressBar.isVisible = false
//                        adapter.updateItems(it.list)
                        binding.textView2.text = it.tappticEntity.name

                    }
                    is TappticState.Error ->{
//                        binding.recyclerView.isVisible = false
//                        binding.progressBar.isVisible = false
                        Toast.makeText(binding.root.context, it.error, Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }


            }
        }
    }
}