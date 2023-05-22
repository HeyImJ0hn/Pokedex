package dam_a47471.pokedex.ui.pokemon.detail

import EqualSpacingItemDecoration
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dam_a47471.pokedex.PokemonContainer
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.data.mocks.PokemonMockData
import dam_a47471.pokedex.databinding.FragmentPokemonDetailBinding
import dam_a47471.pokedex.model.repository.PokemonMapper
import dam_a47471.pokedex.model.repository.PokemonRepository
import dam_a47471.pokedex.ui.evolution.EvolutionAdapter
import dam_a47471.pokedex.ui.type.TypeAdapter

class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val viewModel: PokemonDetailViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val activity: Activity? = activity
        if (activity is AppCompatActivity) {
            // Cast the Activity to AppCompatActivity to access the toolbar
            val appCompatActivity = activity
            // Hide the toolbar
            if (appCompatActivity.supportActionBar != null) {
                appCompatActivity.supportActionBar!!.hide()
            }
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = checkNotNull(
            arguments?.getParcelable("pokemon", Pokemon::class.java)
        )
        viewModel.initViewMode(PokemonContainer.getInstance(requireContext()).pokemonRepository)
        binding.pokemon = pokemon
        viewModel.getPokemonDetail(pokemon).observe(viewLifecycleOwner, Observer {
            val detail = PokemonMapper.toPokemonDetail(it.detail)
            binding.pkDetail = detail
            binding.pkStats = PokemonMapper.toPokemonStats(it.stats)
            binding.typeListView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            binding.typeListView.adapter = TypeAdapter(pokemon.types, view.context)
            binding.typeListView.addItemDecoration(
                EqualSpacingItemDecoration(30, EqualSpacingItemDecoration.HORIZONTAL)
            );
            binding.evolutionListView.adapter = EvolutionAdapter(detail.evolution!!, view.context)
            binding.evolutionListView.addItemDecoration(
                EqualSpacingItemDecoration(30, EqualSpacingItemDecoration.VERTICAL)
            );
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val activity: Activity? = activity
        if (activity is AppCompatActivity) {
            // Cast the Activity to AppCompatActivity to access the toolbar
            val appCompatActivity = activity
            // Hide the toolbar
            if (appCompatActivity.supportActionBar != null) {
                appCompatActivity.supportActionBar!!.show()
            }
        }
    }
}
