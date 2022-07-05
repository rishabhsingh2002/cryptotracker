package livecrypto.crypto.livecrypto.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import livecrypto.crypto.livecrypto.R
import livecrypto.crypto.livecrypto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailsFragment || destination.id == R.id.privacyPolicyFragment) {

                binding.bottomNavigationView.visibility = View.GONE
            } else {

                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }
}