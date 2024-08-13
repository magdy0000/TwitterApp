package com.halan.twittercomponent.presentation.fragments

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.halan.twittercomponent.BuildConfig
import com.halan.twittercomponent.R
import com.halan.twittercomponent.base.BasicFragment
import com.halan.twittercomponent.databinding.FragmentHomeBinding
import com.halan.twittercomponent.presentation.fragments.HomeContractor.Action
import com.halan.twittercomponent.presentation.fragments.HomeContractor.Event
import com.halan.twittercomponent.presentation.fragments.HomeContractor.State
import com.magdy.poststask.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BasicFragment<FragmentHomeBinding, State,
        Event, Action>(FragmentHomeBinding::inflate) {


    override val viewModel: HomeViewModel by viewModels()

    override fun onViewState(state: State) {
        if (state.isLoading){
            showLoading()
        }else{
            hideLoading()
        }
        binding.characterRemaining.text = state.remaining.toString()
        binding.charactersTyped.text = "${state.typedChars} / 280"
    }

    override fun FragmentHomeBinding.initializeUI() {

        initViewEvent { event ->
            when (event) {
                is Event.Clear -> {
                    editTweet.setText("")
                }
                is Event.CopyText -> {
                    copyText(event.text)
                    showToast(getString(R.string.text_copied_to_clipboard))
                }

                is Event.ShowError -> {
                    showToast(event.message)
                }

                is Event.ShowErrorWithLocalMessage -> {
                    showToast(getString(event.resourceId))
                }
                Event.TweetSent -> {
                    showToast(getString(R.string.tweet_has_been_sent))
                }
            }
        }
    }

    override fun registerListeners() {
        binding.apply {
            editTweet.doAfterTextChanged {
                viewModel.handleAction(Action.CheckRemaining(it.toString()))
            }
            btnSendTweet.setOnClickListener {
                viewModel.handleAction(Action.SendTweet)
            }
            btnCopy.setOnClickListener {
                viewModel.handleAction(Action.Copy)
            }
            btnClear.setOnClickListener {
                viewModel.handleAction(Action.Clear)
            }
        }
    }
    private fun copyText(text : String){
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }


}