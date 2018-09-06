package uz.diamondsolutions.mk_test.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import uz.diamondsolutions.mk_test.R
import uz.diamondsolutions.mk_test.retrofit.models.Crypto
import uz.diamondsolutions.mk_test.utils.CurrencyViewHolder

class CurrenciesAdapter(private val retryCallback: () -> Unit) : PagedListAdapter<Crypto, RecyclerView.ViewHolder>(CryptoDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.currency_rv_item -> (holder as CurrencyViewHolder).bindTo(getItem(position))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.currency_rv_item -> CurrencyViewHolder.create(parent)
            else -> throw IllegalArgumentException("Ошибка")
        }
    }

    private fun hasExtraRow(): Boolean {
        return false
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_rv_item
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    companion object {
        val CryptoDiffCallback = object : DiffUtil.ItemCallback<Crypto>() {
            override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
                return oldItem == newItem
            }
        }
    }

}

