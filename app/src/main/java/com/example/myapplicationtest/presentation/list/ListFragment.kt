package com.example.myapplicationtest.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.databinding.FragmentListBinding
import com.example.myapplicationtest.presentation.AdapterRv
import com.example.myapplicationtest.presentation.TappticState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    val listViewModel: ListViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = AdapterRv(listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listViewModel.getData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            listViewModel.sharedFlow.collect{
                println("!!!!!!!!!!!"+it )
                when(it){
                    is TappticState.Loading -> {
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                     is TappticState.Success ->{
                         binding.recyclerView.isVisible = true
                         binding.progressBar.isVisible = false
                     adapter.updateItems(it.list)

                     }
                    is TappticState.Error ->{
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = false
                        Toast.makeText(binding.root.context, it.error,Toast.LENGTH_LONG).show()
                    }
                }


            }
        }

    }
}