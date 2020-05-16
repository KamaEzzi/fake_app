package crazy.about.raccoon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import crazy.about.raccoon.R
import crazy.about.raccoon.vm.Event
import crazy.about.raccoon.vm.MainVm

class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainVm>()

    private lateinit var viewHolder: ContentViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewHolder = ContentViewHolder(findViewById(android.R.id.content)) { event ->
            vm.accept(event)
        }
        vm.accept(Event.Init)
        vm.screenState.observe(this, viewHolder)
    }

}
