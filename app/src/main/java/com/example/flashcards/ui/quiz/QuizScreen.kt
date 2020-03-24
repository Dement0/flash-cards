package com.example.flashcards.ui.quiz

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.flashcards.R

class QuizScreen : AppCompatActivity() {

    private lateinit var adapter: QuizAdapter
    private lateinit var closeButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        setupViews()
        setupClickListeners()
        setupOnPageChangeListener()
        observe()
    }

    private fun setupViews() {
        adapter = QuizAdapter()
        closeButton = findViewById(R.id.closeQuizButton)
        nextButton = findViewById(R.id.nextButtonQuiz)
        previousButton = findViewById(R.id.previousButtonQuiz)
        viewPager = findViewById(R.id.quizPager)
        viewPager.adapter = adapter
    }

    private fun setupClickListeners() {
        closeButton.setOnClickListener { finish() }
        previousButton.setOnClickListener {
            if (viewPager.currentItem < 0) finish() else viewPager.currentItem--
        }
        nextButton.setOnClickListener {
            if (viewPager.currentItem == adapter.itemCount - 1) finish() else viewPager.currentItem++
        }
    }

    private fun setupOnPageChangeListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (viewPager.currentItem == 0) {
                    previousButton.visibility = INVISIBLE
                    nextButton.visibility = VISIBLE
                } else {
                    previousButton.visibility = VISIBLE
                    nextButton.visibility = VISIBLE
                }
            }
        })
    }

    private fun observe() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is QuizState.Error -> {
                    Toast.makeText(this, "No question available.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is QuizState.Success -> {
                    adapter.submitList(state.questions)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}
