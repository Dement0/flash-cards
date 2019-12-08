package com.example.flashcards.ui.notifications

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.R
import com.example.flashcards.db.Flashcard
import kotlinx.android.synthetic.main.notifications_fragment.*
import kotlin.random.Random


class NotificationsFragment : Fragment() {

    companion object {
        fun openNotificationsFragment() = NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notifications_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

        // LayoutManager and Adapter
        recyclerView_notifications.layoutManager = LinearLayoutManager(this.context)
        recyclerView_notifications.adapter = NotificationsAdapter()

        // Get variables from shared preferences
        setUpViews()
    }

    private fun setUpViews() {

        // Get Flashcard name from shared preferences
        val flashcard_name = "None"

        // Create new Flashcard
        val newFlashcard = Flashcard(Random.nextInt().toString(), flashcard_name)

        // Add new Flashcard to ViewModel
        viewModel.addNotificationsData(newFlashcard)

        // Observer on notifications_list variable
        observe()
    }

    private fun observe() {
        viewModel.notifications_list.observe(viewLifecycleOwner, Observer { notifications ->
            notifications.let {
                (recyclerView_notifications.adapter as NotificationsAdapter).notification_titles =
                    notifications
            }
        })
    }
}
