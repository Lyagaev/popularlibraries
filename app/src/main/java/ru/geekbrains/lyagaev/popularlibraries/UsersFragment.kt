package ru.geekbrains.lyagaev.popularlibraries

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.lyagaev.popularlibraries.presenter.UsersPresenter
import ru.geekbrains.lyagaev.popularlibraries.view2.UsersView
import ru.geekbrains.lyagaev.popularlibraries.databinding.FragmentUsersBinding
import ru.geekbrains.lyagaev.popularlibraries.navigation.AndroidScreens
import ru.geekbrains.lyagaev.popularlibraries.network.ApiHolder
import ru.geekbrains.lyagaev.popularlibraries.repository.GithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.repository.RetrofitGithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.utils.ConverterImage
import ru.geekbrains.lyagaev.popularlibraries.utils.Image

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        private const val IMAGE_REQUEST_ID = 1
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter { UsersPresenter(
        RetrofitGithubUsersRepo(ApiHolder.api),
        App.instance.router,
        AndroidScreens(),
        ConverterImage(context),
        AndroidSchedulers.mainThread()) }
    var adapter: UsersRVAdapter? = null

    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter

        vb?.btnConverter?.setOnClickListener {
            presenter.convertClick()
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    override fun pickImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(Intent.createChooser(intent, "Выбирите изображение"), IMAGE_REQUEST_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let {uri ->
                    val bytes = context?.contentResolver?.openInputStream(uri)?.buffered()?.use { it.readBytes() }
                    bytes?.let {
                        presenter.imageSelected(Image(bytes))
                    }
                }
            }
        }
    }

    override fun convertSuccess() {
        Toast.makeText(context, "Изображение изменено", Toast.LENGTH_SHORT).show()
    }

}