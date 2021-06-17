package ru.geekbrains.lyagaev.popularlibraries

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.lyagaev.popularlibraries.presenter.IUserListPresenter
import ru.geekbrains.lyagaev.popularlibraries.view2.UserItemView
import ru.geekbrains.lyagaev.popularlibraries.databinding.ItemUserBinding
import ru.geekbrains.lyagaev.popularlibraries.intarface.IImageLoader

class UsersRVAdapter(private val presenter: IUserListPresenter, val imageLoader: IImageLoader<ImageView>) : RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(private val vb: ItemUserBinding) : RecyclerView.ViewHolder(vb.root),
        UserItemView {
        override var pos = -1

        override fun setLogin(text: String) = with(vb) {
            tvLogin.text = text
        }

        override fun loadAvatar(url: String)= with(vb) {
            imageLoader.loadInto(url,  ivAvatar)
        }
    }
}