package io.github.cloudify.scala.spdf

/**
 * Renders a parameter of type T to a sequence of strings that will be
 * appended to the command line.
 */
trait ParamShow[T] {
  def show(name: String, value: T): Iterable[String]
}

object ParamShow {

  implicit object StringParamShow extends ParamShow[String] {
    override def show(name: String, value: String): Iterable[String] =
      formatParam(name, Some(value))
  }

  implicit object BooleanParamShow extends ParamShow[Boolean] {
    override def show(name: String, value: Boolean): Iterable[String] = value match {
      case true => formatParam(name, None)
      case _    => Iterable.empty
    }
  }

  implicit object IterableParamShow extends ParamShow[Iterable[String]] {
    override def show(name: String, value: Iterable[String]): Iterable[String] = {
      value flatMap (x => formatParam(name, Some(x)))
    }
  }

  implicit object MultipleValueParamShow extends ParamShow[(String, String)] {
    override def show(name: String, value: (String, String)): Iterable[String] =
      formatParam(name, value)
  }

  implicit object OptionBooleanParamShow extends ParamShow[Option[Boolean]] {
    override def show(name: String, valueOpt: Option[Boolean]): Iterable[String] =
      valueOpt.toIterable.flatMap { formatParam(name, _) }
  }

  implicit object IntParamShow extends ParamShow[Int] {
    override def show(name: String, value: Int): Iterable[String] =
      formatParam(name, Some(value.toString))
  }

  implicit object FloatParamShow extends ParamShow[Float] {
    override def show(name: String, value: Float): Iterable[String] =
      formatParam(name, Some("%.2f".format(value)))
  }

  implicit object PageOrientationParamShow extends ParamShow[PageOrientation] {
    override def show(name: String, value: PageOrientation): Iterable[String] =
      formatParam(name, Some(value.value))
  }

  private def formatParam(name: String, value: Option[String]): Iterable[String] =
    Seq(Some("--" + name), value).flatten

  private def formatParam(name: String, value: (String, String)): Iterable[String] =
    Seq("--" + name, value._1, value._2)

  private def formatParam(name: String, value: Boolean): Iterable[String] = if (value) {
    Some("--" + name)
  } else {
    Some("--no-" + name)
  }

}
