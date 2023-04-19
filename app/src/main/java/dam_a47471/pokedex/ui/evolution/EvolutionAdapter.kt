package dam_a47471.pokedex.ui.evolution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.PokemonEvolution
import dam_a47471.pokedex.databinding.ItemEvolutionBinding

class EvolutionAdapter(
    private val list: List<PokemonEvolution>,
    private val context: Context
) : RecyclerView.Adapter<EvolutionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemEvolutionBinding.bind(itemView)
        fun bindView(item: PokemonEvolution) {
            viewBinding.evolution = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_evolution, parent, false)
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
