package scalajsreact.template.routes

import scalajsreact.template.components.items.{Item1Data, Item2Data, Item3Data, ItemsInfo}
import scalajsreact.template.pages.ItemsPage
import japgolly.scalajs.react.extra.router.RouterConfigDsl
import japgolly.scalajs.react.vdom.VdomElement

sealed abstract class Item(val title: String,
                           val routerPath: String,
                           val render: () => VdomElement)

object Item {

  case object Info extends Item("Info", "info", () => ItemsInfo())

  case object Item1 extends Item("Item1", "item1", () => Item1Data())

  case object Item2 extends Item("Item2", "item2", () => Item2Data())
  case object Item3 extends Item("Item3", "item3", () => Item3Data())

  val menu = Vector(Info, Item1, Item2, Item3)

  val routes = RouterConfigDsl[Item].buildRule { dsl =>
    import dsl._
    menu
      .map(item => staticRoute(item.routerPath, item) ~> renderR(r => ItemsPage(ItemsPage.Props(item, r))))
      .reduce(_ | _)
  }
}
