package com.quyunshuo.androidbaseframemvvm.base.mvvm.v

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.quyunshuo.androidbaseframemvvm.base.utils.BindingReflex
import com.quyunshuo.androidbaseframemvvm.base.utils.EventBusRegister
import com.quyunshuo.androidbaseframemvvm.base.utils.EventBusUtils

/**
 * Activity基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFrameActivity<VB : ViewBinding> : AppCompatActivity(), FrameView<VB> {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)
        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)
        mBinding.initView()
        initLiveDataObserve()
        initRequestData()
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
    }
}