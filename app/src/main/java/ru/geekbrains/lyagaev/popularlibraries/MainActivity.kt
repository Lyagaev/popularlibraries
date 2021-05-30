package ru.geekbrains.lyagaev.popularlibraries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.geekbrains.lyagaev.popularlibraries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listener = View.OnClickListener {
            presenter.counterClick(it.tag.toString().toInt())
        }

        binding?.btnCounter1?.setOnClickListener(listener)
        binding?.btnCounter2?.setOnClickListener(listener)
        binding?.btnCounter3?.setOnClickListener(listener)
    }

    fun setButtonText(index: Int, text: String) {
        when(index){
            0 -> binding?.btnCounter1?.text = text
            1 -> binding?.btnCounter2?.text = text
            2 -> binding?.btnCounter3?.text = text
        }
    }


}


