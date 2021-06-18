package ru.geekbrains.lyagaev.popularlibraries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.geekbrains.lyagaev.popularlibraries.databinding.ItemRepositoryBinding
import ru.geekbrains.lyagaev.popularlibraries.presenter.IRepositoryListPresenter
import ru.geekbrains.lyagaev.popularlibraries.view2.RepositoryItemView


class ReposotoriesRVAdapter(val presenter: IRepositoryListPresenter) :
    RecyclerView.Adapter<ReposotoriesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
    }

    inner class ViewHolder(private val vb: ItemRepositoryBinding) : RecyclerView.ViewHolder(vb.root),
        RepositoryItemView {
        override var pos = -1
        override fun setName(text: String)= with(vb) {
            tvName.text = text
        }
    }
}