# ObjectPool

An object reuse pool suitable for scenes where objects are frequently created and destroyed. If an instance exists in the reuse pool, the instance is reused; if not, a new instance is created.

[中文说明](README-zh.md)

## Use

### creating a global object reuse pool
```kotlin
val pool = ObjectPoolProvider.global().get(Person::class.java, object : ObjectFactory<Person> {
    override fun create(vararg args: Any?): Person {
        val name = args[0] as String
        val age = args[1] as Int
        return Person(name, age)
    }

    override fun reuse(instance: Person, vararg args: Any?) {
        val name = args[0] as String
        val age = args[1] as Int
        instance.name = name
        instance.age = age
    }

})
```

### use `ObjectPoolStoreOwner` instance to create an object reuse pool
```kotlin
val store = ObjectPoolStore()
val storeOwner = object : ObjectPoolStoreOwner {
    override val store: ObjectPoolStore
        get() = store

}
val pool = ObjectPoolProvider.create(storeOwner).get(Person::class.java, object : ObjectFactory<Person> {
    override fun create(vararg args: Any?): Person {
        val name = args[0] as String
        val age = args[1] as Int
        return Person(name, age)
    }

    override fun reuse(instance: Person, vararg args: Any?) {
        val name = args[0] as String
        val age = args[1] as Int
        instance.name = name
        instance.age = age
    }

})
```

### get instance
get the object instance from the object reuse pool. If the instance exists in the reuse pool, reuse the instance; if not, create a new instance
```kotlin
val person = pool.obtain("Andy", 16);
```
### recycle instance
the object reuse pool recycles objects to facilitate instance reuse
```kotlin
pool.recycle(person)
```
### implementing the `Reusable` interface
Object classes managed by the object reuse pool must implement `Reusable`
```kotlin
class Person(var name: String, var age: Int) : Reusable
```


## Download
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.xenonbyte:ObjectPool:1.1.0'
}
```

## License
Copyright [2024] [xubo]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
