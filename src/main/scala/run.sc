val a = Range(1, 5).toList
val srch = 10
a.fold(false)((acc, b) => if ((acc == true) | (b == srch)) true else false)

a.contains(2)

Stream.from(1).take(5).toList

a.take(2)
