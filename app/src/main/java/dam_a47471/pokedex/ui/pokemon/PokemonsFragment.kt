package dam_a47471.pokedex.ui.pokemon

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.databinding.FragmentPokemonsBinding

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
        //val region = checkNotNull(arguments?.getParcelable("region", PokemonRegion::class.java))
        val id = checkNotNull(arguments?.getInt("id"))
        val name = checkNotNull(arguments?.getString("name"))
        val bg = checkNotNull(arguments?.getInt("bg"))
        val starters = checkNotNull(arguments?.getInt("starters"))
        val region = PokemonRegion(id, name, bg, starters)
        viewModel.getListPokemonsByRegion(region).observe(viewLifecycleOwner, Observer {
            val pokemons: List<Pokemon> = it
            binding.pokemonsRecyclerView.adapter = PokemonsAdapter(pokemons, view.context)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}