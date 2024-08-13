package com.halan.twittercomponent.presentation.fragments

import androidx.lifecycle.viewModelScope
import com.halan.twittercomponent.R
import com.halan.twittercomponent.base.BaseViewModel
import com.halan.twittercomponent.domain.usecases.CountTweetWordsUseCase
import com.halan.twittercomponent.domain.usecases.SendTweetUseCase
import javax.inject.Inject
import com.halan.twittercomponent.presentation.fragments.HomeContractor.State
import com.halan.twittercomponent.presentation.fragments.HomeContractor.Action
import com.halan.twittercomponent.presentation.fragments.HomeContractor.Event
import com.magdy.poststask.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.math.E

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val countTweetWordsUseCase: CountTweetWordsUseCase,
    private val sendTweetUseCase: SendTweetUseCase
) : BaseViewModel<State, Event, Action>(initialState = State()) {


    override fun handleAction(action: Action) {
        when (action) {
            is Action.CheckRemaining -> {
                setState {
                    copy(
                        text = action.tweet,
                        isTextValid = action.tweet.trim().isNotEmpty()
                    )
                }
                handleRemainingCharacter(action.tweet)
            }

            is Action.SendTweet -> {
                if (validateTweet()) {
                    sendTweet()
                }
            }

            Action.Clear -> {
                setEvent {
                    Event.Clear
                }
            }

            Action.Copy -> {
                setEvent {
                    Event.CopyText(currentState.text)
                }
            }

            Action.ValidateTweet -> {
                validateTweet()
            }
        }
    }


    private fun validateTweet(): Boolean {
        if (currentState.isTextValid) {
            return true
        }
        setEvent {
            Event.ShowErrorWithLocalMessage(R.string.tweet_can_t_be_empty)
        }
        return false
    }

    private fun handleRemainingCharacter(tweet: String) {
        val remaining = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        val charTyped = 280 - remaining
        setState { copy(remaining = remaining, typedChars = charTyped) }

    }

    private fun sendTweet() {

        setState { copy(isLoading = true) }
        viewModelScope.launch(IO) {
            val data = sendTweetUseCase.invoke(currentState.text)
            setState { copy(isLoading = false) }
            when (data) {
                is Resource.Success -> {
                    setEvent {
                        Event.TweetSent
                    }
                    setEvent {
                        Event.Clear
                    }

                }
                is Resource.Failure -> {
                    setEvent {
                        Event.ShowError(data.getErrorMessage().toString())
                    }
                }
            }
        }
    }

}