package cool.diffutil.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cool.diffutil.android.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val mCoroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var mBinding: ActivityMainBinding;
    private lateinit var mUserAdapter: UserAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val viewModel: MyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MyViewModel::class.java)
        mUserAdapter = UserAdapter()
        mBinding.userList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.userList.adapter = mUserAdapter

        viewModel.getUsersList()?.observe(this) {
            mUserAdapter.submitList(it)
        }

        mUserAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                scrollBottom()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                scrollBottom()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                scrollBottom()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                scrollBottom()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                scrollBottom()
            }
        })

        mBinding.btnAddView.setOnClickListener {
            mCoroutineScope.launch {
                val localUserList = AppDatabase.getInstance().userDao().getAll2()
                val count = localUserList?.size ?: 0
                val targetCount = count + 1
                val user = User(targetCount, "FirstName:" + targetCount, "LastName:" + targetCount)
                AppDatabase.getInstance().userDao().insertOneUser(user)
            }
        }
        mBinding.btnEditView.setOnClickListener {
            mCoroutineScope.launch {
                val localUserList = AppDatabase.getInstance().userDao().getAll2()
                if (localUserList?.isNullOrEmpty() == true) {
                    return@launch
                }
                localUserList?.forEach {
                    it.firstName = it.firstName + "A"
                    it.lastName = it.lastName + "B"
                }

                localUserList?.let {
                    AppDatabase.getInstance().userDao().insertUserList(it)
                }
            }
        }

        mBinding.btnDeleteView.setOnClickListener {
            mCoroutineScope.launch {
                val localUserList = AppDatabase.getInstance().userDao().getAll2()
                localUserList?.let {
                    val user = it?.get(it.size - 1)
                    if (user != null) {
                        AppDatabase.getInstance().userDao().delete(user)
                    }
                }
            }
        }
    }

    fun scrollBottom() {
        val count = mUserAdapter.itemCount;
        if (count > 0) {
            mBinding.userList.scrollToPosition(count - 1)
        }
    }
}