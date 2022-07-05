package livecrypto.crypto.livecrypto.ui.fragment

import android.os.Bundle
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
import livecrypto.crypto.livecrypto.databinding.FragmentTopLossersBinding
import livecrypto.crypto.livecrypto.model.CryptoCurrency
import java.util.*

class TopLossersFragment : Fragment() {
    private lateinit var binding: FragmentTopLossersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopLossersBinding.inflate(layoutInflater)


        getTopCurrencyList()

        return binding.root
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = APIUtilities.getInstance().create(APIInterface::class.java).getCryptoData()

            if (res.body() != null) {
                withContext(Dispatchers.Main) {
                    val dataItem = res.body()!!.data.cryptoCurrencyList
                    Collections.sort(dataItem) { o1, o2 ->
                        (o2.quotes[0].percentChange24h)
                            .compareTo(o1.quotes[0].percentChange24h)
                    }

                    binding.spinKitView.visibility = View.GONE
                    val list = ArrayList<CryptoCurrency>()

                    list.clear()
                    for (i in 0..50) {
                        list.add(dataItem[dataItem.size - 1 - i])
                    }
                    binding.topLossersRecyclerView.adapter =
                        MarketAdapter(requireContext(), list,"Home")
                }
            }

        }
    }

}