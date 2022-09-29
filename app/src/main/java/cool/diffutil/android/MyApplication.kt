package cool.diffutil.android

import androidx.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {

    companion object {
        var instance: MyApplication? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}