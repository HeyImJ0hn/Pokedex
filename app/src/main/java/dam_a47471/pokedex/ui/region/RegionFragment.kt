package dam_a47471.pokedex.ui.region

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.databinding.FragmentRegionBinding

class RegionFragment : Fragment() {
    private var _regionViewModel: RegionViewModel? = null
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private val regionViewModel get() = _regionViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _regionViewModel = ViewModelProvider(this)[RegionViewModel::class.java]
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regionViewModel.getRegions().observe(viewLifecycleOwner) {
            val regions: List<PokemonRegion> = it
            binding.regionsRecyclerView.adapter = RegionAdapter(
                regions, itemClickedListener = { region ->
                    val reg = region as PokemonRegion
                    val bundle = bundleOf(
                        "id" to reg.id,
                        "name" to reg.name,
                        "bg" to reg.bg,
                        "starters" to reg.starters
                    )
                    findNavController().navigate(
                        R.id.action_nav_regions_to_nav_pokemon, bundle, null
                    )
                }, view.context
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}