package dam_a47471.pokedex.ui.pokemon

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.databinding.FragmentPokemonsBinding
import dam_a47471.pokedex.ui.region.RegionAdapter

class PokemonsFragment : Fragment() {
    private var _binding: FragmentPokemonsBinding? = null
    private val viewModel: PokemonsViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val region = checkNotNull(arguments?.getParcelable("region", PokemonRegion::class.java))
        viewModel.getListPokemonsByRegion(region).observe(viewLifecycleOwner, Observer {
            val pokemons: List<Pokemon> = it
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
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}