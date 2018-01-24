package scalajsreact.template.components

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalajsreact.template.models.Menu
import scalajsreact.template.routes.AppRouter.AppPage

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

object TopNav {

  object Style extends StyleSheet.Inline {

    import dsl._

    val navMenu = style(display.flex,
                        alignItems.center,
                        backgroundColor(c"#F2706D"),
                        margin.`0`,
                        listStyle := "none")

    val menuItem = styleF.bool { selected =>
      styleS(
        padding(20.px),
        fontSize(1.5.em),
        cursor.pointer,
        color(c"rgb(244, 233, 233)"),
        mixinIfElse(selected)(backgroundColor(c"#E8433F"), fontWeight._500)(
          &.hover(backgroundColor(c"#B6413E")))
      )
    }
  }

  case class Props(menus: Vector[Menu], selectedPage: AppPage, controller: RouterCtl[AppPage])

  implicit val currentPageReuse = Reusability.by_==[AppPage]
  implicit val propsReuse = Reusability.by((_: Props).selectedPage)

  def renderMenuItem(item: Menu, selected: Boolean, onClick: TagMod) =
    <.li(^.key := item.name, Style.menuItem(selected), item.name, onClick)

  def renderMenuItems(props: Props) =
    props.menus.toTagMod(item => {
      val selected = item.route.getClass == props.selectedPage.getClass
      val onClick = props.controller setOnClick item.route

      renderMenuItem(item, selected, onClick)
    })


  val component = ScalaComponent.builder[Props]("TopNav")
    .render_P { props =>
      <.header(
        <.nav(Style.navMenu, renderMenuItems(props))
      )
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  def apply(props: Props) = component(props)

}
