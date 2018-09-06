package uz.diamondsolutions.mk_test.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.currency_rv_item.view.*
import uz.diamondsolutions.mk_test.R
import uz.diamondsolutions.mk_test.retrofit.models.Crypto

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(currency: Crypto?) {
        itemView.cur_id.text = currency!!.id!!.toString()
        itemView.cur_name.text = currency.name
        itemView.cur_slug.text = currency.websiteSlug
        itemView.cur_symbol.text = currency.symbol
    }

    companion object {
        fun create(parent: ViewGroup): CurrencyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.currency_rv_item, parent, false)
            return CurrencyViewHolder(view)
        }
    }

}