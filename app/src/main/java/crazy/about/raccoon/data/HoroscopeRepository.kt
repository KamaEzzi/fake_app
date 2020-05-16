package crazy.about.raccoon.data

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class HoroscopeRepository : BaseRepository {

    private val signs = ArrayList<String>()
    private val lock = ReentrantReadWriteLock()


    override fun getSigns() : List<String> =  lock.write {
        if(signs.isEmpty()) {
            for (i in 0..12){
                signs.add("Sign $i")
            }
        }
        return signs
    }


    override fun getPredictionByIndex(idx: Int): String {
        val sign = lock.read { signs[idx] }
        return generateText(sign)
    }

    private fun generateText(sign: String): String {
        return "Ham hock salami pork, shoulder sirloin jerky chislic burgdoggen turkey chuck ribeye hamburger ball tip short loin. Strip steak beef ribs andouille, biltong ground round pork tri-tip sausage jerky cow kielbasa bacon porchetta.  Sausage venison chicken porchetta, andouille pork sirloin cupim prosciutto ham hock beef ribs doner kielbasa"
            .split(" ")
            .shuffled().reduce { acc, s -> "$acc $s" }
            .let {
                it.trim()
                "$sign $it"
            }
    }

}