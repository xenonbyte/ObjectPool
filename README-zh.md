# ObjectPool

适用于对象频繁创建和销毁的对象复用池；当复用池中存在实例则复用该实例，如果不存在则创建新的实例

## 使用

### 创建全局对象复用池

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

### 使用`ObjectPoolStoreOwner`创建对象复用池

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

### 获取对象

从对象复用池获取对象,如果复用池存在实例，复用该实例；如果不存在则创建新的实例

```kotlin
val person = pool.obtain("Andy", 16);
```

### 回收对象

对象复用池回收对象，方便复用

```kotlin
pool.recycle(person)
```

### 实现`Reusable`接口

复用对象需实现`Reusable`接口

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

