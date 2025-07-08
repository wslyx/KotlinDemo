package top.superyaxi.klassAndObject

import kotlinx.serialization.Serializable

@Serializable
data object InformationBaseRoute {

    @Serializable
    data object InformationRoute

    @Serializable
    data object InformationRoute2

}

fun main() {
    println(InformationBaseRoute.InformationRoute)

    val route1 = InformationBaseRoute.InformationRoute
    val route2 = InformationBaseRoute.InformationRoute
    println("route1 == route2:\t" + (route1 == route2))
    println("route1:\t$route1")
    println("route2:\t$route2")

    println("InformationBaseRoute.InformationRoute2:\t${InformationBaseRoute.InformationRoute2}")
    println("InformationBaseRoute:\t${InformationBaseRoute}")
}
