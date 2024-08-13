package com.halan.twittercomponent.presentation.fragments

import com.halan.twittercomponent.base.ViewAction
import com.halan.twittercomponent.base.ViewEvent
import com.halan.twittercomponent.base.ViewState

class HomeContractor {

    sealed class Action : ViewAction {

        data class CheckRemaining (val tweet : String) : Action()
        object SendTweet  : Action()
        object Clear : Action()
        object Copy : Action()
        object ValidateTweet : Action()
    }

    sealed class Event : ViewEvent {

        object Clear : Event()
        object TweetSent : Event()
        data class CopyText(val text: String) : Event()
        data class ShowErrorWithLocalMessage(val resourceId: Int) : Event()
        data class ShowError(val message: String) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val remaining  : Int = 280,
        val typedChars : Int = 0,
        val text : String = "",
        val isTextValid : Boolean = false
    ) : ViewState
}