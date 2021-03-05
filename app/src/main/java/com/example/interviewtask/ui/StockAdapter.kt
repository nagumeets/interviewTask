package com.example.interviewtask.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtask.data.models.StockOwnerItem
import com.example.interviewtask.databinding.ItemStockersBinding


class StockAdapter :
        PagingDataAdapter<StockOwnerItem, StockAdapter.StockViewHolder>(StockComparator) {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): StockViewHolder {
        return StockViewHolder(
                ItemStockersBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindStocker(it) }
    }

    inner class StockViewHolder(private val binding: ItemStockersBinding) :
            RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindStocker(item: StockOwnerItem) = with(binding) {
            profileImage.loadImage(item.owner.profile_image)
            userName.text = item.owner.display_name
            scoreTxt.text = "Score:${item.score}"
            binding.root.setOnClickListener { view ->
                val bundle = bundleOf(
                        "display_name" to item.owner.display_name,
                        "score" to item.score,
                        "profile_image" to item.owner.profile_image,
                        "create_date" to item.creation_date,
                        "last_date" to item.last_activity_date,
                        "link" to item.owner.link,
                        "licence" to item.content_license
                )
                //Navigation.findNavController(view).navigate(R.id.action_home_to_details, bundle)
                 val action=StockFragmentDirections.actionDestinationHomeToDestinationDetails(
                         item.owner.profile_image, item.owner.display_name, item.score, item.creation_date, item.last_activity_date, item.owner.link, item.content_license)
                 Navigation.findNavController(view).navigate(action)
            }
        }
    }

    object StockComparator : DiffUtil.ItemCallback<StockOwnerItem>() {
        override fun areItemsTheSame(oldItem: StockOwnerItem, newItem: StockOwnerItem): Boolean {
            return oldItem.question_id == newItem.question_id
        }

        override fun areContentsTheSame(oldItem: StockOwnerItem, newItem: StockOwnerItem): Boolean {
            return oldItem == newItem
        }
    }
}