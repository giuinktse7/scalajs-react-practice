package scalajsreact.template.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.internal.mutable.{GlobalRegistry, StyleSheet}

object Footer {

  object Style extends StyleSheet.Inline {
    import dsl._

    val separator = style(
      overflow.visible,
      padding.`0`,
      borderTop(Color("#333")),
      textAlign.center
    )
  }

  val component = ScalaComponent.builder
    .static("Footer")(
      <.footer(
        ^.textAlign.center,
        <.hr(Style.separator),
        <.p(^.paddingTop := "5px", "Footer")
      )
    )
    .componentDidMount(_ => Callback { GlobalRegistry.register(Style) })
    .build

  def apply() = component()
}
