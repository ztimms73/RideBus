package org.xtimms.ridebus.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.mikepenz.aboutlibraries.util.getThemeColor
import org.xtimms.ridebus.R
import org.xtimms.ridebus.databinding.CommonViewEmptyBinding
import kotlin.random.Random

class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {

    private val binding: CommonViewEmptyBinding =
        CommonViewEmptyBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Hide the information view
     */
    fun hide() {
        this.isVisible = false
    }

    /**
     * Show the information view
     * @param textResource text of information view
     */
    fun show(@StringRes textResource: Int, actions: List<Action>? = null) {
        show(context.getString(textResource), actions)
    }

    fun show(message: String, actions: List<Action>? = null) {
        binding.textFace.text = getRandomErrorFace()
        binding.textLabel.text = message

        binding.actionsContainer.removeAllViews()
        val buttonContext = ContextThemeWrapper(context, R.style.Widget_RideBus_Button_ActionButton)
        val buttonColor = ColorStateList.valueOf(context.getThemeColor(R.attr.colorOnBackground))
        actions?.forEach {
            val button = MaterialButton(
                buttonContext,
                null,
                R.attr.borderlessButtonStyle
            ).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f / actions.size
                )

                setTextColor(buttonColor)
                iconTint = buttonColor

                setIconResource(it.iconResId)
                setText(it.stringResId)

                setOnClickListener(it.listener)
            }

            binding.actionsContainer.addView(button)
        }

        this.isVisible = true
    }

    companion object {
        private val ERROR_FACES = listOf(
            "(･o･;)",
            "Σ(ಠ_ಠ)",
            "ಥ_ಥ",
            "(˘･_･˘)",
            "(；￣Д￣)",
            "(･Д･。"
        )

        fun getRandomErrorFace(): String {
            return ERROR_FACES[Random.nextInt(ERROR_FACES.size)]
        }
    }

    data class Action(
        @StringRes val stringResId: Int,
        @DrawableRes val iconResId: Int,
        val listener: OnClickListener
    )
}