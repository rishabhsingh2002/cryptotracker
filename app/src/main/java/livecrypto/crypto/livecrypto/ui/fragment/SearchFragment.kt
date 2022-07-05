package livecrypto.crypto.livecrypto.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import livecrypto.crypto.livecrypto.adapter.MarketAdapter
import livecrypto.crypto.livecrypto.api.APIInterface
import livecrypto.crypto.livecrypto.api.APIUtilities
import livecrypto.crypto.livecrypto.databinding.FragmentSearchBinding
import livecrypto.crypto.livecrypto.model.CryptoCurrency
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var list: ArrayList<CryptoCurrency>
    private lateinit var adapter: MarketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)


        list = ArrayList()


        adapter = MarketAdapter(requireContext(), list,"Search")
        binding.rvSearch.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val res = APIUtilities.getInstance().create(APIInterface::class.java).getCryptoData()

            if (res.body() != null) {
                withContext(Dispatchers.Main) {
                    list = res.body()!!.data.cryptoCurrencyList as ArrayList<CryptoCurrency>
                    adapter.updateData(list)
                    binding.spinKitView.visibility = View.GONE
                }
            }
        }

        //searching coin
        searchCrypto()

        return binding.root
    }

    lateinit var searchText: String
    private fun searchCrypto() {

        binding.edtCrypto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().toLowerCase()

                updatedRecyclerview()
            }

            private fun updatedRecyclerview() {
                val data = ArrayList<CryptoCurrency>()

                for (item in list) {
                    val coinName = item.name.lowercase(Locale.getDefault())
                    val coinSymbols = item.symbol.lowercase(Locale.getDefault())

                    if (coinName.contains(searchText) || coinSymbols.contains(searchText)){
                        data.add(item)
                    }
                }
                adapter.updateData(data)


            }
        })

    }

}