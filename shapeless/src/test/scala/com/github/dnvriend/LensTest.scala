package com.github.dnvriend

import shapeless._

case class Address(street: String, city: String, postcode: String)
case class Person(name: String, age: Int, address: Address)

object Person {
  val nameLens = lens[Person] >> 'name
  val ageLens = lens[Person] >> 'age
  val addressLens = lens[Person] >> 'address
  val streetLens = lens[Person] >> 'address >> 'street
  val cityLens = lens[Person] >> 'address >> 'city
  val postcodeLens = lens[Person] >> 'address >> 'postcode
}

class LensTest extends TestSpec {
  val person = Person("Joe Grey", 37, Address("Southover Street", "Brighton", "BN2 9UA"))

  it should "access the age field" in {
    person.age shouldBe 37
    Person.ageLens.get(person) shouldBe 37
  }

  it should "update a field in a sane way" in {
    val person2: Person = Person.ageLens.set(person)(42)
    person2.age shouldBe 42
  }

  it should "transform a field using a function" in {
    def plusOne(x: Int): Int = x + 1
    val person2 = Person.ageLens.modify(person)(plusOne)
    person2.age shouldBe 38
  }

  it should "read a nested field" in {
    val street = Person.streetLens.get(person)
    street shouldBe "Southover Street"
  }

  it should "modify a nested field" in {
    val person2 = Person.streetLens.set(person)("Montpelier Road")
    person2.address.street shouldBe "Montpelier Road"
  }
}
