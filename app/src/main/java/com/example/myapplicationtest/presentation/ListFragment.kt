package com.example.myapplicationtest.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationtest.R
import com.example.myapplicationtest.data.api.TappticApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class ListFragment : Fragment() {
    val listViewModel: ListViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.getData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            listViewModel.sharedFlow.collect{
                println("!!!!!!!!!!!"+it )
                when(it){
                    is TappticState.Loading -> {}
                     is  TappticState.Success ->{
                         println("!!!!!!!!!!!"+it )
                     }
                    is TappticState.Error ->{}
                }


            }
        }

    }
}