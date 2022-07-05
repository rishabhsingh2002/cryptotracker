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
import livecrypto.crypto.livecrypto.ui.fragment.SearchFragmentDirections
import livecrypto.crypto.livecrypto.ui.fragment.WatchListFragmentDirections

class MarketAdapter(var context: Context, var list: ArrayList<CryptoCurrency>, var type: String) :
    RecyclerView.Adapter<MarketAdapter.TopGainerViewHolder>() {

    inner class TopGainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = CryptoItemBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopGainerViewHolder {
        return TopGainerViewHolder(
            LayoutInflater.from(context).inflate(R.layout.crypto_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopGainerViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        //crypto image
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
            if (type == "Home") {
                Navigation.findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                )
            } else if (type == "Search") {
                Navigation.findNavController(it).navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(item)
                )
            } else if (type == "Watchlist") {
                Navigation.findNavController(it).navigate(
                    WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(item)
                )
            }


        }
    }

    fun updateData(dataItem: ArrayList<CryptoCurrency>) {
        list = dataItem
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return list.size
    }
}