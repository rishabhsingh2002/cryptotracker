package livecrypto.crypto.livecrypto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import livecrypto.crypto.livecrypto.R
import livecrypto.crypto.livecrypto.databinding.CryptoItemBinding
import livecrypto.crypto.livecrypto.model.CryptoCurrency
import livecrypto.crypto.livecrypto.ui.fragment.MarketFragmentDirections

class PopularCryptoAdapter(var context: Context, var list: List<CryptoCurrency>) :
    RecyclerView.Adapter<PopularCryptoAdapter.PopularCryptoViewHolder>() {

    inner class PopularCryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CryptoItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCryptoViewHolder {
        return PopularCryptoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.crypto_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularCryptoViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        //image
        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        //crypto chart
        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        holder.binding.currencyPriceTextView.text =
            "$" + String.format(("%.02f"), item.quotes[0].price)
        //"$" + item.quotes[0].price.toString()

        if (item.quotes[0].percentChange24h > 0) {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text =
                "+ ${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        }
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(
                MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}