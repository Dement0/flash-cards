package com.example.flashcards.db

class FlashcardRepository(private val flashcardDao: FlashcardDao) {

    suspend fun insert(flashcard: Flashcard) {
        flashcardDao.insert(flashcard)
    }

    suspend fun getFlashcards() = flashcardDao.getFlashcards()
}