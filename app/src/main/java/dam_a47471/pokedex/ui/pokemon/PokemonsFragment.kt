package dam_a47471.pokedex.ui.pokemon

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.databinding.FragmentPokemonsBinding
import dam_a47471.pokedex.ui.region.RegionAdapter

class PokemonsFragment : Fragment() {
    private var _binding: FragmentPokemonsBinding? = null
    private val viewModel: PokemonsViewModel by viewModels()
    private val binding get() = _binding!!

    private var filtered = false
    private var search = ""
    private var filteredTypes : MutableList<PokemonType> = mutableListOf()

    private var pokemons : List<Pokemon> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = Dialog(view.context)

        val region = checkNotNull(arguments?.getParcelable("region", PokemonRegion::class.java))

        _binding?.floatingSearchBtn?.setOnClickListener {
            dialog.setContentView(R.layout.search_popup)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            dialog.show()

            val clearBtn = dialog.findViewById<Button>(R.id.clearBtn)
            val searchBtn = dialog.findViewById<Button>(R.id.searchBtn)

            clearBtn.setOnClickListener {
                filtered = false
                dialog.hide()
            }

            searchBtn.setOnClickListener {
                filtered = true
                search = dialog.findViewById<EditText>(R.id.searchInput).text.toString()
                filter(pokemons)
                dialog.hide()
            }

        }

        viewModel.getListPokemonsByRegion(region).observe(viewLifecycleOwner, Observer {
            println(it)
            pokemons = it
            binding.pokemonsRecyclerView.adapter = PokemonsAdapter(
                pokemons, itemClickedListener = { pokemon ->
                    val bundle = bundleOf(
                        "pokemon" to pokemon
                    )
                    findNavController().navigate(
                        R.id.action_nav_pokemon_to_pokemonDetail, bundle, null
                    )
                }, view.context
            )
            binding.pokemonsRecyclerView.adapter?.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filter(pokemons : List<Pokemon>) : List<Pokemon> {
        if (!filtered)
            return pokemons

        val list : MutableList<Pokemon> = mutableListOf()
        for (pokemon in pokemons) {
            if (pokemon.name.contains(search, ignoreCase = true)) {
                println(pokemon.name)
                list.add(pokemon)
            }
        }
        this.pokemons = list
        return list
    }

}