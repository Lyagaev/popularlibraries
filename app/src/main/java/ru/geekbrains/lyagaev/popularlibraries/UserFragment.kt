package ru.geekbrains.lyagaev.popularlibraries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.lyagaev.popularlibraries.adapter.ReposotoriesRVAdapter
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.presenter.UserPresenter
import ru.geekbrains.lyagaev.popularlibraries.view2.UserView
import ru.geekbrains.lyagaev.popularlibraries.databinding.FragmentUserBinding
import ru.geekbrains.lyagaev.popularlibraries.network.ApiHolder
import ru.geekbrains.lyagaev.popularlibraries.repository.RetrofitGithubRepositoriesRepo
import ru.geekbrains.lyagaev.popularlibraries.room.RoomGithubRepositoryCache
import ru.geekbrains.lyagaev.popularlibraries.room.db.Database

class UserFragment : MvpAppCompatFragment(), UserView {
    private lateinit var binding: FragmentUserBinding
    //private val binding get() = _binding!!
    private val user by lazy {
        arguments?.getParcelable<GithubUser>(USER_LOGIN) as GithubUser
    }
    var adapter: ReposotoriesRVAdapter? = null

    val presenter: UserPresenter by moxyPresenter { UserPresenter(
        RetrofitGithubRepositoriesRepo(ApiHolder.api, AndroidNetworkStatus(requireContext()),
            RoomGithubRepositoryCache(Database.getInstance())
            //Database.getInstance()
                    ),
        App.instance.router,
        user,
        AndroidSchedulers.mainThread()) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        private const val USER_LOGIN = "USER_LOGIN"
        fun newInstance(user: GithubUser) =
                UserFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(USER_LOGIN, user)
                    }
                }
    }

    override fun init() {
        binding.rvRepositories.layoutManager = LinearLayoutManager(context)
        adapter = ReposotoriesRVAdapter(presenter.repositoriesListPresenter)
        binding.rvRepositories.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showLogin(userLogin: String) {
        binding.tvUserName.text = userLogin
    }
}