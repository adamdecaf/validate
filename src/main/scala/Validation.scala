package com.decaf.validation
import scala.language.higherKinds
import scala.util.control.NonFatal

final case class Success[E, A](value: A) extends Validation[E, A]
final case class Failure[E, A](error: E) extends Validation[E, A]

sealed trait Validation[+E, +A] {

  def isSuccess: Boolean = this match {
    case Success(_) => true
    case _ => false
  }

  def isFailure: Boolean = !isSuccess

  def map[B](f: A => B): Validation[E, B] = this match {
    case Success(a) => Success(f(a))
    case Failure(e) => Failure(e)
  }

  def leftMap[B](f: E => B): Validation[B, A] = this match {
    case Success(a) => Success(a)
    case Failure(e) => Failure(f(e))
  }

  def flatMap[EE >: E, AA](f: A => Validation[EE, AA]): Validation[EE, AA] = this match {
    case Success(a) => f(a)
    case Failure(e) => Failure(e)
  }

  def bimap[EE, AA](f: E => EE, g: A => AA) = this match {
    case Success(a) => Success(g(a))
    case Failure(e) => Failure(f(e))
  }

  def forAll(f: A => Boolean): Boolean = this match {
    case Success(a) => f(a)
    case _ => false
  }

  def foreach[B](f: A => B): Unit = this match {
    case Success(a) => f(a)
    case Failure(_) => ()
  }

  def orElse[EE >: E, AA >: A](or: => Validation[EE, AA]): Validation[EE, AA] = this match {
    case Success(a) => Success(a)
    case Failure(_) => or
  }

  def swap: Validation[A, E] = this match {
    case Success(a) => Failure(a)
    case Failure(e) => Success(e)
  }

  def toEither: Either[E, A] = this match {
    case Success(a) => Right(a)
    case Failure(e) => Left(e)
  }

  def toList: List[A] = this match {
    case Success(a) => List(a)
    case _ => List.empty
  }

  def toOption: Option[A] = this match {
    case Success(a) => Some(a)
    case _ => None
  }
}

object Validation {

  def apply[E, A](v: => A) =
    try {
      Success(v)
    } catch {
      case NonFatal(e) => Failure(e)
    }

  def success[E, A](v: A): Validation[E, A] = Success(v)
  def failure[E, A](v: E): Validation[E, A] = Failure(v)
}
