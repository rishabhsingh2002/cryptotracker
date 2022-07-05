package livecrypto.crypto.livecrypto.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import livecrypto.crypto.livecrypto.R
import livecrypto.crypto.livecrypto.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {
    private lateinit var binding: FragmentPrivacyPolicyBinding

    private val url = "file:///android_asset/privacy_policy.html"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrivacyPolicyBinding.inflate(layoutInflater)


        //Toolbar Setup
        toolBarSetUp()

        //WebView setup
        webViewSetup(url)


        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(webUrl: String) {
        //add the privacy policy file in asset folder
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webUrl)
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarPrivacyPolicy)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_stack)
            binding.toolbarPrivacyPolicy.title = "Privacy Policy"
        }
        binding.toolbarPrivacyPolicy.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}