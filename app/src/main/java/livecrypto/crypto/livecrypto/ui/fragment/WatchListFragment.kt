package livecrypto.crypto.livecrypto.ui.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import livecrypto.crypto.livecrypto.R
import livecrypto.crypto.livecrypto.adapter.MarketAdapter
import livecrypto.crypto.livecrypto.api.APIInterface
import livecrypto.crypto.livecrypto.api.APIUtilities
import livecrypto.crypto.livecrypto.databinding.FragmentWatchListBinding
import livecrypto.crypto.livecrypto.model.CryptoCurrency

class WatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchListBinding

    private lateinit var watchList: ArrayList<String>
    private lateinit var watchListItem: ArrayList<CryptoCurrency>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchListBinding.inflate(layoutInflater)


        readData()

        //toolbar setup
        toolBarSetUp()

        lifecycleScope.launch(Dispatchers.IO) {
            val res = APIUtilities.getInstance().create(APIInterface::class.java).getCryptoData()

            if (res.body() != null) {

                withContext(Dispatchers.Main) {
                    watchListItem = ArrayList()
                    watchListItem.clear()

                    for (watchData in watchList) {
                        for (item in res.body()!!.data.cryptoCurrencyList) {
                            if (watchData == item.symbol) {
                                watchListItem.add(item)
                            }
                        }
                    }
                    if (watchListItem.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvEmpty.visibility = View.GONE

                    }
                    binding.rvWatchlist.adapter =
                        MarketAdapter(requireContext(), watchListItem, "Watchlist")
                }
            }
        }

        return binding.root
    }

    private fun readData() {
        val sharedPreference =
            requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreference.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }


    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWatchlist)
        if ((activity as AppCompatActivity).supportActionBar != null) {
        }
        binding.toolbarWatchlist.title = ""
        setHasOptionsMenu(true)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.watchlist_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_share -> {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    var shareMessage = "\nShare with you loved ones\n\n"
                    shareMessage = """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${livecrypto.crypto.livecrypto.BuildConfig.APPLICATION_ID}""".trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {
                }
                true
            }
            R.id.mi_rate_us -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + requireActivity().packageName)
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                        )
                    )
                }
                true
            }
            R.id.mi_privacy -> {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_watchListFragment_to_privacyPolicyFragment)
                true
            }
            R.id.mi_feedback -> {
                val intent = Intent(Intent.ACTION_SEND)
                val recipients = arrayOf("rishabh1112131415@gmail.com")
                intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                intent.putExtra(Intent.EXTRA_SUBJECT, "")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.putExtra(Intent.EXTRA_CC, "mailcc@gmail.com")
                intent.type = "text/html"
                intent.setPackage("com.google.android.gm")
                startActivity(Intent.createChooser(intent, "Send mail"))
                true
            }
        }

        return true
    }


}