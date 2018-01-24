package scalajsreact.template.pages

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scalajsreact.template.components.Example

object HomePage {

  object Style extends StyleSheet.Inline {
    import dsl._

    val container = style(
      display.flex,
      flexDirection.column,
      alignItems.center
    )

    val content = style(
      alignSelf.center,
      textAlign.center,
      fontSize(22.px),
      color.darkgrey,
      paddingTop(2.em))
  }

  val component =
    ScalaComponent.builder
      .static("HomePage")(<.div(
        Style.container,
        <.p(
          Style.content,
          "This is the Homepage component, which renders the Example component below"
        ),
        Example()
      ))
      .build

  def apply() = component()
}
