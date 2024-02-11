package com.devrachit.insta.ui.DashBoardScreens.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.insta.Constants.Constants.Companion.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
) : ViewModel() {

    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchResults = MutableStateFlow<List<answer>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private var searchJob: Job? = null

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) { text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                delay(2000L)
                persons.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        println("Text: $text")
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000L)
            val snapshot = db.collection(USER_NODE)
                .whereGreaterThanOrEqualTo("userName", text)
                .whereLessThanOrEqualTo("userName", text + "\uf8ff")
            val answer = snapshot.get().await()

            val results = mutableListOf<answer>()

            for (document in answer.documents) {
                val data = document.data
                if (data != null) {
                    val answer2 = answer(
                        data["userName"] as String,
                        data["profilePic"] as String,
                        data["followers"].toString().toInt(),
                        data["following"].toString().toInt(),
                        data["bio"] as String,
                        data["postCount"].toString().toInt(),
                        data["email"] as String,
                        data["uid"] as String,
                        data["emailVerified"] as Boolean
                    )
                    println("Answer: $answer2")
                    results.add(answer2)
                    _searchResults.value = results
                }
            }
//
        }
    }
}

data class answer(
    val userName: String,
    val profilePic: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val bio: String = "",
    val postCount: Int = 0,
    val email: String = "",
    val uid: String = "",
    val emailVerified : Boolean = false,

)

data class Person(
    val firstName: String,
    val lastName: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

val allPersons = listOf(
    Person(
        firstName = "Philipp",
        lastName = "Lackner"
    ),
    Person(
        firstName = "Beff",
        lastName = "Jezos"
    ),
    Person(
        firstName = "Chris P.",
        lastName = "Bacon"
    ),
    Person(
        firstName = "Jeve",
        lastName = "Stops"
    ),
)