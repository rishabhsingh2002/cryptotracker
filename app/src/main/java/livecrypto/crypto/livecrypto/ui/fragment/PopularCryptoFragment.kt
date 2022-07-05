package livecrypto.crypto.livecrypto.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import livecrypto.crypto.livecrypto.adapter.PopularCryptoAdapter
import livecrypto.crypto.livecrypto.api.APIInterface
import livecrypto.crypto.livecrypto.api.APIUtilities
import livecrypto.crypto.livecrypto.databinding.FragmentPopularCryptoBinding

class PopularCryptoFragment : Fragment() {
    private lateinit var binding: FragmentPopularCryptoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularCryptoBinding.inflate(layoutInflater)

        getTopCurrencyList()

        return binding.root
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = APIUtilities.getInstance().create(APIInterface::class.java).getCryptoData()

            withContext(Dispatchers.Main) {
                binding.popularRecyclerView.adapter =
                    PopularCryptoAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
                binding.spinKitView.visibility = View.GONE
            }

            Log.d("TAG", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }


}