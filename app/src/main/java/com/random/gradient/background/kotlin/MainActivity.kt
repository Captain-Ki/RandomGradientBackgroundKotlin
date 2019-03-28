package com.random.gradient.background.kotlin

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var needToWork: Boolean = false
    var colorFrom1: Int = 0
    var colorTo1: Int = 0
    var colorFrom2: Int = 0
    var colorTo2: Int = 0

    var colorAnimation1: ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom1, colorTo1)
    var colorAnimation2: ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom2, colorTo2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            if (needToWork) {
                needToWork = false
            } else {
                needToWork = true
                changeGradient()
            }
        }
    }

    fun changeGradient() {

        if (colorFrom1 == 0 && colorFrom2 == 0) {
            colorFrom1 = Color.argb(255, 255, 255, 255)
            colorFrom2 = Color.argb(255, 255, 255, 255)
            colorTo1 = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            colorTo2 = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        } else {
            colorFrom1 = colorTo1
            colorFrom2 = colorTo2
            colorTo1 = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            colorTo2 = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        }

        colorAnimation2 = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom2, colorTo2)
        colorAnimation2.duration = 3000
        colorAnimation2.addUpdateListener { }
        colorAnimation2.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                if (needToWork) {
                    changeGradient()
                } else {
                    colorAnimation1.pause()
                    colorAnimation2.pause()
                }
            }
        })
        colorAnimation2.start()

        colorAnimation1 = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom1, colorTo1)
        colorAnimation1.duration = 3000
        colorAnimation1.addUpdateListener {
            var gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(colorAnimation1.animatedValue as Int, colorAnimation2.animatedValue as Int)
            )
            gradientDrawable.cornerRadius = 0f


            top_text.text = String.format("#%06X", 0xFFFFFF and colorAnimation1.animatedValue as Int)
            bottom_text.text = String.format("#%06X", 0xFFFFFF and colorAnimation2.animatedValue as Int)
            constraint_layout.background = gradientDrawable
        }
        colorAnimation1.start()
    }
}