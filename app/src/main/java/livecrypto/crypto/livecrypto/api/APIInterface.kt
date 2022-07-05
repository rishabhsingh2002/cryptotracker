package livecrypto.crypto.livecrypto.api

import livecrypto.crypto.livecrypto.model.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
   suspend fun getCryptoData(): Response<MarketModel>

}