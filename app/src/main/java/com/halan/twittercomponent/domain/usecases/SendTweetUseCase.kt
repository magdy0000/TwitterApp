package com.halan.twittercomponent.domain.usecases

import com.halan.twittercomponent.domain.IRepository
import javax.inject.Inject

class SendTweetUseCase @Inject constructor (val iRepository: IRepository)  {

    suspend operator fun invoke (text : String) =
        iRepository.sendTweet(text)

}