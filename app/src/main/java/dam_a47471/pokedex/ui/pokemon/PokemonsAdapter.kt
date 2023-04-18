package dam_a47471.pokedex.ui.pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.databinding.ItemPokemonBinding
import dam_a47471.pokedex.ui.region.RegionAdapter

class PokemonsAdapter(private val pokemonList: List<Pokemon>, private val context: Context?) :
    RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemPokemonBinding.bind(itemView)

        fun bindView(pokemonItem: Pokemon) {
            viewBinding.pokemon = pokemonItem
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, String.format("Click in %s Pokemon", pokemonItem.name), Toast.LENGTH_LONG).show()
            };
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
