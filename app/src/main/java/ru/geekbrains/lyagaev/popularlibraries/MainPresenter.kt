package ru.geekbrains.lyagaev.popularlibraries

class MainPresenter(private val view: MainActivity) {
    private val model = CountersModel()

    fun counterClick(id: Int){
        val nextValue = model.next(id-1)
        view.setButtonText(id-1, nextValue.toString())
    }

}