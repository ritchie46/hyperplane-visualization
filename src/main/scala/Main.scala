import org.scalajs.dom
import scala.scalajs.js
import org.singlespaced.d3js.d3
import org.singlespaced.d3js.Ops._
import js.JSConverters._

object Main {
  val graphHeight = 850
  val rows = 75
  val radius = 2

  val svg = d3.select("body").append("svg").attr("width", "100%")
    .attr("height", s"${graphHeight}px")


  def main(args: Array[String]): Unit = {
    val (x, y) = grid(rows)

    scatterData(x.toVector, y, 25)
  }

  def grid(rows: Int = 20): (Seq[Int], Seq[Int]) = {
    val gridOffset = 2
    val width: Int = graphHeight / rows
    val detGridX: Int => Int = (d: Int) => (d % (rows * 2)) * width + gridOffset
    val detGridY: Int => Int = (d: Int) => (d / (rows * 2)) * width + gridOffset
    val data = Range(0, rows * 2 * rows)

    val sel = svg.selectAll("circle").data( data.toJSArray ).enter()
    sel
      .append("circle")
      .attr("cx", detGridX)
      .attr("cy", detGridY)
      .attr("r", radius)
      .attr("fill", "grey")

    (data.map(detGridX), data.map(detGridY))
  }

  def scatterData(x: Seq[Int], y: Seq[Int], n: Int = 5): Unit = {

    val (xr, yr) = chooseRandom(x, y, n)
    val (xb, yb) = chooseRandom(x, y, n)

    val c = (x, y).zipped.map( (a, b) => if ((xr, yr).zipped.toList.contains((a, b))) "red" else
                                         if ((xb, yb).zipped.toList.contains((a, b))) "blue" else "grey")
    val dotRadius = c.map(v => if (v == "grey") radius else radius * 2)

    val sel = svg.selectAll("circle")
    sel
      .attr("cx", x)
      .attr("cy", y)
      .attr("r", dotRadius)
      .attr("fill", c)

  }

  def chooseRandom(x: Seq[Int], y: Seq[Int], n: Int): (Vector[Int], Vector[Int]) = {
    val rd = new scala.util.Random()
    val ** = (x: Int) => scala.math.pow(x, 2)
    // first dot
    val x1 = x(rd.nextInt(x.length))
    val y1 = y(rd.nextInt(y.length))

    Stream.from(1).view.map(_ => (x(rd.nextInt(x.length)), y(rd.nextInt(y.length))) )
      .filter( { case (a, b) => scala.math.sqrt(**(x1 - a) + **(y1 - b)) < scala.math.sqrt(n * rows * 30)}).take(n).toVector.unzip
  }

}

