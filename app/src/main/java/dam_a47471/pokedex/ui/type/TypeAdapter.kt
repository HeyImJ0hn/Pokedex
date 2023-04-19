package dam_a47471.pokedex.ui.type

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.databinding.ItemTypeBinding

class TypeAdapter(
    private val list: List<PokemonType>,
    private val context: Context
) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemTypeBinding.bind(itemView)
        fun bindView(item: PokemonType) {
            viewBinding.type = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
