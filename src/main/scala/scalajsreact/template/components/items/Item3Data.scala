package scalajsreact.template.components.items

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Item3Data {

  val component =
    ScalaComponent.builder.static("Item3")(<.div("Third item")).build

  def apply() = component().vdomElement
}
