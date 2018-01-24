package scalajsreact.template.components

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalajsreact.template.routes.Item
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._


object LeftNav {

  object Style extends StyleSheet.Inline {

    import dsl._

    val container = style(display.flex,
                          flexDirection.column,
                          listStyle := "none",
                          padding.`0`)

    val menuItem = styleF.bool { selected =>
      styleS(
        lineHeight(48.px),
        padding :=! "0 25px",
        cursor.pointer,
        textDecoration := "none",
        mixinIfElse(selected)(color.red, fontWeight._500)(
          color.black,
          &.hover(color(c"#555555"), backgroundColor(c"#ecf0f1"))
        )
      )
    }
  }

  case class Props(menus: Vector[Item],
                   selectedPage: Item,
                   controller: RouterCtl[Item])

  implicit val currentPageReuse = Reusability.by_==[Item]
  implicit val propsReuse = Reusability.by((_: Props).selectedPage)

  def renderMenuItem(item: Item, selected: Boolean, onClick: TagMod) =
    <.li(^.key := item.title,
      Style.menuItem(selected),
      item.title,
      onClick)

  def renderMenuItems(props: Props) =
    props.menus.toTagMod(item => {
      val selected = item == props.selectedPage
      val onClick = props.controller setOnClick item

      renderMenuItem(item, selected, onClick)
    })

  val component = ScalaComponent
    .builder[Props]("LeftNav")
    .render_P { props =>
      <.ul(Style.container, renderMenuItems(props))
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  def apply(props: Props) = component(props)

}
