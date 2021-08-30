package org.xtimms.ridebus.ui.base.activity

import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseThemedActivity() {

    lateinit var binding: VB
}
