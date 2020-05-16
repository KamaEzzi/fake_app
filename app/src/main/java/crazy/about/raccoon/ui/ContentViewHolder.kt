package crazy.about.raccoon.ui

import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import crazy.about.raccoon.R
import crazy.about.raccoon.vm.Event
import crazy.about.raccoon.vm.ScreenState

class ContentViewHolder(rootView: View, private val selectFun: (Event) -> Unit) : Observer<ScreenState> {
    private val loader: ProgressBar = rootView.findViewById(R.id.loader_pb)
    private val btnLoader: ProgressBar = rootView.findViewById(R.id.btn_progress)
    private val contentGroup = rootView.findViewById<Group>(R.id.content_group)
    private val spinner: Spinner = rootView.findViewById(R.id.items_spinner)
    private val chooseBtn: TextView = rootView.findViewById(R.id.choose_btn)
    private val contentText: TextView = rootView.findViewById(R.id.content_tv)
    private val signsAdapter = ArrayAdapter<String>(rootView.context, R.layout.spinner_item)
    private var isInitialized = false

    init {
        signsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = signsAdapter
        chooseBtn.setOnClickListener {
            val event = Event.ItemSelect(spinner.selectedItemPosition)
            selectFun(event)
        }
    }


    override fun onChanged(s: ScreenState) {
        when(s) {
            is ScreenState.DataLoad -> {
                chooseBtn.isEnabled = false
                btnLoader.isVisible = true
            }
            is ScreenState.Data -> {
                chooseBtn.isEnabled = true
                btnLoader.isVisible = false
                contentGroup.isVisible = true
                loader.isVisible = false
                if(!isInitialized) {
                    isInitialized = true
                    signsAdapter.addAll(s.items)
                }
                spinner.setSelection(s.selectedItem)
                contentText.text = s.content
            }
            is ScreenState.Error -> {

            }

            is ScreenState.Load -> {
                contentGroup.isVisible = false
                loader.isVisible = true
            }
        }
    }

}