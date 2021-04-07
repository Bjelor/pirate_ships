package bilek.pirateships

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bilek.pirateships.databinding.ItemListContentBinding
import bilek.pirateships.model.PirateShip
import com.bumptech.glide.Glide

class PirateShipAdapter(
    private val context: Context,
    private val onClickListener: ((Long) -> Unit)
) :
    RecyclerView.Adapter<PirateShipAdapter.ViewHolder>() {

    var values: List<PirateShip> = emptyList()
        set(value) {

            val diffCallback = DiffUtilCallback(value, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            field = value

            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListContentBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.bind(item, onClickListener)
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pirateShip: PirateShip, onClickListener: ((Long) -> Unit)) {
            binding.pirateShip = pirateShip
            binding.root.setOnClickListener { onClickListener.invoke(pirateShip.id) }

            Glide.with(context).load(pirateShip.image).into(binding.image)
        }
    }

    inner class DiffUtilCallback(
        private val newList: List<PirateShip>,
        private val oldList: List<PirateShip>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldId = oldList.getOrNull(oldItemPosition)?.id
            val newId = newList.getOrNull(newItemPosition)?.id

            return (oldId != null) && (newId != null) && (oldId == newId)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList.getOrNull(oldItemPosition)
            val newItem = newList.getOrNull(newItemPosition)

            return (oldItem != null) && (newItem != null) && (oldItem == newItem)
        }
    }
}
