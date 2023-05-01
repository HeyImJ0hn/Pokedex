package dam_a47471.pokedex.ui.pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.databinding.ItemPokemonBinding
import dam_a47471.pokedex.ui.events.OnItemClickedListener

class PokemonsAdapter(
    private var pokemonList: List<Pokemon>,
    private val itemClickedListener: OnItemClickedListener? = null,
    private val context: Context?,
) :
    RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemPokemonBinding.bind(itemView)

        fun bindView(pokemonItem: Pokemon, itemClickedListener: OnItemClickedListener?) {
            viewBinding.pokemon = pokemonItem
            itemView.setOnClickListener {
                itemClickedListener?.invoke(pokemonItem)
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
        holder.bindView(item, itemClickedListener)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun setPokemonList(list: List<Pokemon>) {
        this.pokemonList = list
    }

    companion object {
        private val POKEMON_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }


}
