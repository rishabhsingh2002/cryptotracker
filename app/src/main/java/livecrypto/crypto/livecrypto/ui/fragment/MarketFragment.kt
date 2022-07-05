package livecrypto.crypto.livecrypto.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smarteist.autoimageslider.SliderView
import livecrypto.crypto.livecrypto.adapter.PagerAdapters
import livecrypto.crypto.livecrypto.adapter.SliderAdapter
import livecrypto.crypto.livecrypto.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding

    lateinit var imageUrl: ArrayList<String>

    lateinit var sliderView: SliderView

    lateinit var sliderAdapter: SliderAdapter

    private lateinit var pagerAdapters: PagerAdapters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(layoutInflater)

        //Slider setup
        sliderSetUp()

        //Tab layout setup
        tabLayoutSetUp()

        binding.viewPager



        return binding.root
    }

    private fun sliderSetUp() {
        sliderView = binding.slider
        imageUrl = ArrayList()

        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/live-crypto-d00e7.appspot.com/o/imageSlider%2FPopular%20Crytocurrency.png?alt=media&token=4b9185f8-bd4d-4f1e-8b17-e5a82243a392") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/live-crypto-d00e7.appspot.com/o/imageSlider%2FGainersCrytocurrency.png?alt=media&token=6aa799a1-4305-4032-8712-7632f3b33282") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://firebasestorage.googleapis.com/v0/b/live-crypto-d00e7.appspot.com/o/imageSlider%2FLoosers%20Crytocurrency.png?alt=media&token=360c719d-4129-46a3-8a80-af76ce7e9e97") as ArrayList<String>

        sliderAdapter = SliderAdapter(imageUrl)

        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.scrollTimeInSec = 2

        sliderView.isAutoCycle = true

        sliderView.startAutoCycle()
    }

    private fun tabLayoutSetUp() {
        /**set Fragment List*/
        pagerAdapters = PagerAdapters(childFragmentManager)
        pagerAdapters.addFragment(PopularCryptoFragment(), "Popular")
        pagerAdapters.addFragment(TopGainersFragment(), "Top Gainers")
        pagerAdapters.addFragment(TopLossersFragment(), "Top Lossers")
        /** set view page adapter*/
        binding.viewPager.adapter = pagerAdapters
        /** set tabs*/
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}