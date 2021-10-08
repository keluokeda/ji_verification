package com.ke.jverification

import android.content.Context
import cn.jiguang.verifysdk.api.JVerificationInterface
import cn.jiguang.verifysdk.api.JVerifyUIConfig
import cn.jiguang.verifysdk.api.LoginSettings
import io.reactivex.Observable

object JiVerificationService {

    /**
     * 初始化
     */
    fun init(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            JVerificationInterface.init(context, 5000) { code, _ ->
                emitter.onNext(code == 8000)
                emitter.onComplete()
            }
        }
    }

    fun setDebugMode(boolean: Boolean) {
        JVerificationInterface.setDebugMode(boolean)
    }

    /**
     * 是否初始化成功
     */
    fun isInitSuccess() = JVerificationInterface.isInitSuccess()


    /**
     * 判断网络环境是否可用
     */
    fun checkVerifyEnable(context: Context) = JVerificationInterface.checkVerifyEnable(context)

    /**
     * 获取token
     */
    fun getToken(context: Context): Observable<VerifyResult> {
        return Observable.create { emitter ->
            JVerificationInterface.getToken(context, 5000) { code, content, operator ->
                emitter.onNext(VerifyResult(code == 2000, content, operator))
                emitter.onComplete()
            }
        }
    }

    /**
     * 预登录
     * 验证当前运营商网络是否可以进行一键登录操作，该方法会缓存取号信息，提高一键登录效率。建议发起一键登录前先调用此方法。
     */
    fun preLogin(context: Context): Observable<PreLoginResult> {
        return Observable.create { emitter ->
            JVerificationInterface.preLogin(context, 5000) { code, content ->
                emitter.onNext(PreLoginResult(code == 7000, content))
                emitter.onComplete()
            }
        }
    }

    /**
     * 清除sdk当前预取号结果缓存
     */
    fun clearPreLoginCache() = JVerificationInterface.clearPreLoginCache()


    /**
     * 请求授权一键登录
     */
    fun loginAuth(context: Context): Observable<VerifyResult> {
        val loginSettings = LoginSettings().apply {
            isAutoFinish = true
            timeout = 15 * 1000
        }

        return Observable.create { emitter ->
            JVerificationInterface.loginAuth(context, loginSettings) { code, content, operator ->
                emitter.onNext(VerifyResult(code == 6000, content, operator))
                emitter.onComplete()

            }
        }
    }

    /**
     * 设置协议，每次登录前都设置一下
     */
    fun setCustomUI(title: String, url: String, logoPath: String, privacyState: Boolean = false) {
        val config = JVerifyUIConfig.Builder()


        config.setAppPrivacyOne(title, url)
        config.setPrivacyState(privacyState)
        config.setLogoImgPath(logoPath)

        JVerificationInterface.setCustomUIWithConfig(config.build())
    }
}