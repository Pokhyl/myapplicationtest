package com.example.myapplicationtest.presentation.list


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplicationtest.databinding.ItemBinding
import com.example.myapplicationtest.domain.TappticEntity
import com.squareup.picasso.Picasso

class AdapterRv(var list: List<TappticEntity>, var adapterOnClickListener: AdapterOnClickListener): RecyclerView.Adapter<AdapterRv.TappticViewHolder>() {
    fun updateItems(newList: List<TappticEntity>) {
        val diffCallback: TappticAntityDiffCallback = TappticAntityDiffCallback(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TappticViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TappticViewHolder( binding, adapterOnClickListener)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TappticViewHolder, position: Int) {
        val tappticEntity: TappticEntity = list[position]
        holder.bind(tappticEntity)
    }


    class TappticViewHolder(
        private val binding: ItemBinding,
        var adapterOnClickListener: AdapterOnClickListener
    ) : ViewHolder(binding.root) {


        @SuppressLint("ClickableViewAccessibility")
        fun bind(tappticEntity: TappticEntity) {
            binding.textView.text = tappticEntity.name
            Picasso.get().load(tappticEntity.image).into(binding.imageView)
            binding.conLayout.setOnClickListener {

                adapterOnClickListener.onClick(tappticEntity)

            }
            binding.conLayout.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.conLayout.setBackgroundColor(Color.GRAY)

                        true

                    }
                    MotionEvent.ACTION_UP -> {

                        binding.conLayout.setBackgroundColor(Color.WHITE)
                        adapterOnClickListener.onClick(tappticEntity)
                        binding.conLayout.setBackgroundColor(Color.GREEN)
                        true
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        binding.conLayout.setBackgroundColor(Color.WHITE)
                        true
                    }
                    else -> false

                }
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
