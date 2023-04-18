package dam_a47471.pokedex.ui.region

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.databinding.ItemRegionBinding
import dam_a47471.pokedex.ui.events.OnItemClickedListener

class RegionAdapter(
    private val pkRegionList: List<PokemonRegion>,
    private val itemClickedListener: OnItemClickedListener? = null,
    private val context: Context
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemRegionBinding.bind(itemView)

        fun bindView(regionItem: PokemonRegion, itemClickedListener: OnItemClickedListener?) {
            viewBinding.regionNameTextView.text = regionItem.name
            viewBinding.regionIdTextView.text = String.format(
                itemView.context.getString(
                    R.string.pk_generations
                ), regionItem.id
            )
            viewBinding.regionBgImage.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context, regionItem.bg
                )
            )
            viewBinding.regionStartersImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context, regionItem.starters
                )
            )
            //viewBinding.region = regionItem
            itemView.setOnClickListener {
                itemClickedListener?.invoke(regionItem)
            };
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pkRegionList[position]
        holder.bindView(item, itemClickedListener)
    }

    override fun getItemCount(): Int {
        return pkRegionList.size
    }
}