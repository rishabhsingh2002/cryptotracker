package livecrypto.crypto.livecrypto.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIUtilities {

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}