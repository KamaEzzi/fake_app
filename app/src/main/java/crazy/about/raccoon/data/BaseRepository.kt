package crazy.about.raccoon.data

interface BaseRepository {
    fun getSigns() : List<String>
    fun getPredictionByIndex(idx: Int): String
}