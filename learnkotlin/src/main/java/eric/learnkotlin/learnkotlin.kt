package eric.learnkotlin

import eric.learnkotlin.annotation.TestIntDef
import eric.learnkotlin.proxy.Car
import eric.learnkotlin.proxy.IVehical
import eric.learnkotlin.proxy.VehicalProxy
import eric.learnkotlin.reflection.ReflectionUtil

/**
 *
 * Created by wenjun.zhong on 2017/6/6.
 */

fun main(args: Array<String>) {
    println(max(1,2))
    val square = Rectangle(12, 11)
    println(square.isSquare)

   // val  apple = Apple()
  //  apple.displayName()
   // FruitInfoUilt.getUtilityInfo()
   // TableCreator.createTable()

    val car = Car()
    val vehicalProxy = VehicalProxy(car)
    var proxy : IVehical = vehicalProxy.create()
    proxy.run()
    ReflectionUtil.MyClassReflection()

    val testIntDef = TestIntDef()
    testIntDef.code = 4
}

fun max(a: Int, b: Int): Int = if(a > b) a else b

class Rectangle(val height: Int, val width: Int){
    val isSquare: Boolean
        get() {
            return height == width
        }
}




