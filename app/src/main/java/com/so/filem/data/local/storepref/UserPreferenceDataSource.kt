package com.so.filem.data.local.storepref

interface UserPreferenceDataSource {
    fun isSkipIntro(): Boolean
    fun setSkipIntro(isSkipIntro: Boolean)
    fun getUserToken(): String?
    fun setUserToken(username: String)
    fun clearUserToken()
}

class UserPreferenceDataSourceImpl(private val preference: UserPreference) :
    UserPreferenceDataSource {

    override fun isSkipIntro(): Boolean {
        return preference.isSkipIntro()
    }

    override fun setSkipIntro(isSkipIntro: Boolean) {
        preference.setSkipIntro(isSkipIntro)
    }

    override fun getUserToken(): String? {
        return preference.getUserToken()
    }

    override fun setUserToken(username: String) {
        preference.setUserToken(username)
    }

    override fun clearUserToken() {
        preference.clearUserToken()
    }

}