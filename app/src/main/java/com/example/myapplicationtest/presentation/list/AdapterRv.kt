package com.example.myapplicationtest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplicationtest.databinding.ItemBinding
import com.example.myapplicationtest.domain.TappticEntity
import com.example.myapplicationtest.presentation.list.ListFragmentDirections
import com.squareup.picasso.Picasso

class AdapterRv(var list: List<TappticEntity>, var navController: NavController): RecyclerView.Adapter<AdapterRv.TappticViewHolder>() {
    fun updateItems(newList: List<TappticEntity>) {
        val diffCallback: TappticAntityDiffCallback = TappticAntityDiffCallback(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TappticViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TappticViewHolder(binding, navController)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TappticViewHolder, position: Int) {
        val tappticEntity: TappticEntity = list[position]
        holder.bind(tappticEntity)
    }


    class TappticViewHolder(private val binding: ItemBinding,val navController: NavController) : ViewHolder(binding.root) {
        fun bind(tappticEntity: TappticEntity) {
            binding.textView.text = tappticEntity.name
            Picasso.get().load(tappticEntity.image).into(binding.imageView)
            binding.conLayout.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToItemFragment2(tappticEntity)
                navController.navigate(action)

            }
        }

    }
}
class TappticAntityDiffCallback(val oldList: List<TappticEntity>, val newList: List<TappticEntity> ): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
