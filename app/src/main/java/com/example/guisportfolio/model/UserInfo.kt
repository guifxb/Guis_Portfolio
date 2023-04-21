package com.example.guisportfolio.model

import android.content.Context
import com.example.guisportfolio.R


class UserInfo(
    var name: Int,
    var title: Int,
    var aboutMe: Int,
    var whatsApp: Int,
    var linkedIn: Int,
    var email: Int
) {

    fun toString(context: Context, int: Int): String{
        return context.getString(int)
    }
}

val defaultUser = UserInfo(R.string.home_screen_about_me, R.string.title_dev, R.string.about_me_info, R.string.contactWhasApp, R.string.contactLinkedin, R.string.contactEmail)
