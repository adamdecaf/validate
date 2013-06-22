package com.decaf.validation
import org.specs2.matcher.{MatchersImplicits, Matcher}

trait ValidationMatchers extends MatchersImplicits {

  def beSuccess[E, A]: Matcher[Validation[E, A]] =
    ((v: Validation[E, A]) => v.isSuccess,
     (v: Validation[E, A]) => "%s is not a Success".format(v))

  def beSuccess[E, A](inner: A): Matcher[Validation[E, A]] =
    ((v: Validation[E, A]) => v.isSuccess && v === Success(inner),
     (v: Validation[E, A]) => if (v.isFailure) "%s is not a success".format(v) else "%s is not equal to %s".format(v, Success(inner)))

  def beFailure[E, A]: Matcher[Validation[E, A]] =
    ((v: Validation[E, A]) => v.isFailure,
     (v: Validation[E, A]) => "%s is not a Failure".format(v))

  // def beFailure[E]: Matcher[Validation[E, _]] =
  //   ((v: Validation[E, _]) => v.isFailure && v.isInstanceOf[Failure[E, _]],
  //    (v: Validation[E, _]) => if (v.isSuccess) "%s is not a success".format(v) else "%s is not a failure of type %s".format(v, v))
}
