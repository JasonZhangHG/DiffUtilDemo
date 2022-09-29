package cool.diffutil.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    fun getUsersList(): LiveData<MutableList<User>>? {
        return AppDatabase.getInstance().userDao().getAll()
    }
}