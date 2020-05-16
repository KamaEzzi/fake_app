package crazy.about.raccoon.vm

sealed class Event {

    object Init : Event()
    class ItemSelect(val position: Int): Event()

}