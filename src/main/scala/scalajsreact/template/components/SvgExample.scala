package scalajsreact.template.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.svg_<^
import org.singlespaced.d3js.Ops._
import org.singlespaced.d3js.d3

import scala.math.{log, floor}
import scala.language.postfixOps
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.internal.mutable.{GlobalRegistry, StyleSheet}

//noinspection TypeAnnotation
object SvgExample {

  /*
   * This object corresponds to css in normal js. Each val here generates a css class, eg. Item3Data_Style-paragraph.
   * The classes can then be applied to a html tag by passing them as an argument, eg.
   * <.p(Style.paragraph, "This is a paragraph!").
   */
  object Style extends StyleSheet.Inline {
    import dsl._

    val container = style(
      alignSelf.center,
      alignItems.center,
      display.flex,
      flexDirection.column,
      zIndex(10),
      padding(2.em),
      margin(2.em),
      width(60.%%),
      boxShadow := "0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)",
      transition := "all 0.3s cubic-bezier(.25,.8,.25,1)",
      &.hover(
        boxShadow := "0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22)"
      )
    )

    val innerItem = style(
      display.flex,
      flexDirection.row
    )

    val paragraph = style(
      color(Color("#43b083")),
      fontSize(25.px)
    )
  }

  /* Props are properties of our React component. These are passed from the component that renders ours. In our case,
   * we have none, and so they are of type Unit.
   * State is stored internally in the component, but our component is stateless, and so State is also Unit.
   */
  type Props = Unit
  type State = Unit

  /* The backend class is the class that handles the logic (and also the rendering) of our component
   *
   */
  class Backend($: BackendScope[Props, State]) {
    // This function is responsible for creating the markup
    def render(p: Props, s: State) = {
      <.div(Style.container,
        <.p(Style.paragraph, "Example component, showcasing SVG usage"),
        svg_<^.<.svg(^.id := "svg-holder", ^.maxWidth := "750px")
      )
    }
    // When our component is ready to mount, add its css styles to the page
    def componentWillMount: Callback = Callback { GlobalRegistry.register(Style) }

    // When the component has mounted, populate the SVG
    def componentDidMount: Callback = Callback {
      val data = js.Array(8, 14, 7, 28)

      val graphWidth = 800
      val graphHeight = 450
      val barWidth = (graphWidth * 0.9) / data.length
      val barSeparation = (graphWidth * 0.1) / data.length
      val maxData = 50
      val horizontalBarDistance = barWidth + barSeparation
      val barHeightMultiplier = graphHeight / maxData
      val c = d3.rgb(33, 108, 42)

      val rectXFun = (_: Int, i: Int) => i * horizontalBarDistance
      val rectYFun = (d: Int) => graphHeight - d * barHeightMultiplier
      val rectHeightFun = (d: Int) => d * barHeightMultiplier
      val rectColorFun = (d: Int, _: Int) => c.brighter(d / 10).toString


      val svg = d3.select("#svg-holder").attr("viewBox", "0 0 800 450")
      val dataNodes = svg.selectAll(".data").data(data).enter().append("g").attr("class", "data")

      dataNodes.append("rect")
        .attr("x", rectXFun)
        .attr("y", rectYFun)
        .attr("width", barWidth)
        .attr("height", rectHeightFun)
        .style("fill", rectColorFun)

      dataNodes.append("text")
        .attr("x", (d: Int, i: Int) => rectXFun(d, i) + barWidth / 2)
        .attr("y", (d: Int) => rectYFun(d) - 20)
        .style("text-anchor", "middle")
        .text((d: Int) => s"$d$$")
        .style("fill", rectColorFun)
        .style("font-size", (d: Int, i: Int) => s"${(12 * log(d)) floor}px")

    }
  }

  // Build the component, hook up life-cycle methods (componentWillMount, componentDidMount) to the backend
  val component = ScalaComponent.builder[Props]("Example")
    .renderBackend[Backend] // Use our backend
    .componentWillMount(_.backend.componentWillMount)
    .componentDidMount(_.backend.componentDidMount)
    .build

  // Convert our component into a virtual-dom element
  def apply() = component().vdomElement
}
