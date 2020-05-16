package crazy.about.raccoon.vm

import androidx.core.util.Consumer
import androidx.lifecycle.*
import crazy.about.raccoon.data.HoroscopeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainVm: ViewModel(), Consumer<Event> {

    private val repository = HoroscopeRepository()

    private val publisher = MutableLiveData<ScreenState>()

    private var job: Job? = null

    val screenState: LiveData<ScreenState>
        get() = publisher

    override fun accept(t: Event) {
        when(t) {
            is Event.Init -> initScreen()
            is Event.ItemSelect -> {
                postState(ScreenState.DataLoad())
                job?.cancelChildren()
                job = viewModelScope.launch {
                    delay(500)
                    val allSigns = repository.getSigns()
                    val text = repository.getPredictionByIndex(t.position)
                    val newState = ScreenState.Data(allSigns, t.position, text)
                    postState(newState)
                }
            }
        }
    }

    private fun initScreen() {

        viewModelScope.launch {
            postState(ScreenState.Load)
            delay(1500)
            val allSigns = repository.getSigns()
            val newState = ScreenState.Data(allSigns, 0, null)
            postState(newState)
        }

    }

    private fun postState(newState: ScreenState) {
        publisher.postValue(newState)
    }

}