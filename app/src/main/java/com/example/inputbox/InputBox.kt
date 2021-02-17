package com.example.inputbox

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates


class InputBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) :
    AppCompatEditText(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 边框宽度
    // 边框圆角
    // 边框颜色
    private var boxWidth by Delegates.notNull<Float>()
    private var boxRadius by Delegates.notNull<Float>()
    private var boxColor by Delegates.notNull<Int>()

    // 字体颜色
    // 字体大小
    private var textBoxSize by Delegates.notNull<Float>()
    private var textBoxColor by Delegates.notNull<Int>()

    // 背景框
    private val rectF = RectF(0f, 0f, 40f, 40f)


    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputBox)
        // 获得转换后的px值
        val boxWidthPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            2f,
            context.resources.displayMetrics
        )
        boxWidth = typedArray.getDimension(
            R.styleable.InputBox_boxStrokeWidth,
            boxWidthPx
        )

        // 获得转换后的px值
        val boxRadiusPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            10f,
            context.resources.displayMetrics
        )
        boxRadius = typedArray.getFloat(
            R.styleable.InputBox_boxStrokeRadius, boxRadiusPx
        )
        boxColor = typedArray.getColor(
            R.styleable.InputBox_boxStrokeColor,
            ContextCompat.getColor(context, R.color.black)
        )
        // 获得转换后的ps值
        val textSizeSp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            15f,
            context.resources.displayMetrics
        )
        textBoxSize =
            typedArray.getDimension(R.styleable.InputBox_android_textSize, textSizeSp)
        textBoxColor = typedArray.getColor(
            R.styleable.InputBox_android_textColor,
            ContextCompat.getColor(context, R.color.design_default_color_primary)
        )

        typedArray.recycle()

        // 去掉下划线
        background = null
        // 限制最大长度
        filters = arrayOf(InputFilter.LengthFilter(4))
        // 数字类型
        inputType = InputType.TYPE_CLASS_NUMBER
        letterSpacing = 0.9f
    }


    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)

        println("输入了:$text")

        // 绘制背景
        canvas?.save()
        mPaint.run {
            color = boxColor
            strokeWidth = boxWidth
            style = Paint.Style.STROKE
        }
        canvas?.run {
            drawRoundRect(rectF, boxRadius, boxRadius, mPaint)
            translate(50f, 0f)
            drawRoundRect(rectF, boxRadius, boxRadius, mPaint)
            translate(50f, 0f)
            drawRoundRect(rectF, boxRadius, boxRadius, mPaint)
            translate(50f, 0f)
            drawRoundRect(rectF, boxRadius, boxRadius, mPaint)
        }
        canvas?.restore()

        // 绘制文本
        canvas?.save()
        mPaint.run {
            color = textBoxColor
            textSize = textBoxSize
            strokeWidth = 1f
            style = Paint.Style.FILL
        }
        val textStr = text.toString()
        textStr.forEach {
            canvas?.run {
                drawText(it.toString(), 15f, 28f, mPaint)
                translate(50f, 0f)
            }
        }
        canvas?.restore()

    }

}