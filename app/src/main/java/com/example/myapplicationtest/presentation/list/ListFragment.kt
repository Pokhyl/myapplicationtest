package com.example.myapplicationtest.presentation.list

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.R
import com.example.myapplicationtest.databinding.FragmentListBinding
import com.example.myapplicationtest.domain.TappticEntity
import com.example.myapplicationtest.presentation.AdapterRv
import com.example.myapplicationtest.presentation.SharedViewModel
import com.example.myapplicationtest.presentation.TappticState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

@AndroidEntryPoint

class ListFragment : Fragment(), AdapterOnClickListener {
    private lateinit var binding: FragmentListBinding

    val listViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = AdapterRv(listOf(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (isNetworkConnected(requireContext())){
            listViewModel.getData()
        }else{
            //binding.error.isVisible = true
        //    binding.buttonrepit.isVisible = true
            listViewModel.getData()
            binding.error1.setOnClickListener {
                listViewModel.getData()
            }
        }



        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            listViewModel.sharedFlowList
                .collect{
                println("!!!!!!!!!!!"+it )
                when(it){
                    is TappticState.Loading -> {
                        binding.error.isVisible = false
                        binding.error1.isVisible = false
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = true

                    }
                     is TappticState.Success ->{
                         binding.error.isVisible = false
                         binding.error1.isVisible = false
                         binding.recyclerView.isVisible = true
                         binding.progressBar.isVisible = false

                     adapter.updateItems(it.list)

                     }
                    is TappticState.Error ->{
                        binding.error.isVisible = true
                        binding.error1.isVisible = true
                        binding.recyclerView.isVisible = false
                        binding.progressBar.isVisible = false
                        Toast.makeText(binding.root.context, it.error,Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }


            }
        }

    }

    override fun onClick(tappticEntity: TappticEntity) {
        listViewModel.getItem(tappticEntity.name)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){

//            val action = ListFragmentDirections.actionListFragmentToItemFragment2()
            findNavController().navigate(R.id.itemFragment)
        }
    }
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
interface AdapterOnClickListener{
    fun onClick(tappticEntity: TappticEntity)
}
