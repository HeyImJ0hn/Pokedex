package dam_a47471.pokedex.ui.pokemon

import android.app.Dialog
import android.content.res.ColorStateList
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
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dam_a47471.pokedex.PokemonContainer
import dam_a47471.pokedex.R
import dam_a47471.pokedex.data.Pokemon
import dam_a47471.pokedex.data.PokemonRegion
import dam_a47471.pokedex.data.PokemonType
import dam_a47471.pokedex.data.mocks.PokemonMockData
import dam_a47471.pokedex.databinding.FragmentPokemonsBinding
import dam_a47471.pokedex.databinding.SearchPopupBinding
import dam_a47471.pokedex.ui.region.RegionAdapter

class PokemonsFragment : Fragment() {
    private var _binding: FragmentPokemonsBinding? = null
    private val viewModel: PokemonsViewModel by viewModels()
    private val binding get() = _binding!!

    private var filtered = false
    private var exact = false
    private var search = ""
    private var filteredTypes: MutableList<String> = mutableListOf()

    private var ogPokemons: List<Pokemon> = listOf()

    private var typeCheckboxes: List<CheckBox> = listOf()
    private lateinit var exactCheckbox: CheckBox

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

        colorFAB()

        _binding?.floatingSearchBtn?.setOnClickListener {
            dialog.setContentView(R.layout.search_popup)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()

            val popupBinding: SearchPopupBinding = SearchPopupBinding.inflate(layoutInflater)
            dialog.setContentView(popupBinding.root)

            exactCheckbox = popupBinding.exactCb
            typeCheckboxes = listOf(
                popupBinding.waterBtn,
                popupBinding.fireBtn,
                popupBinding.bugBtn,
                popupBinding.ghostBtn,
                popupBinding.grassBtn,
                popupBinding.groundBtn,
                popupBinding.rockBtn,
                popupBinding.darkBtn,
                popupBinding.dragonBtn,
                popupBinding.electricBtn,
                popupBinding.fairyBtn,
                popupBinding.fightingBtn,
                popupBinding.iceBtn,
                popupBinding.normalBtn,
                popupBinding.psychicBtn,
                popupBinding.flyingBtn,
                popupBinding.poisonBtn,
                popupBinding.steelBtn
            )

            popupBinding.searchInput.setText(search)
            checkCheckboxes()

            popupBinding.clearBtn.setOnClickListener {
                clear()
                dialog.dismiss()
            }

            popupBinding.searchBtn.setOnClickListener {
                search(popupBinding)
                dialog.dismiss()
            }

        }

        val region = checkNotNull(arguments?.getParcelable("region", PokemonRegion::class.java))
        viewModel.initViewMode(PokemonContainer.getInstance(requireContext()).pokemonRepository)

        viewModel.getPokemonsByRegion(region).observe(viewLifecycleOwner, Observer {
            val pokemons: List<Pokemon> = it
            ogPokemons = it
            binding.pokemonsRecyclerView.adapter = PokemonsAdapter(
                pokemons, itemClickedListener = { pokemon ->
                    val bundle = bundleOf(
                        "pokemon" to pokemon
                    )
                    findNavController().navigate(
                        R.id.action_nav_pokemon_to_pokemonDetail, bundle, null
                    )
                    if (filtered)
                        clear()
                }, view.context
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filter(pokemons: List<Pokemon>): List<Pokemon> {
        if (!filtered && binding.pokemonsRecyclerView.adapter != null) {
            (binding.pokemonsRecyclerView.adapter as PokemonsAdapter).setPokemonList(pokemons)
            binding.pokemonsRecyclerView.adapter?.notifyDataSetChanged()
            return pokemons
        }

        if (!filtered)
            return pokemons

        val list: MutableList<Pokemon> = mutableListOf()
        for (pokemon in pokemons) {
            val pokeTypes: MutableList<String> = mutableListOf()
            pokemon.types.forEach { pokeTypes.add(it.name) }
            if (!exact) {
                if (pokemon.name.contains(search, ignoreCase = true) && filteredTypes.isEmpty())
                    list.add(pokemon)
                else if (pokemon.name.contains(
                        search,
                        ignoreCase = true
                    ) && filteredTypes.isNotEmpty() && pokeTypes.intersect(filteredTypes.toSet())
                        .isNotEmpty()
                )
                    list.add(pokemon)
            } else {
                /*if (pokemon.name.contains(search, ignoreCase = true) && filteredTypes.isNotEmpty())
                    if (filteredTypes == pokemon.types || (pokemon.types[0] == pokemon.types[1] && filteredTypes.size == 1 && filteredTypes[0] == pokemon.types[0]))
                        list.add(pokemon)*/
            }
        }

        if (binding.pokemonsRecyclerView.adapter != null) {
            (binding.pokemonsRecyclerView.adapter as PokemonsAdapter).setPokemonList(list)
            binding.pokemonsRecyclerView.adapter?.notifyDataSetChanged()
        }

        return list
    }

    private fun getFilteredTypes(binding: SearchPopupBinding) {
        filteredTypes = mutableListOf()
        for (checkbox in typeCheckboxes)
            if (checkbox.isChecked)
                for (type in PokemonMockData.pokemonTypeMock)
                    if (type.name == checkbox.text.toString().lowercase())
                        filteredTypes.add(type.name);

        exact = exactCheckbox.isChecked
    }

    private fun checkCheckboxes() {
        for (checkbox in typeCheckboxes) {
            for (type in filteredTypes)
                if (checkbox.text.toString().lowercase() == type)
                    checkbox.isChecked = true
        }
        exactCheckbox.isChecked = exact
    }

    private fun clear() {
        if (filtered) {
            filtered = false
            filter(ogPokemons)
            colorFAB()
        }
        filteredTypes = mutableListOf()
        for (checkbox in typeCheckboxes)
            checkbox.isChecked = false
        exact = false
        exactCheckbox.isChecked = exact
        search = ""
        _binding?.floatingSearchBtn!!.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
        _binding?.floatingSearchBtn!!.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.black))
    }

    private fun search(binding: SearchPopupBinding) {
        search = binding.searchInput.text.toString()
        getFilteredTypes(binding)
        if (search != "" || filteredTypes.isNotEmpty()) {
            filtered = true
            filter(ogPokemons)
            colorFAB()
        }
    }

    private fun colorFAB() {
        if (filtered) {
            _binding?.floatingSearchBtn!!.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.red_500))
            _binding?.floatingSearchBtn!!.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
        } else {
            _binding?.floatingSearchBtn!!.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.white))
            _binding?.floatingSearchBtn!!.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.black))
        }
    }
}