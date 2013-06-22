package com.decaf.validation
import java.lang.ArithmeticException
import org.specs2.mutable.Specification

object ValidationSpec extends Specification with ValidationMatchers {

  "Validation" should {
    "work to create success and failures" in {
      val succ = Validation(1 + 1)
      val fail = Validation(1 / 0)

      succ must beSuccess
      succ must beSuccess(2)
      fail must beFailure
    }

    "let us perform map" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.map(_.toString) must beSuccess("1")
      fail.map(_.toString) must beFailure
    }

    "let us flatMap" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.flatMap(s => Success(s + 10)) must beSuccess(11)
      succ.flatMap(Failure(_)) must beFailure
      fail.flatMap(Success(_)) must beFailure
    }

    "let us bimap" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.bimap(_ => new Throwable{}, _ + 10) must beSuccess(11)
      fail.bimap(_ => new Throwable{}, _ + 10) must beFailure
    }

    "let us filter" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.filter(_ == 1) must beSuccess(1)
      //succ.filter(_ == 2) must beFailure
      fail.filter(_ == 12) must beFailure
    }

    "let us forAll" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.forAll(_ == 1) must beTrue
      succ.forAll(_ == 2) must beFalse
      fail.forAll(_ == 12) must beFalse
    }

    "let us foreach" in {
      var count = 0
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.foreach(count += _)
      fail.foreach(count += _)
      count === 1
    }

    "let us orElse" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      (succ orElse fail) must beSuccess(1)
      (fail orElse fail) must beFailure
    }

    "let us swap" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.swap must beFailure
      fail.swap must beSuccess
    }

    "let us toEither" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.toEither === Right(1)
      fail.toEither must beLeft
    }

    "let us toList" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.toList === List(1)
      fail.toList must beEmpty
    }

    "let us toOption" in {
      val succ = Validation(1)
      val fail = Validation(1 / 0)
      succ.toOption must beSome(1)
      fail.toOption must beNone
    }
  }
}
