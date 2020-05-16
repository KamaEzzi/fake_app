package crazy.about.raccoon.vm

sealed class ScreenState {

    object Load : ScreenState()

    class DataLoad(): ScreenState()

    class Data(val items: List<String>,
               val selectedItem: Int,
               val content: String?
    ): ScreenState()

    object Error : ScreenState()

}