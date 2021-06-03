package ru.geekbrains.lyagaev.popularlibraries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.presenter.UserPresenter
import ru.geekbrains.lyagaev.popularlibraries.view2.UserView
import ru.geekbrains.lyagaev.popularlibraries.databinding.FragmentUserBinding

class UserFragment : MvpAppCompatFragment(), UserView {
    private lateinit var binding: FragmentUserBinding
    //private val binding get() = _binding!!
    private val user by lazy {
        arguments?.getParcelable<GithubUser>(USER_LOGIN) as GithubUser
    }
    private val presenter by moxyPresenter {
        UserPresenter(App.instance.router, user)
    }

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

    override fun showLogin(userLogin: String) {
        binding.tvUserName.text = userLogin
    }
}