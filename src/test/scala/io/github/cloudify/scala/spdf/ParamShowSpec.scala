package io.github.cloudify.scala.spdf

import io.github.cloudify.scala.spdf.ParamShow.{BooleanParamShow, StringParamShow, IterableParamShow, MultipleValueParamShow}
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class ParamShowSpec extends WordSpec with ShouldMatchers {

  "StringParamShow" should {

    "represent a parameter" in {
      StringParamShow.show("param", "value") should equal(Seq("--param", "value"))
    }

  }

  "BooleanParamShow" should {

    "represent a parameter when true" in {
      BooleanParamShow.show("param", value = true) should equal(Seq("--param"))
    }

    "return empty parameter when false" in {
      BooleanParamShow.show("param", value = false) should equal(Iterable.empty)
    }

  }

  "IterableParamShow" should {

    "represent a repeatable parameter" in {
      IterableParamShow.show("param", List("a", "b")) should equal(Seq("--param", "a", "--param", "b"))
    }

  }
  
  "MultipleValueParamShow" should {

    "represent a parameter allowing two values" in {
      MultipleValueParamShow.show("param", ("a", "b")) should equal(Seq("--param", "a", "b"))
    }

  }

}
